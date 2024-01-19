package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;

import java.util.List;

public interface IBrandBO {
        List<Brand> findAll();

        List<Brand> findByActive(boolean active);
}
