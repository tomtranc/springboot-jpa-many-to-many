package com.ttran.demo.model.entity;

import com.ttran.demo.model.type.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @JoinTable(name = "CUSTOMER_DEPARTMENT",
            joinColumns = {@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "id")})
    @MapKeyColumn(name = "EMPLOYEE_TYPE") // creates 'EMPLOYEE_TYPE' column in the join table 'CUSTOMER_DEPARTMENT'
    @MapKeyEnumerated(EnumType.STRING)
    private Map<CustomerType, DepartmentEntity> departmentMap;
}
