package br.com.rbs.request.withdraw.service;

import br.com.rbs.request.withdraw.domain.Account;
import br.com.rbs.request.withdraw.domain.Transaction;
import br.com.rbs.request.withdraw.dto.RequestDto;
import br.com.rbs.request.withdraw.dto.ResponseDto;
import br.com.rbs.request.withdraw.enuns.WithdrawEnum;
import br.com.rbs.request.withdraw.exception.BlockedAccountException;
import br.com.rbs.request.withdraw.exception.InsuficientFundsException;
import br.com.rbs.request.withdraw.exception.InvalidAccountException;
import br.com.rbs.request.withdraw.exception.RequestException;
import br.com.rbs.request.withdraw.repository.AccountRepository;
import br.com.rbs.request.withdraw.utils.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class WithdrawService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawService.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PsiService psiService;

    @Autowired
    private AccountRepository accountRepository;

    public ResponseDto withdraw(String message) {
        Transaction transaction = new Transaction(WithdrawEnum.AUTHORIZED, Cache.nextAuthorizationCode());
        LOGGER.info("WithDrawServer-WithdrawService:Iniciando processamento traceCode={}", transaction.getTraceCode());

        try {
            RequestDto requestDto = validationService.getRequestDto(message, transaction);

            transaction.setAccountId(psiService.getAccount(requestDto.getCardnumber(), transaction.getTraceCode()));

            withdraw(transaction);
        } catch (InvalidAccountException e) {
            transaction.setCode(WithdrawEnum.INVALID_ACCOUNT);
        } catch (InsuficientFundsException e) {
            transaction.setCode(WithdrawEnum.INSUFFICIENT_FUNDS);
        } catch (RequestException e) {
            transaction.setCode(WithdrawEnum.PROCESSING_ERROR);
        } catch (ParseException e) {
            transaction.setCode(WithdrawEnum.PROCESSING_ERROR);
        } catch (Exception e) {
            transaction.setCode(WithdrawEnum.PROCESSING_ERROR);
        }

        transactionService.postTransaction(transaction);

        LOGGER.info("WithDrawServer-WithdrawService:Saque finalizado. traceCode={}", transaction.getTraceCode());
        return new ResponseDto(transaction);
    }

    public void withdraw(Transaction transaction) throws InvalidAccountException, InsuficientFundsException, BlockedAccountException {
        LOGGER.info("WithDrawServer-WithdrawService:Iniciando saque traceCode={}", transaction.getTraceCode());
        Account account = accountRepository.findById(transaction.getAccountId()).get();

        if (account == null) {
            LOGGER.info("WithDrawServer-WithdrawService:Falha na recuperação da conta traceCode={}", transaction.getTraceCode());
            throw new InvalidAccountException("Account not found");
        }

        if (account.isBlockBalance()) {
            LOGGER.info("WithDrawServer-WithdrawService:Conta bloqueada para saque traceCode={}", transaction.getTraceCode());
            throw new BlockedAccountException("blocked account");
        } else {
            LOGGER.info("WithDrawServer-WithdrawService:Efetuando saque traceCode={}", transaction.getTraceCode());
            account.setBlockBalance(Boolean.TRUE);
            accountRepository.save(account);
            LOGGER.info("WithDrawServer-WithdrawService:Saldo bloqueado traceCode={}", transaction.getTraceCode());

            LOGGER.info("WithDrawServer-WithdrawService:Validando saldo traceCode={}", transaction.getTraceCode());
            boolean isWithdraw = account.isWithdraw(transaction.getAmount());
            if (isWithdraw) {
                LOGGER.info("WithDrawServer-WithdrawService:Saldo disponível traceCode={}", transaction.getTraceCode());
                account.Withdraw(transaction.getAmount());
            }

            LOGGER.info("WithDrawServer-WithdrawService:Atualizando conta e liberando saldo. traceCode={}", transaction.getTraceCode());
            accountRepository.save(account);

            if (!isWithdraw) {
                LOGGER.info("WithDrawServer-WithdrawService:Saldo não disponível. traceCode={}", transaction.getTraceCode());
                throw new InsuficientFundsException("Insuficient funds");
            }
        }
    }
}