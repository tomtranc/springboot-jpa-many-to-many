package com.ttran.demo.rest;

import com.ttran.demo.model.entity.EmployeeEntity;
import com.ttran.demo.model.entity.DepartmentEntity;
import com.ttran.demo.model.type.CustomerType;
import com.ttran.demo.repo.schema.EmployeeRepo;
import com.ttran.demo.rest.base.BaseRestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class CustomerController extends BaseRestController {
    @Autowired private EmployeeRepo employeeRepo;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String index(HttpServletRequest request) throws UnknownHostException {
        return "Application is up at: http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort;
    }

    @GetMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeEntity> list(HttpServletRequest request) {
        return employeeRepo.findAll();
    }

    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeEntity postEndpoint(@Valid @RequestBody EmployeeEntity dto, Errors errors) {
        if (errors.hasErrors()) {
            final String errorMessage = getErrorMessage(errors.getAllErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
        return employeeRepo.save(dto);
    }

    @GetMapping(value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeEntity> findById(@PathVariable String id) {
        EmployeeEntity EmployeeEntity = employeeRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(EmployeeEntity);
    }

    @PutMapping(value = "/employee/{id}")
    public EmployeeEntity putEndpoint(@PathVariable String id, @Valid @RequestBody EmployeeEntity dto) {
        // find and update only present fields
        EmployeeEntity employee = employeeRepo.getReferenceById(id);
        Optional.ofNullable(dto.getName()).ifPresent(employee::setName);
        Optional.ofNullable(dto.getDescription()).ifPresent(employee::setDescription);
        Optional.ofNullable(dto.getDepartmentMap()).ifPresent(employee::setDepartmentMap);

        return employeeRepo.save(employee);
    }

    @DeleteMapping(value = "/employee/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteEndpoint(@PathVariable String id) {
        employeeRepo.deleteById(id);
    }

    @PutMapping(value = "/init")
    public ResponseEntity<?> populateData() {
        DepartmentEntity d1 = DepartmentEntity.builder().name("department1").build();
        DepartmentEntity d2 = DepartmentEntity.builder().name("department2").build();
        DepartmentEntity d3 = DepartmentEntity.builder().name("department3").build();
        DepartmentEntity d4 = DepartmentEntity.builder().name("department4").build();

        EmployeeEntity c1 = EmployeeEntity.builder()
                .name("customer1")
                .description("desc 1")
                .departmentMap(Map.of(
                        CustomerType.FULL_TIME, d1,
                        CustomerType.PART_TIME, d2
                ))
                .build();

        EmployeeEntity c2 = EmployeeEntity.builder()
                .name("customer2")
                .description("desc 2")
                .departmentMap(Map.of(
                        CustomerType.PART_TIME, d3,
                        CustomerType.FULL_TIME, d4
                ))
                .build();

        employeeRepo.saveAll(List.of(c1, c2));

        return ResponseEntity.ok(employeeRepo.findAll());
    }

}