package com.example.security.web;

import com.example.security.dto.ChangeRoleDTO;
import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import com.example.security.services.AdminService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/admins")
@PreAuthorize("hasAuthority('users:write')")
@AllArgsConstructor
public class AdminsController {

    private final AdminService adminService;

    @GetMapping
    public List<User> notApproveUsers() {
        return adminService.getNotApproveUsers();
    }

    @PutMapping
    public ResponseEntity<User> changeRole(@RequestBody ChangeRoleDTO changeRoleDto) {
        return adminService.changeRole(changeRoleDto.getId(), changeRoleDto.getRole());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> approve(@PathVariable Long id) {
        return adminService.approve(id);
    }

    @PutMapping("approve")
    public void approveAll() {
        adminService.approveAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        adminService.deleteUser(id);
    }
}