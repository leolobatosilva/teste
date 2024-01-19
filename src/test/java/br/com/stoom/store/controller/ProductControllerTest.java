package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.model.ProductFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductBO productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
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
        when(productService.findAll()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList, response.getBody());
    }

    @Test
    void testGetProductById() {
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

        when(productService.findById(productList2.get(0).getId())).thenReturn(Optional.of(productList2.get(0)));

        ResponseEntity<Product> response = productController.getProductById(productList2.get(0).getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList2.get(0), response.getBody());
    }

    @Test
    void testSearchProducts() {
        ProductFilter filter = new ProductFilter();
        List<Product> searchResults = new ArrayList<>();
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

        when(productService.searchProducts(filter)).thenReturn(searchResults);

        ResponseEntity<List<Product>> response = productController.searchProducts(filter);

    }

    @Test
    void testGetProductsByBrand() {
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

        when(productService.findByBrand(brand1.getId())).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.getProductsByBrand(brand1.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList , response.getBody());
    }

    @Test
    void testGetProductsByCategory() {
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

        when(productService.findByCategory(category2.getId())).thenReturn(productList2);

        ResponseEntity<List<Product>> response = productController.getProductsByCategory(category2.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList2, response.getBody());
    }
}