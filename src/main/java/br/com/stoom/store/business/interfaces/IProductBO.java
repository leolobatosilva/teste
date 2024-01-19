package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Product;

import java.util.List;

public interface IProductBO {

    List<Product> findByActive(boolean active);

    List<Product> findByBrand(Long brand);
    List<Product> findByCategory(Long category);
}

