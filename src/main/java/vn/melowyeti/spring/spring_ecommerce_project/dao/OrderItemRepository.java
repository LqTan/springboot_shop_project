package vn.melowyeti.spring.spring_ecommerce_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Order;
import vn.melowyeti.spring.spring_ecommerce_project.entity.OrderItem;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Product;
import vn.melowyeti.spring.spring_ecommerce_project.entity.User;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Query("select o from OrderItem o where o.product_id=?1 and o.user_id=?2 and o.order_id=?3")
    public OrderItem findOrderItemByProductAndUser(Product product, User user, Order order);
    @Query("select o from OrderItem o where o.user_id=?1 and o.order_id=?2")
    public List<OrderItem> findOrderItemsByUser(User user, Order order);
}
