package vn.melowyeti.spring.spring_ecommerce_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.melowyeti.spring.spring_ecommerce_project.dao.ProductRepository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Product;

import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping
    public String returnHomePage(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "home";
    }
}
