package vn.melowyeti.spring.spring_ecommerce_project.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.melowyeti.spring.spring_ecommerce_project.entity.User;

public interface UserService extends UserDetailsService {
    public User findByUsername(String username);
    public void save(User user);
    public User getLoggedInUser();
}
