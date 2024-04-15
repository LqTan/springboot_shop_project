package vn.melowyeti.spring.spring_ecommerce_project.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import vn.melowyeti.spring.spring_ecommerce_project.dao.OrderItemRepository;
import vn.melowyeti.spring.spring_ecommerce_project.dao.OrderRepository;
import vn.melowyeti.spring.spring_ecommerce_project.dao.ProductRepository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Order;
import vn.melowyeti.spring.spring_ecommerce_project.entity.OrderItem;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Product;
import vn.melowyeti.spring.spring_ecommerce_project.entity.User;
import vn.melowyeti.spring.spring_ecommerce_project.service.HtmlEmailSender;
import vn.melowyeti.spring.spring_ecommerce_project.service.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DetailController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private HtmlEmailSender htmlEmailSender;
    @PostMapping("/detail")
    public String returnCartPage(@RequestParam("productName") String name, Model model) {
        Product existingProduct = productRepository.findProductByProductName(name);
        if (existingProduct != null) {
            model.addAttribute("product", existingProduct);
        }
        return "detail";
    }
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productName") String name) {
//        Product product = productRepository.findProductByProductName(name);
//        User user = userService.getLoggedInUser();
//        Order order = orderRepository.findOrderByUser(user);
//        if (order == null) {
//            order = new Order(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0, "nope", user);
//        }
//        //Order order = new Order(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0, "nope", user);
//        orderRepository.save(order);
//
//        List<Order> orders = new ArrayList<>();
//        List<Product> products = new ArrayList<>();
//
//        orders.add(order);
//        products.add(product);
//
//        order.setProducts(products);
//        product.setOrders(orders);
//
//        productRepository.save(product);
//
//        order.getProducts().forEach(product1 -> {
//            System.out.println(product1);
//        });
        Product product = productRepository.findProductByProductName(name);
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findOrderByUserAndStatus(user, "nope");
        if (order == null) {
            order = new Order("", "", "", 0, "nope", user);
            orderRepository.save(order);
        }
        OrderItem orderItem = orderItemRepository.findOrderItemByProductAndUser(product, user, order);
        if (orderItem == null) {
            orderItem = new OrderItem(product, user, order, 0);
        }
        orderItem.setQuantity(orderItem.getQuantity() + 1);
        orderItemRepository.save(orderItem);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String returnCart(Model model) {
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findOrderByUserAndStatus(user,"nope");
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByUser(user, order);
        orderItems.forEach(orderItem -> {
            System.out.println(orderItem.getProduct_id().getProduct_name());
//            orderItem.getProduct_id().getId();
//            orderItem.getProduct_id().getProduct_name();
//            orderItem.getProduct_id().getProduct_price();
//            orderItem.getQuantity();
        });
        model.addAttribute("orderItems", orderItems);

        return "cart";
    }
    @PostMapping("/order-item-modifier")
    public String modifyOrderItem(@RequestParam("orderItemProductNameForModify") String name, Model model) {
        User user = userService.getLoggedInUser();
        Product product = productRepository.findProductByProductName(name);
        Order order = orderRepository.findOrderByUserAndStatus(user, "nope");
        OrderItem orderItem = orderItemRepository.findOrderItemByProductAndUser(product, user, order);
        if (orderItem != null) {
            model.addAttribute("orderItem", orderItem);
        }
        return "order-item-modify";
    }
    @PostMapping("/order-item-modify-submit")
    public String submitModifyOrderItem(@RequestParam("quantity") int quantity,
                                        @RequestParam("productName") String productName) {
        User user = userService.getLoggedInUser();
        Product product = productRepository.findProductByProductName(productName);
        Order order = orderRepository.findOrderByUserAndStatus(user, "nope");
        OrderItem orderItem = orderItemRepository.findOrderItemByProductAndUser(product, user, order);
        if (orderItem != null) {
            orderItem.setQuantity(quantity);
        }
        orderItemRepository.save(orderItem);
        return "redirect:/cart";
    }
    @PostMapping("/order-item-delete")
    public String deleteOrderItem() {
        return "redirect:/";
    }
    @GetMapping("/checkout")
    public String returnCheckout(Model model) {
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findOrderByUserAndStatus(user, "nope");
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByUser(user, order);
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getProduct_id().getProduct_price() * orderItem.getQuantity();
        }
        model.addAttribute("total", total);
        return "checkout";
    }
    @PostMapping("/checkout-submit")
    public String checkoutSubmit(@RequestParam("fullname") String fullname,
                                 @RequestParam("address") String address,
                                 @RequestParam("phone_number") String phone_number,
                                 @RequestParam("total") double total) throws MessagingException {
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findOrderByUserAndStatus(user, "nope");
//        if (order == null) {
//            //order = new Order(fullname, address, phone_number, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), total, "nope", user);
//        }
        user.setEmail("lequantan1974@gmail.com");
        userService.save(user);
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByUser(user, order);
        String content = "Xin chao ban: " + user.getUsername() + "\n" +
                "Thong tin don hang cua ban: \n";
        for (OrderItem orderItem : orderItems) {
            content += "- " + orderItem.getProduct_id().getProduct_name() + " so luong: " + orderItem.getQuantity() + "\n";
        }
        content += "Tong tien: " + total + "\n";
        content += "Cam on quy khach da ghe tham ung ho san pham cua shop :3";
        htmlEmailSender.sendEmail(user.getEmail(), "Xac nhan don hang", content);

        order.setFullname(fullname);
        order.setAddress(address);
        order.setPhone_number(phone_number);
        order.setTotal(total);
        order.setStatus("OK");
        orderRepository.save(order);

        return "redirect:/";
    }
}
