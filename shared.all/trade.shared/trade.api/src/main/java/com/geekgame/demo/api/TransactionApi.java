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
        Result<TransactionRecord> result = new Result<>();
        if (record == null){
            result.setMessage("record is null");
            return result;
        }
        if (record.getPayee() == null || record.getPayer() == null || record.getAmount() == null || record.getAmount() <= 0) {
            result.setMessage("Invalid transfer");
            return result;
        }
        TransactionRecord transfer = service.transfer(record);
        if (transfer == null) {
            result.setMessage("transfer fail");
            return result;
        }
        result.setSuccess(true);
        result.setData(transfer);
        return result;
    }

}
