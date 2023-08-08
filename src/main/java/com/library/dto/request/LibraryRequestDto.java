package com.library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryRequestDto implements Serializable {
    private String name;
    private String address;
    private String phoneNumber;
    private URL website;
}
