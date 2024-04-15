package vn.melowyeti.spring.spring_ecommerce_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import vn.melowyeti.spring.spring_ecommerce_project.dao.CategoryRepository;
import vn.melowyeti.spring.spring_ecommerce_project.dao.OrderRepository;
import vn.melowyeti.spring.spring_ecommerce_project.dao.ProductRepository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Category;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Order;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Product;
import vn.melowyeti.spring.spring_ecommerce_project.entity.User;
import vn.melowyeti.spring.spring_ecommerce_project.service.UserService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringEcommerceProjectApplication {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SpringEcommerceProjectApplication.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner commandLineRunner() {
		return args->{
//			User user = userService.getLoggedInUser();
//			Order order = orderRepository.findOrderByUser(user);
//			order.getProducts().forEach(product -> {
//				System.out.println(product);
//			});
			System.out.println("oke");
		};
	}
}