package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;

import java.util.List;

public interface ICategoryBO{
    List<Category> findAll();
    List<Category> findByActive(boolean active);
}
