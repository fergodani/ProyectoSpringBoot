package com.capgemini.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.services.RolesService;
import com.capgemini.biblioteca.services.SecurityService;
import com.capgemini.biblioteca.services.UsuarioService;
import com.capgemini.biblioteca.validators.SignUpFormValidator;

@Controller
public class UserController {
	
	@Autowired
    private UsuarioService usersService;

    @Autowired
    private SecurityService securityService;
	
	@Autowired
    private SignUpFormValidator signUpFormValidator;
	
	@Autowired
    private RolesService rolesService;

	@GetMapping("/login")
	public String getLoginForm() {
		return "login";
	}
	
	@GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new Usuario());
        return "signup";
    }
	
	@PostMapping("/signup")
    public String signup(@Validated Usuario user, BindingResult result) {
		System.out.println("Hola");
        signUpFormValidator.validate(user, result);
        if (result.hasErrors())
            return "signup";
        System.out.println("Hola2");
        user.setRole(rolesService.getRoles()[0]);
        usersService.saveEntity(user);
        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());
        return "redirect:/";
    }
	
}
