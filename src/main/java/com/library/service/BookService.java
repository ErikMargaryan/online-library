package com.library.service;

import com.library.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService {
    private final String API_URL = "https://fakerapi.it/api/v1/books?_quantity=2&_locale=en_US";

    public ApiResponse getBooks() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(API_URL, ApiResponse.class);
    }
}
