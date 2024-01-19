package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.model.ProductFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductBO productService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> p = productService.findAll();
        if (!p.isEmpty())
            return new ResponseEntity<>(p, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productService.findById(id)
                .map(existingProduct -> {
                    updatedProduct.setId(id);
                    Product savedProduct = productService.updateProduct(id, updatedProduct).get();
                    return ResponseEntity.ok(savedProduct);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@ModelAttribute ProductFilter productFilter) {
        List<Product> searchResults = productService.searchProducts(productFilter);

        if (searchResults.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(searchResults);
        }
    }

    @GetMapping("brand/{idBrand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable Long idBrand) {
        try {
            List<Product> products = productService.findByBrand(idBrand);

            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("category/{idCategory}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long idCategory) {
        try {
            List<Product> products = productService.findByCategory(idCategory);

            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
