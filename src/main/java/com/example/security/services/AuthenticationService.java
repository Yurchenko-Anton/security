package com.example.security.services;

import com.example.security.dto.AuthenticationRequestDTO;
import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import com.example.security.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;


    public Map<Object, Object> authenticate(AuthenticationRequestDTO request) throws AuthenticationException {
        return buildResponse(
                findUser(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())).isAuthenticated()
                        , request)
        );
    }

    private User findUser(boolean auth, AuthenticationRequestDTO request) throws AuthenticationException {
        if (auth) {
            User user = userRepository.findByPhone(request.getPhone()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
            return user;
        } else throw new UsernameNotFoundException("User doesn't exists");
    }

    public Map<Object, Object> buildResponse(User user) {
        return new HashMap<Object, Object>() {
            {
                put("phone", user.getPhone());
                put("token", jwtTokenProvider.createToken(user.getPhone(), user.getRole().name(), user.getId()));
            }
        };
    }
}
