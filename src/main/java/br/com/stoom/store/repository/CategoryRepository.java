package br.com.stoom.store.repository;

import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT p FROM Category p WHERE p.active = :active")
    List<Category> findByActive(@Param("active") boolean active);
}