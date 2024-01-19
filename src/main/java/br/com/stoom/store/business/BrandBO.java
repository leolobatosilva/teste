package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandBO implements IBrandBO {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findAll(){
        return brandRepository.findAll();
    }

    @Override
    public List<Brand> findByActive(boolean active) {
        return null;
    }

}
