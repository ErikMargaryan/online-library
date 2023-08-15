package com.library.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicPage {
    @GetMapping("/public")
    public String publicPage() {
        return "public.html";
    }
}
