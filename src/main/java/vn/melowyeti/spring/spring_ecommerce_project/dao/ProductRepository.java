package vn.melowyeti.spring.spring_ecommerce_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.product_name=?1")
    public Product findProductByProductName(String product_name);
}
