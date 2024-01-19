package br.com.stoom.store.model;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductSpecification {

    public static Specification<Product> filterProducts(ProductFilter productFilter) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            Specification<Product> categorySpec = categoryIdEqual(Optional.ofNullable(productFilter.getCategoryId()));
            Specification<Product> brandSpec = brandIdEqual(Optional.ofNullable(productFilter.getBrandId()));
            Specification<Product> priceSpec = priceBetween(Optional.ofNullable(productFilter.getMinPrice()),Optional.ofNullable(productFilter.getMaxPrice()));
            Specification<Product> activeSpec = activeEqual(Optional.ofNullable(productFilter.getActive()));


            Specification<Product> finalSpec = Specification.where(categorySpec)
                    .and(brandSpec)
                    .and(priceSpec)
                    .and(activeSpec);

            return finalSpec.toPredicate(root, query, criteriaBuilder);
        };
    }

    private static Specification<Product> categoryIdEqual(Optional<Long> categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId.map(id -> criteriaBuilder.equal(root.get("category").get("id"), id))
                        .orElse(criteriaBuilder.conjunction());
    }

    private static Specification<Product> brandIdEqual(Optional<Long> brandId) {
        return (root, query, criteriaBuilder) ->
                brandId.map(id -> criteriaBuilder.equal(root.get("brand").get("id"), id))
                        .orElse(criteriaBuilder.conjunction());
    }

    private static Specification<Product> priceBetween(Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice.flatMap(min -> maxPrice.map(max -> criteriaBuilder.between(root.get("price"), min, max)))
                        .orElse(criteriaBuilder.conjunction());
    }

    private static Specification<Product> activeEqual(Optional<Boolean> active) {
        return (root, query, criteriaBuilder) ->
                active.map(value -> criteriaBuilder.equal(root.get("active"), value))
                        .orElse(criteriaBuilder.conjunction());
    }
}
