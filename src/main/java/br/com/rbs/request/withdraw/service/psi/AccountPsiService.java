package br.com.rbs.request.withdraw.service.psi;

import br.com.rbs.request.withdraw.domain.AccountPsi;
import br.com.rbs.request.withdraw.repository.psi.AccountPsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountPsiService {

    @Autowired
    private AccountPsiRepository repository;

    public String getAccount(Long cardNumber, boolean enable) {
        String accountId = null;

        Optional<AccountPsi> accountPsi = repository.findByCardNumberAndEnable(cardNumber, enable);
        if (accountPsi.isPresent())
            accountId = accountPsi.get().getAccountId();

        return accountId;
    }

    public List<AccountPsi> geCreditCard(PageRequest pageRequest) {
        Page<AccountPsi> accountPsiPage = repository.findAll(pageRequest);
        return accountPsiPage.getContent();
    }
}