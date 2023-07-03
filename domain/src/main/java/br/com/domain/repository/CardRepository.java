package br.com.domain.repository;

import br.com.domain.entity.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sourabh Sharma
 */
@Repository
public interface CardRepository extends JpaRepository<Card, String> {

}
