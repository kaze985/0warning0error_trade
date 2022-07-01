package com.geekgame.demo.service.impl;

import com.geekgame.demo.dao.TransactionRecordDAO;
import com.geekgame.demo.dataobject.TransactionRecordDO;
import com.geekgame.demo.model.Account;
import com.geekgame.demo.model.TransactionRecord;
import com.geekgame.demo.model.TransactionStatus;
import com.geekgame.demo.service.AccountService;
import com.geekgame.demo.service.TransactionService;
import com.geekgame.demo.util.SnowflakeIdGenerator;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private SnowflakeIdGenerator generator;
    @Autowired
    private TransactionRecordDAO recordDAO;
    @Autowired
    @Lazy
    private TransactionServiceImpl service;
    @DubboReference(timeout = 300000,retries = 0)
    private AccountService accountService;

    @Override
    public int add(TransactionRecord record) {
        if (record == null) {
            return 0;
        }
        int add = recordDAO.add(new TransactionRecordDO(record));
        if (add == 1) {
            return 1;
        }
        return 0;
    }

    @Override
    public int update(TransactionRecord record) {
        if (record == null) {
            return 0;
        }
        int update = recordDAO.update(new TransactionRecordDO(record));
        if (update == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public TransactionRecord transfer(TransactionRecord record) {
        if (record == null){
            return null;
        }
        Account payerAccount = accountService.select(record.getPayer());

        if (payerAccount.getBalance() < record.getAmount()) {
            return null;
        }

        record.setId(String.valueOf(generator.nextId()));
        record.setStatus(TransactionStatus.FAIL);
        record.setStatusName(TransactionStatus.FAIL.getStatusName());
        add(record);

        Account payeeAccount = accountService.select(record.getPayee());

        payerAccount.setBalance(payerAccount.getBalance()-record.getAmount());
        payeeAccount.setBalance(payeeAccount.getBalance()+record.getAmount());

        boolean updateBalance = service.updateBalance(payerAccount,payeeAccount);

        if (updateBalance) {
            record.setStatus(TransactionStatus.SUCCESSFUL);
            record.setStatusName(TransactionStatus.SUCCESSFUL.getStatusName());
            update(record);
        }

        return record;
    }

    @ShardingTransactionType(TransactionType.BASE)
    @GlobalTransactional(timeoutMills = 300000)
    public boolean updateBalance(Account payerAccount, Account payeeAccount) {
        int i = accountService.update(payerAccount);
        int j = accountService.update(payeeAccount);
        int a=1/0;
        if (i==1 && j==1){
            return true;
        }
        return false;
    }
}
