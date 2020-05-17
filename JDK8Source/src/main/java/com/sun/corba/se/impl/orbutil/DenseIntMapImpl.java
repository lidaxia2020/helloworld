/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.orbutil ;

import java.util.ArrayList ;

/** Utility for managing mappings from densely allocated integer
 * keys to arbitrary objects.  This should only be used for
 * keys in the range 0..max such that "most" of the key space is actually
 * used.
 * <p>
 *  键到任意对象。这应该仅用于0..max范围内的键,使得实际使用的键空间的"大多数"。
 * 
 */
public class DenseIntMapImpl
{
    private ArrayList list = new ArrayList() ;

    private void checkKey( int key )
    {
        if (key < 0)
            throw new IllegalArgumentException( "Key must be >= 0." ) ;
    }

    /** If key >= 0, return the value bound to key, or null if none.
     * Throws IllegalArgumentException if key <0.
     * <p>
     *  如果键<0,则抛出IllegalArgumentException。
     * 
     */
    public Object get( int key )
    {
        checkKey( key ) ;

        Object result = null ;
        if (key < list.size())
            result = list.get( key ) ;

        return result ;
    }

    /** If key >= 0, bind value to the key.
     * Throws IllegalArgumentException if key <0.
     * <p>
     *  如果键<0,则抛出IllegalArgumentException。
     */
    public void set( int key, Object value )
    {
        checkKey( key ) ;
        extend( key ) ;
        list.set( key, value ) ;
    }

    private void extend( int index )
    {
        if (index >= list.size()) {
            list.ensureCapacity( index + 1 ) ;
            int max = list.size() ;
            while (max++ <= index)
                list.add( null ) ;
        }
    }
}