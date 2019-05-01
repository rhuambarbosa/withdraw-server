package br.com.rbs.request.withdraw.repository.psi;

import br.com.rbs.request.withdraw.domain.AccountPsi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountPsiRepository extends JpaRepository<AccountPsi, Long> {
    Optional<AccountPsi> findByCardNumberAndEnable(Long cardNumber, boolean enable);
}