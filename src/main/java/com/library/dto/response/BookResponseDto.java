package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto extends RepresentationModel<BookResponseDto> implements Serializable {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String isbn;
    private URL image;
    private LocalDate published;
    private String publisher;
    private BigDecimal price;
}
