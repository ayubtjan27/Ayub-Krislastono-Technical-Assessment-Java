package com.ayub.assessment.api.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {
    @GetMapping("/products")
    public String products(){return "products";}
}
