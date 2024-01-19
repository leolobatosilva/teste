package br.com.stoom.store.controller;

import br.com.stoom.store.business.CategoryBO;
import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryBO service;

    @GetMapping(value = "/")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> p = service.findAll();
        if(!p.isEmpty())
            return new ResponseEntity<>(p, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
