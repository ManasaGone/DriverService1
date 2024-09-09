package com.DrvierService.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.DrvierService.entity.Driver;
import com.DrvierService.service.DriverService;


@RestController
@RequestMapping("/drivers")
@CrossOrigin("*")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/AddDriver")
    public Driver addDriver(@RequestBody Driver driver) {
        return driverService.addDriver(driver);
    }

    @GetMapping("/viewAllDrivers")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/viewDriverById/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }
    @DeleteMapping("/deleteDriver/{id}")
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }

    @PutMapping("/updateDriver/{id}")
    public Driver updateDriver(@PathVariable Long id, @RequestBody Driver driver) {
        return driverService.updateDriver(id, driver);
    }
}
