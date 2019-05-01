package br.com.rbs.request.withdraw.controller;

import br.com.rbs.request.withdraw.domain.AccountPsi;
import br.com.rbs.request.withdraw.service.psi.AccountPsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/psi")
public class PsiController {

    @Autowired
    private AccountPsiService service;

    @ResponseBody
    @GetMapping(value = "/accounts", params = {"cardNumber", "enable"})
    public ResponseEntity<String> getAccount(@RequestParam("cardNumber") Long cardNumber, @RequestParam("enable") boolean enable) {
        Object body;
        HttpStatus status = HttpStatus.OK;

        try {
            String accountId = service.getAccount(cardNumber, enable);

            if (accountId == null) {
                body = "cardNumber: " + cardNumber + " não localizado.";
                status = HttpStatus.NO_CONTENT;
            } else {
                body = accountId;
            }
        } catch (Exception e) {
            e.printStackTrace();
            body = "Falha interna";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(body, status);
    }

    @ResponseBody
    @GetMapping(value = "/creditCard", params = {"page", "size"})
    public ResponseEntity<String> getCreditCard(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Object body;
        HttpStatus status = HttpStatus.OK;

        try {
            List<AccountPsi> accountPsiList = service.geCreditCard(PageRequest.of(page, size));

            if (accountPsiList.isEmpty()) {
                body = "Não existem cartões";
                status = HttpStatus.NO_CONTENT;
            } else {
                body = accountPsiList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            body = "Falha interna";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(body, status);
    }
}