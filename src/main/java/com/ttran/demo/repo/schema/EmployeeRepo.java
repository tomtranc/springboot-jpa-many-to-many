package com.ttran.demo.repo.schema;

import com.ttran.demo.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, String> {

}
