package br.com.stoom.store.business;

import br.com.stoom.store.model.*;
import br.com.stoom.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductBOTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductBO productBO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProduct() {

        Category category = new Category(1L, "Eletrônicos", true,  new ArrayList<>());
        Brand brand = new Brand(1L, "Samsung", true, new ArrayList<>());

        Product product = new Product(1L, "Produto A", "Produto A", new BigDecimal("20.0"), true, category, brand);

        brand.getProducts().add(product);
        category.getProducts().add(product);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productBO.createProduct(product);

        assertEquals(product, result);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        Category category = new Category(1L, "Eletrônicos", true, new ArrayList<>());
        Brand brand = new Brand(1L, "Samsung", true, new ArrayList<>());
        Product updatedProduct = new Product(1L, "Produto A", "Produto A", new BigDecimal("20.0"), true, category, brand);
        updatedProduct.setId(productId);

        category.getProducts().add(updatedProduct);
        brand.getProducts().add(updatedProduct);

        Optional<Product> existingProduct = Optional.of(new Product(1L, "Produto A", "Produto A", new BigDecimal("20.0"), true, category, brand));

        when(productRepository.findById(productId)).thenReturn(existingProduct);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Optional<Product> result = productBO.updateProduct(productId, updatedProduct);

        assertNotNull(result);
        assertEquals(productId, result.get().getId());
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        Category category = new Category(1L, "Eletrônicos", true, new ArrayList<>());
        Brand brand = new Brand(1L, "Samsung", true, new ArrayList<>());
        Product product = new Product(1L, "Produto A", "Produto A", new BigDecimal("20.0"), true, category, brand);

        category.getProducts().add(product);
        brand.getProducts().add(product);

        Optional<Product> existingProduct = Optional.of(product);

        when(productRepository.findById(productId)).thenReturn(existingProduct);

        assertDoesNotThrow(() -> productBO.deleteProduct(productId));
    }

    @Test
    void testSearchProducts() {
        Category category1 = new Category(1L, "Eletrônicos", true, new ArrayList<>());
        Brand brand1 = new Brand(1L, "Samsung", true, new ArrayList<>());

        Category category2 = new Category(2L, "Info", true, new ArrayList<>());
        Brand brand2 = new Brand(2L, "Apple", true, new ArrayList<>());

        List<Product> productList = Arrays.asList(
                new Product(1L, "Produto A", "Produto A", new BigDecimal("42.50"), true, category1, brand1),
                new Product(2L, "Produto B", "Produto B", new BigDecimal("421.50"), true, category1, brand1)
        );

        List<Product> productList2 = Arrays.asList(
                new Product(3L, "Produto C", "Produto C", new BigDecimal("42.220"), true, category2, brand2),
                new Product(4L, "Produto D", "Produto BD", new BigDecimal("421.220"), true, category2, brand2)
        );

        category1.getProducts().addAll(productList);
        brand1.getProducts().addAll(productList);

        category2.getProducts().addAll(productList2);
        brand2.getProducts().addAll(productList2);

        Page<Product> page = new PageImpl<>(productList);

        ProductFilter productFilter = new ProductFilter();
        productFilter.setActive(true);
        productFilter.setCategoryId(1L);

        Specification<Product> spec = ProductSpecification.filterProducts(productFilter);
        when(productRepository.findAll(spec, PageRequest.of(0, 10))).thenReturn(page);

        List<Product> result = productBO.searchProducts(productFilter);

    }

    @Test
    void testFindByActive() {
        boolean active = true;
        Category category = new Category(1L, "Eletrônicos", true, new ArrayList<>());
        Brand brand = new Brand(1L, "Samsung", true, new ArrayList<>());
        Product product1 = new Product(1L, "Produto A", "Produto A", new BigDecimal("20.0"), true, category, brand);
        Product product2 = new Product(2L, "Produto B", "Produto B", new BigDecimal("20.0"), true, category, brand);

        category.getProducts().addAll(Arrays.asList(product1, product2));
        brand.getProducts().addAll(Arrays.asList(product1, product2));

        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findByActive(active)).thenReturn(productList);

        List<Product> result = productBO.findByActive(active);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindByBrand() {
        Long brandId = 1L;
        Category category = new Category(1L, "Eletrônicos", true, new ArrayList<>());
        Brand brand = new Brand(1L, "Samsung", true, new ArrayList<>());
        Product product1 = new Product(1L, "Produto A", "Produto A", new BigDecimal("20.0"), true, category, brand);
        Product product2 = new Product(2L, "Produto B", "Produto B", new BigDecimal("20.0"), true, category, brand);

        category.getProducts().addAll(Arrays.asList(product1, product2));
        brand.getProducts().addAll(Arrays.asList(product1, product2));

        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findByBrand(brandId)).thenReturn(productList);

        List<Product> result = productBO.findByBrand(brandId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindByCategory() {
        Long categoryId = 1L;
        Category category = new Category(1L, "Eletrônicos", true, new ArrayList<>());
        Brand brand = new Brand(1L, "Samsung", true, new ArrayList<>());
        Product product1 = new Product(1L, "Produto A", "Produto A", new BigDecimal("20.0"), true, category, brand);
        Product product2 = new Product(2L, "Produto B", "Produto B", new BigDecimal("20.0"), true, category, brand);

        category.getProducts().addAll(Arrays.asList(product1, product2));
        brand.getProducts().addAll(Arrays.asList(product1, product2));

        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findByCategory(categoryId)).thenReturn(productList);

        List<Product> result = productBO.findByCategory(categoryId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

}