package co.com.api.credibanco.domain.repository;

import co.com.api.credibanco.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}