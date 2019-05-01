package br.com.rbs.request.withdraw.controller;

import br.com.rbs.request.withdraw.dto.CardBalanceDto;
import br.com.rbs.request.withdraw.enuns.ActionEnum;
import br.com.rbs.request.withdraw.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/balances/{cardnumber}")
    public ResponseEntity<CardBalanceDto> getCardBalance(@PathVariable("cardnumber") Long cardnumber) {
        Object body;
        HttpStatus status = HttpStatus.OK;

        try {
            CardBalanceDto cardBalanceDto = accountService.getCardBalance(cardnumber, ActionEnum.WITHDRAW,
                    PageRequest.of(0, 10, Sort.by("creationDate").descending()));

            if (cardBalanceDto == null) {
                body = "cardNumber: " + cardnumber + " não localizado.";
                status = HttpStatus.NO_CONTENT;
            } else {
                body = cardBalanceDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
            body = "Falha interna";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(body, status);
    }
}