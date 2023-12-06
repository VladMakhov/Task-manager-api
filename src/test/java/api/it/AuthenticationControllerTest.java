package api.it;

import api.config.TestConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(10)
    public void registration_return_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/auth/reg")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Vlad",
                                    "lastName": "Makhov",
                                    "position": "Backend",
                                    "email": "mail@mail.com",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Vlad"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Makhov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value("Backend"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("mail@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
    }

    @Test
    @Order(20)
    public void registration_return_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/auth/reg")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Vlad",
                                    "lastName": "Makhov",
                                    "position": "Backend",
                                    "email": "mail@mail.com",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ERROR: User with email mail@mail.com already exist. Try another one"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @Order(30)
    public void login_return_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "mail@mail.com",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Vlad"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Makhov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value("Backend"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("mail@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
    }

    @Test
    @Order(40)
    public void login_return_exception_wrong_email() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "wrong@mail.com",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ERROR: User with email wrong@mail.com not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @Order(50)
    public void login_return_exception_wrong_password() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "mail@mail.com",
                                    "password": "password123"
                                }
                                """))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ERROR: Wrong password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

}
