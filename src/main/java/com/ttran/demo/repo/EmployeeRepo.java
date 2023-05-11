package com.ttran.demo.repo;

import com.ttran.demo.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<UserEntity, String> {

}
