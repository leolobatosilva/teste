package br.com.stoom.store.repository;

import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT p FROM Brand p WHERE p.active = :active")
    List<Brand> findByActive(@Param("active") boolean active);
}