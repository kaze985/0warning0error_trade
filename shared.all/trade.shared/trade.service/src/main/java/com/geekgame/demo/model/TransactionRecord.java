package com.geekgame.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 交易记录模型
 */
public class TransactionRecord implements Serializable {
    /**
     * 交易记录id
     */
    private String id;
    /**
     * 付款方账户
     */
    private String payer;
    /**
     * 收款方账户
     */
    private String payee;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 交易状态
     */
    private TransactionStatus status;
    /**
     * 交易状态名称
     */
    private String statusName;
    /**
     * 交易时间
     */
    private LocalDateTime gmtCreated;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
