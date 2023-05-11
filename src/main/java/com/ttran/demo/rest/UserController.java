package com.ttran.demo.rest;

import com.ttran.demo.model.entity.UserEntity;
import com.ttran.demo.model.entity.RoleEntity;
import com.ttran.demo.model.type.RoleType;
import com.ttran.demo.repo.EmployeeRepo;
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
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class UserController extends BaseRestController {
    @Autowired private EmployeeRepo employeeRepo;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String index(HttpServletRequest request) throws UnknownHostException {
        return "Application is up at: http://" + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort;
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> list(HttpServletRequest request) {
        return employeeRepo.findAll();
    }

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity postEndpoint(@Valid @RequestBody UserEntity dto, Errors errors) {
        if (errors.hasErrors()) {
            final String errorMessage = getErrorMessage(errors.getAllErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
        return employeeRepo.save(dto);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserEntity> findById(@PathVariable String id) {
        UserEntity UserEntity = employeeRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(UserEntity);
    }

    @PutMapping(value = "/user/{id}")
    public UserEntity putEndpoint(@PathVariable String id, @Valid @RequestBody UserEntity dto) {
        // find and update only present fields
        UserEntity employee = employeeRepo.getReferenceById(id);
        Optional.ofNullable(dto.getName()).ifPresent(employee::setName);
        Optional.ofNullable(dto.getDescription()).ifPresent(employee::setDescription);
        Optional.ofNullable(dto.getRoles()).ifPresent(employee::setRoles);

        return employeeRepo.save(employee);
    }

    @DeleteMapping(value = "/user/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteEndpoint(@PathVariable String id) {
        employeeRepo.deleteById(id);
    }

    @PutMapping(value = "/init")
    public ResponseEntity<?> populateData() {
        RoleEntity viewer = RoleEntity.builder().type(RoleType.VIEWER).build();
        RoleEntity editor = RoleEntity.builder().type(RoleType.EDITOR).build();
        RoleEntity admin = RoleEntity.builder().type(RoleType.ADMIN).build();
        RoleEntity superAdmin = RoleEntity.builder().type(RoleType.SUPER_ADMIN).build();

        UserEntity user1 = UserEntity.builder()
                .name("user1")
                .description("desc 1")
                .roles(Set.of(viewer))
                .build();

        UserEntity user2 = UserEntity.builder()
                .name("user2")
                .description("desc 2")
                .roles(Set.of(admin))
                .build();

        employeeRepo.saveAll(List.of(user1, user2));

        return ResponseEntity.ok(employeeRepo.findAll());
    }

}