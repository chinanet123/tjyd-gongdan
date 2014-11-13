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
public enum ProductTypeEnum {
    SHARE("share"),EXCLUSIVE("exclusive"), STORAGE("storage");
    //工单类型
  
    private String productType;

    private ProductTypeEnum(String productType) {
        this.productType = productType;
    }
    
    public String toString() {
        return productType;
    }
}
