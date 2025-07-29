package com.ttran.demo.model.entity;

import com.ttran.demo.model.type.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyClass;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(nullable = false, updatable = false)
    private String id;

    @NotBlank
    @Pattern(regexp = "^\\w+$")
    private String name;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "EMPLOYEE_DEPARTMENT",
            joinColumns = {@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "id")})
    @MapKeyColumn(name = "EMPLOYEE_TYPE") // creates 'EMPLOYEE_TYPE' column in the join table 'CUSTOMER_DEPARTMENT'
    @MapKeyEnumerated(EnumType.STRING)
    private Map<CustomerType, DepartmentEntity> departmentMap;
}
