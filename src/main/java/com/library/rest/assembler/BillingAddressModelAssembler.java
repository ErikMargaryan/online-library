package com.library.rest.assembler;

import com.library.dto.response.BillingAddressResponseDto;
import com.library.rest.controller.BillingAddressController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class BillingAddressModelAssembler
        extends AbstractRepresentationModelAssembler<BillingAddressResponseDto, BillingAddressResponseDto> {
    public BillingAddressModelAssembler() {
        super(BillingAddressController.class, BillingAddressResponseDto.class);
    }

    @Override
    public BillingAddressResponseDto toModel(BillingAddressResponseDto dto) {
        dto.add(linkTo(WebMvcLinkBuilder.methodOn(BillingAddressController.class)
                .getBillingAddressById(dto.getId()))
                .withSelfRel().expand());
        return dto;
    }
}
