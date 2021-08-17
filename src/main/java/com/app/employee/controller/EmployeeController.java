package com.app.employee.controller;

import com.app.employee.constants.ApiResponse;
import com.app.employee.constants.CustomMessages;
import com.app.employee.exceptions.RecordNotFoundException;
import com.app.employee.model.Employee;
import com.app.employee.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private AppService service;

    @PostMapping(value = "/employee/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        employee.setDateCreated(new Date());
        Employee newStudent = service.getEmployeeRepository().save(employee);

        return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, newStudent));
    }

    @GetMapping("/employee/getAllEmployees")
    public List<Employee> getAllEmployees() {
        return service.getEmployeeRepository().findAll();
    }

    @GetMapping("/employee/getEmployeeById/{id}")
    public ResponseEntity getEmployeeById(@PathVariable("id") Long id) {
        return service.getEmployeeRepository().findById(id).map(record -> {
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Success, record));
        }).orElseThrow(() -> new RecordNotFoundException("Record Not Found for: " + id));
    }

    @DeleteMapping("/employee/deleteEmployeeById/{id}")
    public ResponseEntity deleteEmployeeById(@PathVariable("id") Long id) {
        return service.getEmployeeRepository().findById(id).map(record -> {
            service.getEmployeeRepository().deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(CustomMessages.Deleted, CustomMessages.DeletedMessage));
        }).orElseThrow(() -> new RecordNotFoundException("Record Not Found for: " + id));
    }

}
