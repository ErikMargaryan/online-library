package com.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto implements Serializable {
    @NotBlank(message = "Title may not be empty, null or blank")
    private String title;
    @NotBlank(message = "Author may not be empty, null or blank")
    private String author;
    @NotBlank(message = "Genre may not be empty, null or blank")
    private String genre;
    @NotBlank(message = "Description may not be empty, null or blank")
    private String description;
    @NotBlank(message = "ISBN may not be empty, null or blank")
    private String isbn;
    @NotBlank(message = "Image URL may not be empty, null or blank")
    private URL image;
    @NotBlank(message = "Published may not be empty, null or blank")
    private LocalDate published;
    @NotBlank(message = "Publisher may not be empty, null or blank")
    private String publisher;
    @NotBlank(message = "Price may not be empty, null or blank")
    private BigDecimal price;
}
