package com.ayub.assessment.api.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController @RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service){this.service=service;}
    @GetMapping("/{id}") public Product get(@PathVariable Long id){return service.find(id);}
    @PostMapping public Product create(@RequestBody Product product){return service.save(product);}
    @GetMapping("/analytics") public Object analytics(@RequestParam(defaultValue="0") BigDecimal min,@RequestParam(defaultValue="999999999") BigDecimal max){return service.analytics(min,max);}
    @GetMapping("/health") public ResponseEntity<String> health(){return ResponseEntity.ok("UP");}
}
