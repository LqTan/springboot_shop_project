package vn.melowyeti.spring.spring_ecommerce_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.melowyeti.spring.spring_ecommerce_project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.username=?1")
    public User findByUsername(String username);
}
