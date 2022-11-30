package com.example.security.rest;

import com.example.security.model.Admin;
import com.example.security.model.Driver;
import com.example.security.model.Passenger;
import com.example.security.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1")
public class UserRestController {

    private List<User> users = Stream.of(
            new Passenger(1L,"Passenger","P"),
            new Driver(2L,"Driver","D"),
            new Admin(3L,"Admin","A")
    ).collect(Collectors.toList());

    @GetMapping
    @PreAuthorize("hasAuthority('users:read')")
    public List<User> getAll() {return users;}

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public User getById(@PathVariable Long id){
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('users:write')")
    public User create (@RequestBody Passenger passenger){
        this.users.add(passenger);
        return passenger;
    }
}
