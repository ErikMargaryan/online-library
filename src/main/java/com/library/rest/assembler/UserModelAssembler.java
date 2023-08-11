package com.library.rest.assembler;

import com.library.dto.response.UserResponseDto;
import com.library.rest.controller.UserController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UserModelAssembler extends AbstractRepresentationModelAssembler<UserResponseDto, UserResponseDto> {

    public UserModelAssembler() {
        super(UserController.class, UserResponseDto.class);
    }

    @Override
    public UserResponseDto toModel(UserResponseDto dto) {
        dto.add(linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUser(dto.getId()))
                .withSelfRel().expand());
        return dto;
    }
}
