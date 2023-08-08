package com.library.csvData;

import lombok.Data;

@Data
public class UserCSVModel {
    private String name;
    private String phone;
    private String email;
    private String password;
    private String pan;
    private String expdate;
    private String cvv;
    private String address;
    private String postalZip;
    private String country;
}
