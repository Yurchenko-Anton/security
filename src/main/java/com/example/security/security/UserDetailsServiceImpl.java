package com.example.security.security;

import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException { // cast to Long
        User user = userRepository.findById(Long.parseLong(id)).orElseThrow(() ->  new UsernameNotFoundException("User not found"));
        return SecurityUser.fromUser(user);
    }
}