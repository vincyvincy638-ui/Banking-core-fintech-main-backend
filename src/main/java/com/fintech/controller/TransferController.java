package com.fintech.controller;

import com.fintech.service.TransferService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public String transfer(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Double amount) {
        return transferService.transferMoney(from, to, amount);
    }
}