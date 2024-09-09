package com.DrvierService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.DrvierService.entity.Driver;
import com.DrvierService.exception.DriverAlreadyExistsException;
import com.DrvierService.exception.DriverNotFoundException;
import com.DrvierService.repository.DriverRepository;
import com.DrvierService.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DriverServiceTest {

    @InjectMocks
    private DriverService driverService;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Service Layer Tests

    @Test
    public void testAddDriver_Success() {
        Driver driver = new Driver(1L, "John Doe", "123 Main St", "5551234567", "ABC123");
        when(driverRepository.findById(driver.getId())).thenReturn(Optional.empty());
        when(driverRepository.save(driver)).thenReturn(driver);

        Driver result = driverService.addDriver(driver);

        assertNotNull(result);
        assertEquals(driver.getId(), result.getId());
        verify(driverRepository).save(driver);
    }

  

    @Test
    public void testGetAllDrivers() {
        Driver driver1 = new Driver(1L, "John Doe", "123 Main St", "5551234567", "ABC123");
        Driver driver2 = new Driver(2L, "Jane Doe", "456 Elm St", "5557654321", "XYZ789");
        List<Driver> drivers = Arrays.asList(driver1, driver2);
        when(driverRepository.findAll()).thenReturn(drivers);

        List<Driver> result = driverService.getAllDrivers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(driverRepository).findAll();
    }

    @Test
    public void testGetDriverById_Success() {
        Driver driver = new Driver(1L, "John Doe", "123 Main St", "5551234567", "ABC123");
        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));

        Driver result = driverService.getDriverById(driver.getId());

        assertNotNull(result);
        assertEquals(driver.getId(), result.getId());
        verify(driverRepository).findById(driver.getId());
    }

    @Test
    public void testGetDriverById_NotFound() {
        when(driverRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DriverNotFoundException.class, () -> {
            driverService.getDriverById(1L);
        });

        assertEquals("Driver not found with id 1", exception.getMessage());
        verify(driverRepository).findById(1L);
    }

    @Test
    public void testUpdateDriver_Success() {
        Driver existingDriver = new Driver(1L, "John Doe", "123 Main St", "5551234567", "ABC123");
        Driver updatedDriver = new Driver(1L, "John Smith", "789 Oak St", "5554321098", "DEF456");
        when(driverRepository.findById(existingDriver.getId())).thenReturn(Optional.of(existingDriver));
        when(driverRepository.save(updatedDriver)).thenReturn(updatedDriver);

        Driver result = driverService.updateDriver(existingDriver.getId(), updatedDriver);

        assertNotNull(result);
        assertEquals(updatedDriver.getName(), result.getName());
        assertEquals(updatedDriver.getAddress(), result.getAddress());
        assertEquals(updatedDriver.getContactNo(), result.getContactNo());
        assertEquals(updatedDriver.getLicenseNo(), result.getLicenseNo());
        verify(driverRepository).findById(existingDriver.getId());
        verify(driverRepository).save(updatedDriver);
    }

    @Test
    public void testUpdateDriver_NotFound() {
        Driver updatedDriver = new Driver(1L, "John Smith", "789 Oak St", "5554321098", "DEF456");
        when(driverRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DriverNotFoundException.class, () -> {
            driverService.updateDriver(1L, updatedDriver);
        });

        assertEquals("Driver not found with id 1", exception.getMessage());
        verify(driverRepository).findById(1L);
        verify(driverRepository, never()).save(updatedDriver);
    }

    @Test
    public void testDeleteDriver_Success() {
        when(driverRepository.existsById(1L)).thenReturn(true);

        driverService.deleteDriver(1L);

        verify(driverRepository).deleteById(1L);
    }

    @Test
    public void testDeleteDriver_NotFound() {
        when(driverRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(DriverNotFoundException.class, () -> {
            driverService.deleteDriver(1L);
        });

        assertEquals("Driver not found with id 1", exception.getMessage());
        verify(driverRepository).existsById(1L);
        verify(driverRepository, never()).deleteById(1L);
    }
}
