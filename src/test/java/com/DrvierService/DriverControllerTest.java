package com.DrvierService;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.DrvierService.controller.DriverController;
import com.DrvierService.entity.Driver;
import com.DrvierService.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class DriverControllerTest {

    @InjectMocks
    private DriverController driverController;

    @Mock
    private DriverService driverService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(driverController).build();
    }

    @Test
    void testAddDriver_Controller_Success() throws Exception {
        Driver driver = new Driver(1L, "John Doe", "123 Main St", "5551234567", "ABC123");
        when(driverService.addDriver(any(Driver.class))).thenReturn(driver);

        mockMvc.perform(post("/drivers/AddDriver")
                .contentType("application/json")
                .content("{ \"name\": \"John Doe\", \"address\": \"123 Main St\", \"contactNo\": \"5551234567\", \"licenseNo\": \"ABC123\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.contactNo").value("5551234567"))
                .andExpect(jsonPath("$.licenseNo").value("ABC123"));
    }

   

    @Test
    void testGetAllDrivers_Controller() throws Exception {
        List<Driver> drivers = new ArrayList<>();
        Driver driver = new Driver(1L, "John Doe", "123 Main St", "5551234567", "ABC123");
        drivers.add(driver);

        when(driverService.getAllDrivers()).thenReturn(drivers);

        mockMvc.perform(get("/drivers/viewAllDrivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].contactNo").value("5551234567"))
                .andExpect(jsonPath("$[0].licenseNo").value("ABC123"));
    }

    @Test
    void testGetDriverById_Controller_Success() throws Exception {
        Driver driver = new Driver(1L, "John Doe", "123 Main St", "5551234567", "ABC123");
        when(driverService.getDriverById(anyLong())).thenReturn(driver);

        mockMvc.perform(get("/drivers/viewDriverById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.contactNo").value("5551234567"))
                .andExpect(jsonPath("$.licenseNo").value("ABC123"));
    }

   

    @Test
    void testUpdateDriver_Controller_Success() throws Exception {
        Driver updatedDriver = new Driver(1L, "John Smith", "789 Oak St", "5554321098", "DEF456");
        when(driverService.updateDriver(anyLong(), any(Driver.class))).thenReturn(updatedDriver);

        mockMvc.perform(put("/drivers/updateDriver/1")
                .contentType("application/json")
                .content("{ \"name\": \"John Smith\", \"address\": \"789 Oak St\", \"contactNo\": \"5554321098\", \"licenseNo\": \"DEF456\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.address").value("789 Oak St"))
                .andExpect(jsonPath("$.contactNo").value("5554321098"))
                .andExpect(jsonPath("$.licenseNo").value("DEF456"));
    }

  
    @Test
    void testDeleteDriver_Controller_Success() throws Exception {
        doNothing().when(driverService).deleteDriver(anyLong());

        mockMvc.perform(delete("/drivers/deleteDriver/1"))
                .andExpect(status().isOk());
    }

  
}
