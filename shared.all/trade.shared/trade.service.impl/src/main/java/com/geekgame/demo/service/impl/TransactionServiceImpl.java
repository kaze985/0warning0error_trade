package com.geekgame.demo.service.impl;

import com.geekgame.demo.dao.TransactionRecordDAO;
import com.geekgame.demo.dataobject.TransactionRecordDO;
import com.geekgame.demo.model.TransactionRecord;
import com.geekgame.demo.service.AccountService;
import com.geekgame.demo.service.TransactionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
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

}
