package com.DrvierService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DrvierService.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    // Additional query methods can be defined here if needed
	 boolean existsByContactNo(String contactNo);

	    // Check if a Driver with the same license number already exists
	    boolean existsByLicenseNo(String licenseNo);
}
