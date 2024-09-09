package com.DrvierService.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Driver name is mandatory")
    @Size(max = 100, message = "Driver name should not exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 255, message = "Address should not exceed 255 characters")
    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @NotBlank(message = "Contact number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Contact number must be 10 digits")
    @Column(name = "contact_no", nullable = false, length = 10, unique = true)
    private String contactNo;

    @NotBlank(message = "License number is mandatory")
    @Size(max = 50, message = "License number should not exceed 50 characters")
    @Column(name = "license_no", nullable = false, length = 50, unique = true)
    private String licenseNo;

    
}
