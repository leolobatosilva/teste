package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBO implements ICategoryBO {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByActive(boolean active) {
        return null;
    }

}
