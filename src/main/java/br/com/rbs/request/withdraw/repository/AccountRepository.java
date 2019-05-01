package br.com.rbs.request.withdraw.repository;

import br.com.rbs.request.withdraw.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
