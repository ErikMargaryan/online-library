package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryRequestDto implements Serializable {
    @Schema(example = "NPUA Library")
    @NotBlank(message = "Name may not be empty, null or blank")
    private String name;
    @Schema(example = "Teryan")
    @NotBlank(message = "Address may not be empty, null or blank")
    private String address;
    @Schema(example = "+374123456")
    @NotBlank(message = "Phone Number may not be empty, null or blank")
    private String phoneNumber;
    @Schema(example = "https://NPUA.com/")
    private URL website;
}
