package br.com.rbs.request.withdraw.service;

import br.com.rbs.request.withdraw.domain.Account;
import br.com.rbs.request.withdraw.domain.Transaction;
import br.com.rbs.request.withdraw.dto.CardBalanceDto;
import br.com.rbs.request.withdraw.dto.TransactionDto;
import br.com.rbs.request.withdraw.enuns.ActionEnum;
import br.com.rbs.request.withdraw.enuns.WithdrawEnum;
import br.com.rbs.request.withdraw.repository.AccountRepository;
import br.com.rbs.request.withdraw.utils.SecureRandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private PsiService psiService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    public CardBalanceDto getCardBalance(Long cardnumber, ActionEnum action, PageRequest pageRequest) throws Exception {
        final String traceCode = SecureRandomNumber.generateAuthorizationCode();

        final String accountId = psiService.getAccount(cardnumber, traceCode);
        return getCardBalanceDto(accountId, cardnumber, getTransactionDtoList(accountId, action, pageRequest));
    }

    private List<TransactionDto> getTransactionDtoList(String accountId, ActionEnum action, PageRequest pageRequest) {
        List<Transaction> transactions = transactionService.findByAccountIdAndAction(accountId,
                action.getAction(),
                WithdrawEnum.AUTHORIZED.getCode(),
                pageRequest);

        return transactions.stream()
                .map(transaction -> new TransactionDto(transaction.getCreationDate(), transaction.getAmount()))
                .collect(Collectors.toList());
    }

    private CardBalanceDto getCardBalanceDto(String accountId, Long cardnumber, List<TransactionDto> transactionDtoList) {
        Account account = accountRepository.findById(accountId).get();

        CardBalanceDto cardBalanceDto = new CardBalanceDto();
        cardBalanceDto.setCardnumber(cardnumber);
        cardBalanceDto.setAvailableAmount(account.getAvailableAmount());
        cardBalanceDto.setTransactions(transactionDtoList);
        return cardBalanceDto;
    }
}