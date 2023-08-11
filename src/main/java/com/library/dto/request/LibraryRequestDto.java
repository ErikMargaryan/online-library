package com.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryRequestDto implements Serializable {
    @NotBlank(message = "Name may not be empty, null or blank")
    private String name;
    @NotBlank(message = "Address may not be empty, null or blank")
    private String address;
    @NotBlank(message = "Phone Number may not be empty, null or blank")
    private String phoneNumber;
    @NotBlank(message = "Website may not be empty, null or blank")
    private URL website;
}
