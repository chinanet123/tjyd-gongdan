/*
 * $Id$
 *
 * All Rights Reserved 2014 China OPS Information Technology Co.,Ltd.
 */
package com.chinaops.web.ydgd.entity;

/**
 *
 * @author maningtao
 */
public enum ForWebSiteEnum {
    YES("yes"),NO("no");
    //工单类型
  
    private String forWebsite;

    private ForWebSiteEnum(String forWebsite) {
        this.forWebsite = forWebsite;
    }
    
    public String toString() {
        return forWebsite;
    }
}
