package com.capgemini.biblioteca.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
    String[] roles = {"ROLE_LECTOR", "ROLE_ADMIN"};
    public String[] getRoles() {
        return roles;
    }
}
