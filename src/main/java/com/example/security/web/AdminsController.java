package com.example.security.web;

import com.example.security.dto.ChangeRoleDTO;
import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import com.example.security.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@PreAuthorize("hasAuthority('users:write')")
@AllArgsConstructor
public class AdminsController {

    UserRepository userRepository;
    AdminService adminService;

    @GetMapping("/get")
    public List<User> notApproveUsers() {
        return adminService.getNotApproveUsers();
    }

    @PutMapping("/change")
    public ResponseEntity<User> changeRole(@RequestBody ChangeRoleDTO changeRoleDto) {
        return adminService.changeRole(changeRoleDto.getId(), changeRoleDto.getRole());
    }

    @PutMapping("approve/{id}")
    public ResponseEntity<User> approve(@PathVariable Long id) {
        return adminService.approve(id);
    }

    @PutMapping("approve")
    public void approveAll() {
        adminService.approveAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        adminService.deleteUser(id);
    }
}
