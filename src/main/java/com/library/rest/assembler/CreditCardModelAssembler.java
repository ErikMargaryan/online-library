package com.library.rest.assembler;

import com.library.dto.response.CreditCardResponseDto;
import com.library.rest.controller.CreditCardController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CreditCardModelAssembler
        extends AbstractRepresentationModelAssembler<CreditCardResponseDto, CreditCardResponseDto> {

    public CreditCardModelAssembler() {
        super(CreditCardController.class, CreditCardResponseDto.class);
    }

    @Override
    public CreditCardResponseDto toModel(CreditCardResponseDto dto) {
        dto.add(linkTo(WebMvcLinkBuilder.methodOn(CreditCardController.class)
                .getCreditCardById(dto.getId()))
                .withSelfRel().expand());
        return dto;
    }
}
