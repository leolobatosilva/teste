package br.com.stoom.store.repository;

import br.com.stoom.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p JOIN p.brand b WHERE b.id = :brand AND b.active = true AND p.active = true")
    List<Product> findByBrand(@Param("brand") Long brand);

    @Query("SELECT p FROM Product p WHERE p.active = :active")
    List<Product> findByActive(@Param("active") boolean active);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.id = :categoryId AND c.active = true AND p.active = true")
    List<Product> findByCategory(@Param("categoryId") Long categoryId);

}