package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto implements Serializable {
    @Schema(example = "Jane")
    @NotBlank(message = "Title may not be empty, null or blank")
    private String title;
    @Schema(example = "John")
    @NotBlank(message = "Author may not be empty, null or blank")
    private String author;
    @Schema(example = "Alias")
    @NotBlank(message = "Genre may not be empty, null or blank")
    private String genre;
    @Schema(example = "explain book reading purpose")
    @NotBlank(message = "Description may not be empty, null or blank")
    private String description;
    @Schema(example = "123456789")
    @NotBlank(message = "ISBN may not be empty, null or blank")
    private String isbn;
    @Schema(example = "http://image_url.com/")
    @NotBlank(message = "Image URL may not be empty, null or blank")
    private URL image;
    @Schema(example = "2003-05-08")
    @NotBlank(message = "Published may not be empty, null or blank")
    private LocalDate published;
    @NotBlank(message = "Publisher may not be empty, null or blank")
    @Schema(example = "Erik")
    private String publisher;
    @Schema(example = "500")
    @NotBlank(message = "Price may not be empty, null or blank")
    private BigDecimal price;
}
