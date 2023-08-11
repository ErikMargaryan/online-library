package com.library.rest.assembler;

import com.library.dto.response.LibraryResponseDto;
import com.library.rest.controller.LibraryController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class LibraryModelAssembler extends AbstractRepresentationModelAssembler<LibraryResponseDto, LibraryResponseDto> {

    public LibraryModelAssembler() {
        super(LibraryController.class, LibraryResponseDto.class);
    }

    @Override
    public LibraryResponseDto toModel(LibraryResponseDto dto) {
        dto.add(linkTo(WebMvcLinkBuilder.methodOn(LibraryController.class)
                .getLibraryById(dto.getId()))
                .withSelfRel().expand());
        return dto;
    }
}
