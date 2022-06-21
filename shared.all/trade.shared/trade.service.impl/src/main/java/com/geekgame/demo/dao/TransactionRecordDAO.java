package com.geekgame.demo.dao;

import com.geekgame.demo.dataobject.TransactionRecordDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionRecordDAO {
    int add(TransactionRecordDO recordDO);
    int update(TransactionRecordDO recordDO);
}
