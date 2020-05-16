/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.relation;

/**
 * This class describes the various problems which can be encountered when
 * accessing a role.
 *
 * <p>
 *  此类描述了访问角色时可能遇到的各种问题。
 * 
 * 
 * @since 1.5
 */
public class RoleStatus {

    //
    // Possible problems
    //

    /**
     * Problem type when trying to access an unknown role.
     * <p>
     *  尝试访问未知角色时出现问题类型。
     * 
     */
    public static final int NO_ROLE_WITH_NAME = 1;
    /**
     * Problem type when trying to read a non-readable attribute.
     * <p>
     *  尝试读取不可读属性时的问题类型。
     * 
     */
    public static final int ROLE_NOT_READABLE = 2;
    /**
     * Problem type when trying to update a non-writable attribute.
     * <p>
     *  尝试更新不可写属性时的问题类型。
     * 
     */
    public static final int ROLE_NOT_WRITABLE = 3;
    /**
     * Problem type when trying to set a role value with less ObjectNames than
     * the minimum expected cardinality.
     * <p>
     *  尝试设置具有比最小预期基数少的ObjectName的角色值时出现的问题类型。
     * 
     */
    public static final int LESS_THAN_MIN_ROLE_DEGREE = 4;
    /**
     * Problem type when trying to set a role value with more ObjectNames than
     * the maximum expected cardinality.
     * <p>
     *  尝试设置具有比最大预期基数更多ObjectNames的角色值时出现的问题类型。
     * 
     */
    public static final int MORE_THAN_MAX_ROLE_DEGREE = 5;
    /**
     * Problem type when trying to set a role value including the ObjectName of
     * a MBean not of the class expected for that role.
     * <p>
     *  尝试设置角色值(包括不是该角色所需类别的MBean的ObjectName)时出现的问题类型。
     * 
     */
    public static final int REF_MBEAN_OF_INCORRECT_CLASS = 6;
    /**
     * Problem type when trying to set a role value including the ObjectName of
     * a MBean not registered in the MBean Server.
     * <p>
     *  尝试设置角色值时出现的问题类型,包括未在MBean Server中注册的MBean的ObjectName。
     * 
     */
    public static final int REF_MBEAN_NOT_REGISTERED = 7;

    /**
     * Returns true if given value corresponds to a known role status, false
     * otherwise.
     *
     * <p>
     *  如果给定值对应于已知角色状态,则返回true,否则返回false。
     * 
     * @param status a status code.
     *
     * @return true if this value is a known role status.
     */
    public static boolean isRoleStatus(int status) {
        if (status != NO_ROLE_WITH_NAME &&
            status != ROLE_NOT_READABLE &&
            status != ROLE_NOT_WRITABLE &&
            status != LESS_THAN_MIN_ROLE_DEGREE &&
            status != MORE_THAN_MAX_ROLE_DEGREE &&
            status != REF_MBEAN_OF_INCORRECT_CLASS &&
            status != REF_MBEAN_NOT_REGISTERED) {
            return false;
        }
        return true;
    }
}