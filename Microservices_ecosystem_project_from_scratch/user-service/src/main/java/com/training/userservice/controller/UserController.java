package com.training.userservice.controller;

import com.training.userservice.model.Department;
import com.training.userservice.model.User;
import com.training.userservice.model.UserDepart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@Slf4j
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/users/{id}")
    public User fetchUserDetails(@PathVariable("id") String id) {
        User user = User.builder()
                .userName("Vipul")
                .address("New Delhi")
                .email("vipul.bisht96@gmail.com")
                .id(id)
                .build();
        return user;
    }
        @GetMapping("/users/{id}/department/{departId}")
        public UserDepart fetchUserDetails(@PathVariable("id") String id,@PathVariable("departId") String departId) {
            User user= User.builder()
                    .userName("Vipul")
                    .address("New Delhi")
                    .email("vipul.bisht96@gmail.com")
                    .id(id)
                    .build();
//            using department service in user service
            String url=getUrl();
            Department department=restTemplate.getForObject(url+"/departments/"+departId ,Department.class);
            //Department department=restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/"+departId ,Department.class);

            return UserDepart.builder().user(user).department(department).build();
    }

    private String getUrl(){
        ServiceInstance instance=loadBalancerClient.choose("DEPARTMENT-SERVICE");
        String url=instance.getUri().toString();
        int port=instance.getPort();
        String serviceId=instance.getServiceId();
        String host=instance.getHost();
        log.info("URL: {}" + url);
        log.info("PORT: {}" + port);
        log.info("SERVICE: {}" + serviceId);
        log.info("HOST: {}" + host);

        return url;

    }
}
