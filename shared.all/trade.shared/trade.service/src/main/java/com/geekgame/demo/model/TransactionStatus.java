package com.geekgame.demo.model;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 交易状态枚举类
 */
public enum TransactionStatus implements Serializable {
    SUCCESSFUL("成功","01"),
    FAIL("失败","02");

    private final String statusName;
    private final String statusVal;

    TransactionStatus(String statusName, String statusVal) {
        this.statusName = statusName;
        this.statusVal = statusVal;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusVal() {
        return statusVal;
    }

    /**
     * 根据statusVal的值获取枚举实例
     */
    public static TransactionStatus getTransactionStatus(String statusVal){
        for(TransactionStatus status:TransactionStatus.values()){
            if(StringUtils.equals(status.getStatusVal(),statusVal)){
                return status;
            }
        }
        return null;
    }



}
