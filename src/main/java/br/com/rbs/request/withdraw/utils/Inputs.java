package br.com.rbs.request.withdraw.utils;

import br.com.rbs.request.withdraw.domain.Account;
import br.com.rbs.request.withdraw.domain.AccountPsi;
import br.com.rbs.request.withdraw.domain.Transaction;
import br.com.rbs.request.withdraw.enuns.ActionEnum;
import br.com.rbs.request.withdraw.enuns.WithdrawEnum;
import br.com.rbs.request.withdraw.repository.AccountRepository;
import br.com.rbs.request.withdraw.repository.TransactionRepository;
import br.com.rbs.request.withdraw.repository.psi.AccountPsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Classe destinada a criar massa de registros
 */
//@Service
public class Inputs implements CommandLineRunner {

    private static int countDisableCard;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountPsiRepository accountPsiRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) {
        for (int i = 0; i < 1000000; i++) {
            String accountId = createCredCard();
            createAccount(accountId);
            createTransaction(accountId);
            System.out.println("Credcard criado: " + i);
        }
    }

    private void createTransaction(String accountId) {
        int qtdTransaction = getRandom();
        for (int i = 0; i < qtdTransaction; i++) {
            Transaction transaction = new Transaction(WithdrawEnum.AUTHORIZED);
            transaction.setAccountId(accountId);
            transaction.setAmount(new BigDecimal("1.20"));
            transaction.setAction(ActionEnum.WITHDRAW.getAction());
            transactionRepository.save(transaction);
        }
    }

    private void createAccount(String accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAvailableAmount(getAmount());
        accountRepository.save(account);
    }

    private String createCredCard() {
        AccountPsi accountPsi = new AccountPsi();
        accountPsi.setCardNumber(getCardNumber());
        accountPsi.setAccountId(SecureRandomNumber.generateAuthorizationCode());
        accountPsi.setEnable(isEnable());
        accountPsiRepository.save(accountPsi);
        return accountPsi.getAccountId();
    }

    private long getCardNumber() {
        String cardNumer = String.valueOf(getRandom());
        if (cardNumer != "0") {
            cardNumer = String.valueOf(getRandom());
        }

        for (int i = 0; i < 15; i++) {
            cardNumer += String.valueOf(getRandom());
        }
        return Long.valueOf(cardNumer);
    }

    private BigDecimal getAmount() {
        return new BigDecimal((int) (Math.random() * 10000 + 1));
    }

    private int getRandom() {
        return (int) (Math.random() * 9 + 1);
    }

    private boolean isEnable() {
        if (countDisableCard++ == 100) {
            countDisableCard = 0;
            return false;
        }
        return true;
    }
}