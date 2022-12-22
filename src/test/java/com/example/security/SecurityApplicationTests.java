package com.example.security;

import com.example.security.dto.AuthenticationRequestDTO;
import com.example.security.dto.ChangeRoleDTO;
import com.example.security.entity.User;
import com.example.security.feign.TestFeignAdminClient;
import com.example.security.feign.TestFeignLoginClient;
import com.example.security.feign.TestFeignUserClient;
import com.example.security.model.Role;
import com.example.security.model.Status;
import feign.FeignException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = SecurityApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class SecurityApplicationTests {

    @Autowired
    private TestFeignLoginClient feignLoginClient;
    @Autowired
    private TestFeignUserClient feignUserClient;
    @Autowired
    private TestFeignAdminClient feignAdminClient;


    private static AuthenticationRequestDTO admin;
    private static AuthenticationRequestDTO notAdmin;

    private static final String ADMIN_PHONE = "+380937777777";
    private static final String ADMIN_PASS = "admin";
    private static final String NOT_ADMIN_PHONE = "+380935555555";
    private static final String NOT_ADMIN_PASS = "passenger";
    private User user;

    @BeforeAll
    public static void init() {
        admin = new AuthenticationRequestDTO(ADMIN_PHONE, ADMIN_PASS);
        notAdmin = new AuthenticationRequestDTO(NOT_ADMIN_PHONE, NOT_ADMIN_PASS);
    }

    @BeforeEach
    public void getNewUser() {
        createUser();
        user = feignUserClient.registration(user);
    }

    @AfterEach
    public void delUser() {
        feignAdminClient.delete(headers(admin), user.getId());
    }

    void getExistUser() {
        user = feignUserClient.getUser(headers(admin), user.getId());
    }

    void createUser() {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Der");
        user.setRole(Role.ADMIN);
        user.setPhone("+3809357575723");
        user.setPassword("add");
        this.user = user;
    }

    HttpHeaders headers(AuthenticationRequestDTO request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", feignLoginClient.authenticate(request).getBody().get("token").toString());
        return headers;
    }


    @Test
    void changeUserRole() {
        feignAdminClient.changeRole(headers(admin), new ChangeRoleDTO(user.getId(), Role.DRIVER));
        getExistUser();
        MatcherAssert.assertThat(user.getRole(), Matchers.equalTo(Role.DRIVER));
    }

    @Test
    public void changeUserRoleIfNotAdmin() {
        assertThrows(
                FeignException.class,
                () -> feignAdminClient.changeRole(headers(notAdmin), new ChangeRoleDTO(user.getId(), Role.DRIVER)));
    }

    @Test
    void approveOneUser() {
        feignAdminClient.approve(headers(admin), user.getId());
        getExistUser();
        MatcherAssert.assertThat(user.getStatus(), Matchers.equalTo(Status.APPROVED));
    }

    @Test
    void approveAllUsers() {
        feignAdminClient.approveAll(headers(admin));
        MatcherAssert.assertThat(feignAdminClient.getNotApproveUsers(headers(admin)), Matchers.empty());
    }

}
