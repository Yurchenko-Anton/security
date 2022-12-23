package com.example.security.services;

import com.example.security.dto.AuthenticationRequestDTO;
import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import com.example.security.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public Map<String, String> authenticate(AuthenticationRequestDTO request) throws AuthenticationException {
        boolean authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())).isAuthenticated();
        User user = findUser(authenticate, request);
        return buildResponse(user);
    }

    private User findUser(boolean auth, AuthenticationRequestDTO request) throws AuthenticationException {
        if (auth) {
            return userRepository.findByPhone(request.getPhone()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        } else throw new UsernameNotFoundException("User doesn't authenticate");
    }

    public Map<String, String> buildResponse(User user) {
        return new HashMap<String, String>() {
            {
                put("id", user.getId().toString());
                put("token", jwtTokenProvider.createToken(user.getId(), user.getRole().name(), user.getPhone()));
            }
        };
    }

    public void logoutUser(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, null);
    }
}