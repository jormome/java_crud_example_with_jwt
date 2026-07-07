package com.jmorell.spring.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Cuando el navegador pide /login, devolvemos la vista login.html.
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
