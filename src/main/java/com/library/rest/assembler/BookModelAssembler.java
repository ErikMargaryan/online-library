package com.library.rest.assembler;

import com.library.dto.response.BookResponseDto;
import com.library.rest.controller.BookController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class BookModelAssembler extends AbstractRepresentationModelAssembler<BookResponseDto, BookResponseDto> {

    public BookModelAssembler() {
        super(BookController.class, BookResponseDto.class);
    }

    @Override
    public BookResponseDto toModel(final BookResponseDto dto) {
        dto.add(linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                .getBookById(dto.getId()))
                .withSelfRel().expand());
        return dto;
    }
}
