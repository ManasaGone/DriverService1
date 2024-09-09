package com.DrvierService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DrvierService.entity.Driver;
import com.DrvierService.exception.DriverAlreadyExistsException;
import com.DrvierService.exception.DriverNotFoundException;
import com.DrvierService.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver addDriver(Driver driver) {
        if (driverRepository.existsByContactNo(driver.getContactNo())) {
            throw new IllegalArgumentException("Contact number already exists");
        }
        if (driverRepository.existsByLicenseNo(driver.getLicenseNo())) {
            throw new IllegalArgumentException("License number already exists");
        }
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
            .orElseThrow(() -> new DriverNotFoundException("Driver not found with id " + id));
    }

    public Driver updateDriver(Long id, Driver updatedDriver) {
        return driverRepository.findById(id)
            .map(driver -> {
                driver.setName(updatedDriver.getName());
                driver.setAddress(updatedDriver.getAddress());
                driver.setContactNo(updatedDriver.getContactNo());
                driver.setLicenseNo(updatedDriver.getLicenseNo());
                return driverRepository.save(driver);
            })
            .orElseThrow(() -> new DriverNotFoundException("Driver not found with id " + id));
    }

    public void deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new DriverNotFoundException("Driver not found with id " + id);
        }
        driverRepository.deleteById(id);
    }
}
