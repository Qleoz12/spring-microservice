package br.com.domain.repository;

import br.com.domain.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Sourabh Sharma
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
