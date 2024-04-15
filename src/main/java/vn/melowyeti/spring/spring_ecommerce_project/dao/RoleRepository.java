package vn.melowyeti.spring.spring_ecommerce_project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByName(String name);
}
