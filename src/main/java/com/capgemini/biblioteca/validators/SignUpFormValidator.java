package com.capgemini.biblioteca.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.capgemini.biblioteca.model.Usuario;
import com.capgemini.biblioteca.services.UsuarioService;

@Component
public class SignUpFormValidator implements Validator{

	@Autowired
    private UsuarioService usersService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Usuario user = (Usuario) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Error.empty");
        if (user.getUsername().length() < 3 || user.getUsername().length() > 24) {
            errors.rejectValue("username", "Error.signup.username.length");}

        if (usersService.getUserByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Error.signup.username.duplicate");}
        if (user.getName().length() < 2 || user.getName().length() > 24) {
            errors.rejectValue("name", "Error.signup.name.length");}
        if (user.getLastName().length() < 5 || user.getLastName().length() > 24) {
            errors.rejectValue("lastName", "Error.signup.lastName.length");}
        if (user.getPassword().length() < 4 || user.getPassword().length() > 24) {
            errors.rejectValue("password", "Error.signup.password.length");}
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm",
                    "Error.signup.passwordConfirm.coincidence");}
	}

}
