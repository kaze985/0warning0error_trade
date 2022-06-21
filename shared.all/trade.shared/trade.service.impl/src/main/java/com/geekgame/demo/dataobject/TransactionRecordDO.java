package com.geekgame.demo.dataobject;

import com.geekgame.demo.model.TransactionRecord;
import com.geekgame.demo.model.TransactionStatus;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TransactionRecordDO implements Serializable {
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
    private String status;
    /**
     * 交易时间
     */
    private LocalDateTime gmtCreated;
    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

    public TransactionRecordDO() {
    }
    public TransactionRecordDO(TransactionRecord record) {
        BeanUtils.copyProperties(record,this);
        if(record.getStatus()!=null){
            this.setStatus(record.getStatus().getStatusVal());
        }
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public TransactionRecord convertToModel(){
        TransactionRecord record = new TransactionRecord();
        BeanUtils.copyProperties(this,record);
        record.setStatus(TransactionStatus.getTransactionStatus(getStatus()));
        record.setStatusName(record.getStatus().getStatusName());
        return record;
    }
}
