package com.library.rest.assembler;

import com.library.dto.response.PurchaseResponseDto;
import com.library.rest.controller.PurchaseController;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PurchaseModelAssembler extends AbstractRepresentationModelAssembler<PurchaseResponseDto, PurchaseResponseDto> {

    protected PurchaseModelAssembler() {
        super(PurchaseController.class, PurchaseResponseDto.class);
    }

    @Override
    public PurchaseResponseDto toModel(PurchaseResponseDto dto) {
        dto.add(linkTo(WebMvcLinkBuilder.methodOn(PurchaseController.class)
                .getPurchasesByUserId(dto.getUserId(), Pageable.unpaged()))
                .withSelfRel().expand());
        return dto;
    }
}
