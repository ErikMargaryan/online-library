package com.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
        private String firstName;
        private String lastName;
        private String phone;
        private String email;
        private String password;
        private List<String> favoriteGenres;
}