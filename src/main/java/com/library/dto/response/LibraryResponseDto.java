package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponseDto extends RepresentationModel<LibraryResponseDto> implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private URL website;
    private List<BookResponseDto> books;
}
