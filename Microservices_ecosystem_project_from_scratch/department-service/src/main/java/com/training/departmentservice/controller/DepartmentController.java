package com.training.departmentservice.controller;

import com.training.departmentservice.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@Slf4j
public class DepartmentController {

    @Value("${department.name:dept123}")
    String depName ;
    @GetMapping("/departments/{id}")
    public Department getDepartmentDetails(@PathVariable("id") String id){
        log.info("getting department for id :{}",id);

        return Department.builder()
                .departmentId(id)
                .departmentName(depName)
                .build();
//        return department;
    }
}
