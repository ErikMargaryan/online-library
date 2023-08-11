package com.library.rest.assembler;

import com.library.dto.response.RoleResponseDto;
import com.library.rest.controller.RoleController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class RoleModelAssembler extends AbstractRepresentationModelAssembler<RoleResponseDto, RoleResponseDto> {

    public RoleModelAssembler() {
        super(RoleController.class, RoleResponseDto.class);
    }

    @Override
    public RoleResponseDto toModel(RoleResponseDto dto) {
        dto.add(linkTo(WebMvcLinkBuilder.methodOn(RoleController.class)
                .getRoleById(dto.getId()))
                .withSelfRel().expand());
        return dto;
    }
}
