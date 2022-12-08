package com.example.security.dto;

import com.example.security.model.Role;
import lombok.Value;

@Value
public class ChangeRoleDTO {

    Long id;
    Role role;

}
