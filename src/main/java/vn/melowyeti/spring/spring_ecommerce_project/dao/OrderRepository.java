package vn.melowyeti.spring.spring_ecommerce_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Category;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Order;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Product;
import vn.melowyeti.spring.spring_ecommerce_project.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.user=?1 and o.status=?2")
    public Order findOrderByUserAndStatus(User user, String status);
}
