package com.example.security.feign;

import com.example.security.dto.ChangeRoleDTO;
import com.example.security.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "feignAdmin", url = "http://localhost:8080/auth/admins")
public interface TestFeignAdminClient {
    @GetMapping
    List<User> getNotApproveUsers(@RequestHeader HttpHeaders headers);

    @PutMapping
    ResponseEntity<User> changeRole(@RequestHeader HttpHeaders headers, @RequestBody ChangeRoleDTO changeRoleDto);

    @PutMapping("/{id}")
    ResponseEntity<User> approve(@RequestHeader HttpHeaders headers, @PathVariable Long id);

    @PutMapping("approve")
    void approveAll(@RequestHeader HttpHeaders headers);

    @DeleteMapping("/{id}")
    void delete(@RequestHeader HttpHeaders headers, @PathVariable Long id);
}
