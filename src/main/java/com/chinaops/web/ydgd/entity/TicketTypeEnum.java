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
public enum TicketTypeEnum {
    OPEN("open"),MODIFY("modify"), CLOSE("close");
    //工单类型
  
    private String ticketType;

    private TicketTypeEnum(String ticketType) {
        this.ticketType = ticketType;
    }
    
    public String toString() {
        return ticketType;
    }
}
