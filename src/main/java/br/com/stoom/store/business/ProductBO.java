package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.model.ProductFilter;
import br.com.stoom.store.model.ProductSpecification;
import br.com.stoom.store.repository.ProductRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductBO implements IProductBO {

    @Autowired
    private ProductRepository productRepository;
    private static final Logger logger = Logger.getLogger(Product.class.getName());


    public List<Product> findAll() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao buscar todos os produtos", e);
            throw new RuntimeException("Erro ao buscar todos os produtos", e);
        }
    }

    public Optional<Product> findById(Long id) {
        try {
            Optional<Product> productOptional = productRepository.findById(id);

            if (!productOptional.isPresent()) {
                logger.warning("Produto não encontrado com o ID: " + id);
            }

            return productOptional;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao buscar o produto com o ID " + id, e);
            throw new RuntimeException("Erro ao buscar o produto", e);
        }
    }


    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar o produto", e);
            throw new RuntimeException("Erro ao criar o produto", e);
        }
    }


    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        try {
            Optional<Product> existingProduct = productRepository.findById(id);

            if (existingProduct.isPresent()) {
                updatedProduct.setId(id);
                Product savedProduct = productRepository.save(updatedProduct);
                return Optional.of(savedProduct);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao atualizar o produto com o ID " + id, e);
            throw new RuntimeException("Erro ao atualizar", e);
        }
    }


    public void deleteProduct(Long id) {
        try {
            Optional<Product> existingProduct = productRepository.findById(id);

            if (existingProduct.isPresent()) {
                productRepository.deleteById(id);
                logger.info("Produto com o ID " + id + " excluído com sucesso!");
            } else {
                String mensagemErro = "Produto não encontrado com o ID: " + id;
                logger.warning(mensagemErro);
                throw new RuntimeException(mensagemErro);
            }
        } catch (Exception e) {
            String mensagemErro = "Erro ao excluir o produto com o ID " + id;
            logger.log(Level.SEVERE, mensagemErro, e);
            throw new RuntimeException(mensagemErro, e);
        }
    }



    public List<Product> searchProducts(ProductFilter productFilter) {
        try {
            if (productFilter == null) {
                throw new IllegalArgumentException("O filtro de produtos não pode ser nulo.");
            }

            Specification<Product> spec = ProductSpecification.filterProducts(productFilter);

            if (spec == null) {
                throw new IllegalArgumentException("A especificação de filtro de produtos não pode ser nula.");
            }

            Page<Product> resultPage = productRepository.findAll(spec, Pageable.unpaged());
            if (resultPage == null || !resultPage.hasContent()) {
                return new ArrayList<>();
            }
                List<Product> productList = resultPage.getContent();

            if (productList.isEmpty()) {
                throw new NoSuchElementException("Nenhum produto encontrado com base nos critérios fornecidos.");
            }

            return productList;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar produtos.", e);
        }
    }

    @Override
    public List<Product> findByActive(boolean active) {
        try {
            if (active) {
                return productRepository.findByActive(active);
            } else {
                throw new IllegalArgumentException("O parâmetro 'active' não pode ser nulo.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao buscar produtos por ativação", e);
            throw new RuntimeException("Erro ao buscar produtos por ativação", e);
        }
    }

    @Override
    public List<Product> findByBrand(Long brand) {
        try {
            if (!StringUtils.isEmpty(brand)) {
                return productRepository.findByBrand(brand);
            } else {
                throw new IllegalArgumentException("O parâmetro 'brand' não pode ser nulo ou vazio.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao buscar produtos por marca", e);
            throw new RuntimeException("Erro ao buscar produtos por marca", e);
        }
    }

    @Override
    public List<Product> findByCategory(Long category) {
        try {
            if (!StringUtils.isEmpty(category)) {
                return productRepository.findByCategory(category);
            } else {
                throw new IllegalArgumentException("O parâmetro 'brand' não pode ser nulo ou vazio.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao buscar produtos por marca", e);
            throw new RuntimeException("Erro ao buscar produtos por marca", e);
        }
    }
}
