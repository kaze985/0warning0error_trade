package com.geekgame.demo.api;


import com.geekgame.demo.model.Result;
import com.geekgame.demo.model.TransactionRecord;
import com.geekgame.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/trade")
public class TransactionApi {
    @Autowired
    private TransactionService service;

    @PostMapping(path = "/transfer")
    public Result<TransactionRecord> transfer(@RequestBody TransactionRecord record){
        return null;
    }

}
