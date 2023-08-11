package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto extends RepresentationModel<RoleResponseDto> implements Serializable {
    private Long id;
    private String name;
    private String description;
}
