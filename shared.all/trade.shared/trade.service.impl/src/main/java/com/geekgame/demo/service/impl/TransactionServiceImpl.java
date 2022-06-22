package com.geekgame.demo.service.impl;

import com.geekgame.demo.dao.TransactionRecordDAO;
import com.geekgame.demo.dataobject.TransactionRecordDO;
import com.geekgame.demo.model.Account;
import com.geekgame.demo.model.TransactionRecord;
import com.geekgame.demo.model.TransactionStatus;
import com.geekgame.demo.service.AccountService;
import com.geekgame.demo.service.TransactionService;
import com.geekgame.demo.util.SnowflakeIdGenerator;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private SnowflakeIdGenerator generator;
    @Autowired
    private TransactionRecordDAO recordDAO;
    @DubboReference
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
    public TransactionRecord transfer(TransactionRecord record) {
        if (record == null){
            return null;
        }
        Account payerAccount = accountService.selectByAccount(record.getPayer());
        if (payerAccount.getBalance() < record.getAmount()) {
            return null;
        }

        record.setId(String.valueOf(generator.nextId()));
        record.setStatus(TransactionStatus.FAIL);
        record.setStatusName(TransactionStatus.FAIL.getStatusName());
        record.setGmtCreated(LocalDateTime.now());
        record.setGmtModified(LocalDateTime.now());

        Account payeeAccount = accountService.selectByAccount(record.getPayee());
        payerAccount.setBalance(payerAccount.getBalance() - record.getAmount());
        payeeAccount.setBalance(payeeAccount.getBalance() + record.getAmount());

        boolean updateBalance = updateBalance(payerAccount, payeeAccount);
        if (updateBalance) {
            record.setStatus(TransactionStatus.SUCCESSFUL);
            record.setStatusName(TransactionStatus.SUCCESSFUL.getStatusName());
        }
        add(record);

        return record;
    }

    @ShardingTransactionType(TransactionType.LOCAL)
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBalance(Account payerAccount,Account payeeAccount){
        accountService.update(payerAccount);
        accountService.update(payeeAccount);
        return true;
    }
}
