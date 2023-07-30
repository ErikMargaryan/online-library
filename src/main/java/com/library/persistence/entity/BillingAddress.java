package com.library.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "billing_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "postal_zip")
    private Integer postalZip;
    @Column(name = "address")
    private String address;
    @Column(name = "country")
    private String country;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
}
