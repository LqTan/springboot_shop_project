package vn.melowyeti.spring.spring_ecommerce_project.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vn.melowyeti.spring.spring_ecommerce_project.dao.RoleRepository;
import vn.melowyeti.spring.spring_ecommerce_project.entity.Role;
import vn.melowyeti.spring.spring_ecommerce_project.entity.User;
import vn.melowyeti.spring.spring_ecommerce_project.service.UserService;
import vn.melowyeti.spring.spring_ecommerce_project.web.RegisterUser;


import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/register")
public class RegisterController {
    UserService userService;
    RoleRepository roleService;
    @Autowired
    public RegisterController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleService = roleRepository;
    }

    @GetMapping("/showRegisterForm")
    public String showRegisterForm(Model model) {
        RegisterUser ru = new RegisterUser();
        model.addAttribute("registerUser", ru);
        return "register/form";
    }
    @InitBinder
    public void initBinder(WebDataBinder data) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        data.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @PostMapping("/process")
    public String process(@Valid @ModelAttribute RegisterUser registerUser,
                          BindingResult result,
                          Model model,
                          HttpSession session) {
        String username = registerUser.getUsername();
        if (result.hasErrors()) {
            return "register/form";
        }
        User user = this.userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("registerUser", new RegisterUser());
            model.addAttribute("my_error", "Tai khoan da ton tai");
            return "register/form";
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(bcrypt.encode(registerUser.getPassword()));
        user.setEmail(registerUser.getEmail());

        Role defaultRole = this.roleService.findByName("ROLE_USER");
        Collection<Role> roles = new ArrayList<>();
        roles.add(defaultRole);
        user.setRoles(roles);

        this.userService.save(user);

        session.setAttribute("myuser", user);
        return "register/confirmation";
    }
}
