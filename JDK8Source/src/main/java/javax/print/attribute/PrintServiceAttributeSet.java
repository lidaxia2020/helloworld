/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package javax.print.attribute;

/**
 * Interface PrintServiceAttributeSet specifies the interface for a set of
 * print job attributes, i.e. printing attributes that implement interface
 * {@link
 * PrintServiceAttribute PrintServiceAttribute}. In the Print Service API,
 * the Print Service instance uses a PrintServiceAttributeSet to report the
 * status of the print service.
 * <P>
 * A PrintServiceAttributeSet is just an {@link AttributeSet AttributeSet}
 * whose constructors and mutating operations guarantee an additional
 * invariant,
 * namely that all attribute values in the PrintServiceAttributeSet must be
 * instances of interface {@link PrintServiceAttribute PrintServiceAttribute}.
 * The {@link #add(Attribute) add(Attribute)}, and
 * {@link #addAll(AttributeSet) addAll(AttributeSet)} operations
 * are respecified below to guarantee this additional invariant.
 * <P>
 *
 * <p>
 *  接口PrintServiceAttributeSet指定一组打印作业属性的接口,即实现接口{@link PrintServiceAttribute PrintServiceAttribute}的打印属
 * 性。
 * 在打印服务API中,打印服务实例使用PrintServiceAttributeSet报告打印服务的状态。
 * <P>
 *  PrintServiceAttributeSet只是一个{@link AttributeSet AttributeSet},其构造函数和变异操作保证了额外的不变量,即PrintServiceAttri
 * buteSet中的所有属性值必须是interface {@link PrintServiceAttribute PrintServiceAttribute}的实例。
 * 下面重新定义{@link #add(Attribute)add(Attribute)}和{@link #addAll(AttributeSet)addAll(AttributeSet)}操作,以保证此额
 * 外的不变量。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public interface PrintServiceAttributeSet extends AttributeSet {



    /**
     * Adds the specified attribute value to this attribute set if it is not
     * already present, first removing any existing value in the same
     * attribute category as the specified attribute value (optional
     * operation).
     *
     * <p>
     *  如果指定的属性值不存在,则将指定的属性值添加到此属性集中,首先删除与指定属性值相同的属性类别中的任何现有值(可选操作)。
     * 
     * 
     * @param  attribute  Attribute value to be added to this attribute set.
     *
     * @return  <tt>true</tt> if this attribute set changed as a result of
     *          the call, i.e., the given attribute value was not already a
     *          member of this attribute set.
     *
     * @throws  UnmodifiableSetException
     *     (unchecked exception) Thrown if this attribute set does not
     *     support the <CODE>add()</CODE> operation.
     * @throws  ClassCastException
     *     (unchecked exception) Thrown if the <CODE>attribute</CODE> is
     *     not an instance of interface
     *     {@link PrintServiceAttribute PrintServiceAttribute}.
     * @throws  NullPointerException
     *    (unchecked exception) Thrown if the <CODE>attribute</CODE> is null.
     */
    public boolean add(Attribute attribute);

    /**
     * Adds all of the elements in the specified set to this attribute.
     * The outcome is  the same as if the
     * {@link #add(Attribute) add(Attribute)}
     * operation had been applied to this attribute set successively with
     * each element from the specified set. If none of the categories in the
     * specified set  are the same as any categories in this attribute set,
     * the <tt>addAll()</tt> operation effectively modifies this attribute
     * set so that its value is the <i>union</i> of the two sets.
     * <P>
     * The behavior of the <CODE>addAll()</CODE> operation is unspecified if
     * the specified set is modified while the operation is in progress.
     * <P>
     * If the <CODE>addAll()</CODE> operation throws an exception, the effect
     * on this attribute set's state is implementation dependent; elements
     * from the specified set before the point of the exception may or
     * may not have been added to this attribute set.
     *
     * <p>
     * 将指定集合中的所有元素添加到此属性。结果与将{@link #add(Attribute)add(Attribute)}操作连续应用于来自指定集合的​​每个元素的此属性集相同。
     * 如果指定集合中没有类别与此属性集中的任何类别相同,则<tt> addAll()</tt>操作将有效修改此属性集,以使其值为<i> union </i >两个集合。
     * <P>
     *  如果在操作正在进行时修改指定的集合,则未指定<CODE> addAll()</CODE>操作的行为。
     * 
     * @param  attributes  whose elements are to be added to this attribute
     *            set.
     *
     * @return  <tt>true</tt> if this attribute set changed as a result of
     *          the call.
     *
     * @throws  UnmodifiableSetException
     *     (Unchecked exception) Thrown if this attribute set does not
     *     support the <tt>addAll()</tt> method.
     * @throws  ClassCastException
     *     (Unchecked exception) Thrown if some element in the specified
     *     set is not an instance of interface {@link PrintServiceAttribute
     *     PrintServiceAttribute}.
     * @throws  NullPointerException
     *     (Unchecked exception) Thrown if the specified  set is null.
     *
     * @see #add(Attribute)
     */
    public boolean addAll(AttributeSet attributes);
}
