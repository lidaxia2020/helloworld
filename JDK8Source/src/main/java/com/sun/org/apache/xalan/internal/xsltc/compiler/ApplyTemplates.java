/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: ApplyTemplates.java,v 1.2.4.1 2005/09/12 09:59:21 pvedula Exp $
 * <p>
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Enumeration;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.org.apache.xml.internal.utils.XML11Char;

/**
/* <p>
/*  $ Id：ApplyTemplates.java,v 1.2.4.1 2005/09/12 09:59:21 pvedula Exp $
/* 
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
final class ApplyTemplates extends Instruction {
    private Expression _select;
    private Type       _type = null;
    private QName      _modeName;
    private String     _functionName;

    public void display(int indent) {
        indent(indent);
        Util.println("ApplyTemplates");
        indent(indent + IndentIncrement);
        Util.println("select " + _select.toString());
        if (_modeName != null) {
            indent(indent + IndentIncrement);
            Util.println("mode " + _modeName);
        }
    }

    public boolean hasWithParams() {
        return hasContents();
    }

    public void parseContents(Parser parser) {
        final String select = getAttribute("select");
        final String mode   = getAttribute("mode");

        if (select.length() > 0) {
            _select = parser.parseExpression(this, "select", null);

        }

        if (mode.length() > 0) {
            if (!XML11Char.isXML11ValidQName(mode)) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.INVALID_QNAME_ERR, mode, this);
                parser.reportError(Constants.ERROR, err);
            }
            _modeName = parser.getQNameIgnoreDefaultNs(mode);
        }

        // instantiate Mode if needed, cache (apply temp) function name
        _functionName =
            parser.getTopLevelStylesheet().getMode(_modeName).functionName();
        parseChildren(parser);// with-params
    }

    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        if (_select != null) {
            _type = _select.typeCheck(stable);
            if (_type instanceof NodeType || _type instanceof ReferenceType) {
                _select = new CastExpr(_select, Type.NodeSet);
                _type = Type.NodeSet;
            }
            if (_type instanceof NodeSetType||_type instanceof ResultTreeType) {
                typeCheckContents(stable); // with-params
                return Type.Void;
            }
            throw new TypeCheckError(this);
        }
        else {
            typeCheckContents(stable);          // with-params
            return Type.Void;
        }
    }

    /**
     * Translate call-template. A parameter frame is pushed only if
     * some template in the stylesheet uses parameters.
     * <p>
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        boolean setStartNodeCalled = false;
        final Stylesheet stylesheet = classGen.getStylesheet();
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        final int current = methodGen.getLocalIndex("current");

        // check if sorting nodes is required
        final Vector sortObjects = new Vector();
        final Enumeration children = elements();
        while (children.hasMoreElements()) {
            final Object child = children.nextElement();
            if (child instanceof Sort) {
                sortObjects.addElement(child);
            }
        }

        // Push a new parameter frame
        if (stylesheet.hasLocalParams() || hasContents()) {
            il.append(classGen.loadTranslet());
            final int pushFrame = cpg.addMethodref(TRANSLET_CLASS,
                                                   PUSH_PARAM_FRAME,
                                                   PUSH_PARAM_FRAME_SIG);
            il.append(new INVOKEVIRTUAL(pushFrame));
            // translate with-params
            translateContents(classGen, methodGen);
        }


        il.append(classGen.loadTranslet());

        // The 'select' expression is a result-tree
        if ((_type != null) && (_type instanceof ResultTreeType)) {
            // <xsl:sort> cannot be applied to a result tree - issue warning
            if (sortObjects.size() > 0) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.RESULT_TREE_SORT_ERR,this);
                getParser().reportError(WARNING, err);
            }
            // Put the result tree (a DOM adapter) on the stack
            _select.translate(classGen, methodGen);
            // Get back the DOM and iterator (not just iterator!!!)
            _type.translateTo(classGen, methodGen, Type.NodeSet);
        }
        else {
            il.append(methodGen.loadDOM());

            // compute node iterator for applyTemplates
            if (sortObjects.size() > 0) {
                Sort.translateSortIterator(classGen, methodGen,
                                           _select, sortObjects);
                int setStartNode = cpg.addInterfaceMethodref(NODE_ITERATOR,
                                                             SET_START_NODE,
                                                             "(I)"+
                                                             NODE_ITERATOR_SIG);
                il.append(methodGen.loadCurrentNode());
                il.append(new INVOKEINTERFACE(setStartNode,2));
                setStartNodeCalled = true;
            }
            else {
                if (_select == null)
                    Mode.compileGetChildren(classGen, methodGen, current);
                else
                    _select.translate(classGen, methodGen);
            }
        }

        if (_select != null && !setStartNodeCalled) {
            _select.startIterator(classGen, methodGen);
        }

        //!!! need to instantiate all needed modes
        final String className = classGen.getStylesheet().getClassName();
        il.append(methodGen.loadHandler());
        final String applyTemplatesSig = classGen.getApplyTemplatesSig();
        final int applyTemplates = cpg.addMethodref(className,
                                                    _functionName,
                                                    applyTemplatesSig);
        il.append(new INVOKEVIRTUAL(applyTemplates));

        // Pop parameter frame
        if (stylesheet.hasLocalParams() || hasContents()) {
            il.append(classGen.loadTranslet());
            final int popFrame = cpg.addMethodref(TRANSLET_CLASS,
                                                  POP_PARAM_FRAME,
                                                  POP_PARAM_FRAME_SIG);
            il.append(new INVOKEVIRTUAL(popFrame));
        }
    }
}
