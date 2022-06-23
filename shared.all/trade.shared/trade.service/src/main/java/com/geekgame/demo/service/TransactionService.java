package com.geekgame.demo.service;

import com.geekgame.demo.model.TransactionRecord;

public interface TransactionService {

    int add(TransactionRecord record);

    TransactionRecord transfer(TransactionRecord record);

    int update(TransactionRecord record);
}
