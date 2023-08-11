package com.library.validation.validator;

import com.library.service.UserService;
import com.library.validation.annotation.UniqueEmailValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DuplicateEmailValidator implements ConstraintValidator<UniqueEmailValidation, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userService.findAllUsers(Pageable.unpaged()).stream().noneMatch(userEntity -> userEntity
                .getEmail().equals(email));
    }
}
