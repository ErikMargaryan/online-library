package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponseDto implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private URL website;
    private List<BookResponseDto> books;
}
