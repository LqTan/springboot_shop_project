package vn.melowyeti.spring.spring_ecommerce_project.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Lob;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.annotation.MultipartConfig;
import org.hibernate.Hibernate;
import org.hibernate.engine.jdbc.BlobImplementer;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.melowyeti.spring.spring_ecommerce_project.dao.CategoryRepository;
import vn.melowyeti.spring.spring_ecommerce_project.dao.ProductRepository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Category;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Product;
import vn.melowyeti.spring.spring_ecommerce_project.service.LobHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

@Controller
//@MultipartConfig(maxFileSize = 1024 * 1024 * 5, // 5MB
//        maxRequestSize = 1024 * 1024 * 10, // 10MB
//        location = "/tmp/uploads")
@MultipartConfig
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @GetMapping
    public String returnAdminHomePage() {
        return "adminPage/adminHomePage";
    }
    @GetMapping("/products")
    public String returnAdminProductsPage(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "adminPage/adminProducts";
    }

    @PostMapping("/product-modify")
    public String returnAdminProductModifier(@RequestParam("productId") int productId, Model model) {
        Product product = productRepository.findById(productId).orElse(null);
        model.addAttribute("product", product);
        return "adminPage/adminProductModifier";
    }

    @PostMapping("/product-modify-submit")
    public String returnAdminProductModifierSubmit(@RequestParam("productName") String productName,
                                                   @RequestParam("productPrice") String productPrice,
                                                   @RequestParam("productDetail") String productDetail,
                                                   @RequestParam("productImg")MultipartFile productImg) throws IOException {
        Product existingProduct = productRepository.findProductByProductName(productName);

        if (existingProduct != null) {
            existingProduct.setProduct_name(productName);
            existingProduct.setProduct_price(Double.parseDouble(productPrice));
            existingProduct.setProduct_detail(productDetail);
        }
        if (!productImg.isEmpty()) {
            String fileName = productImg.getOriginalFilename();
            String projectPath = System.getProperty("user.dir");
            String filePath = projectPath + File.separator
                    + "src" + File.separator
                    + "main" + File.separator
                    + "resources" + File.separator
                    + "static" + File.separator + fileName;
            File uploadedFile = new File(filePath);
            productImg.transferTo(uploadedFile);
            existingProduct.setProduct_img(fileName);
        }
        productRepository.save(existingProduct);
        return "adminPage/adminProducts";
    }
    @GetMapping("/product-create")
    public String returnAdminProductCreate(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "adminPage/adminProductCreate";
    }
    @PostMapping("/product-create-submit")
    public String returnDone(@RequestParam("productName") String productName,
                             @RequestParam("productPrice") String productPrice,
                             @RequestParam("productDetail") String productDetail,
                             @RequestParam("productCategory") String productCategory,
                             @RequestParam("productImg")MultipartFile productImg) throws IOException {
        Product existingProduct = productRepository.findProductByProductName(productName);
        Category existingCategory = categoryRepository.findCategoryByCategoryName(productCategory);

        if (existingProduct == null) {
            existingProduct = new Product();
            existingProduct.setProduct_name(productName);
            existingProduct.setProduct_price(Double.parseDouble(productPrice));
            existingProduct.setProduct_detail(productDetail);
            if (existingCategory != null) {
                existingProduct.setCategory(existingCategory);
            }
        }
        if (!productImg.isEmpty()) {
            String fileName = productImg.getOriginalFilename();
            String projectPath = System.getProperty("user.dir");
            String filePath = projectPath + File.separator
                    + "src" + File.separator
                    + "main" + File.separator
                    + "resources" + File.separator
                    + "static" + File.separator + fileName;
            File uploadedFile = new File(filePath);
            productImg.transferTo(uploadedFile);
            existingProduct.setProduct_img(fileName);
        }
        productRepository.save(existingProduct);
        return "/adminPage/done";
    }

    @PostMapping("/product-delete")
    public String deleteProduct(@RequestParam("productName") String name, Model model) {
        Product existingProduct = productRepository.findProductByProductName(name);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String check;
        if (existingProduct != null) {
            productRepository.delete(existingProduct);
        }
        //model.addAttribute("name", check);
        return "/adminPage/done";
    }
}
