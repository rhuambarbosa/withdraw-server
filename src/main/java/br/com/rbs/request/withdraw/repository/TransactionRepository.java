package br.com.rbs.request.withdraw.repository;

import br.com.rbs.request.withdraw.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Page<Transaction> findByAccountIdAndActionAndCode(String accountId, String action, String code, Pageable pageable);
}
