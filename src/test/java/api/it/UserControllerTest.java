package api.it;

import api.config.TestConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(10)
    public void getUserById_return_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/user/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("""
                            {
                                 "id": 1,
                                 "firstName": "test",
                                 "lastName": "test",
                                 "email": "test1@mail.com",
                                 "position": "test"
                             }
                            """)
                );
    }

    @Test
    @Order(15)
    public void getUserById_throws_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/user/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ERROR: User with id 0 not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @Order(20)
    public void updateUser_return_updated_user() throws Exception {
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(securityContextMock.getAuthentication().getName()).thenReturn("test1@mail.com");
        SecurityContextHolder.setContext(securityContextMock);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/user/1/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "firstName": "newtest",
                                    "lastName": "test",
                                    "email": "test1@mail.com",
                                    "position": "test"
                                }
                                   """))
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("""
                                {
                                     "id": 1,
                                     "firstName": "newtest",
                                     "lastName": "test",
                                     "email": "test1@mail.com",
                                     "position": "test"
                                }
                            """)
                );
    }

    @Test
    @Order(30)
    public void updateUser_throws_exception() throws Exception {
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(securityContextMock.getAuthentication().getName()).thenReturn("fail@mail.com");
        SecurityContextHolder.setContext(securityContextMock);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/user/1/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "newtest",
                                    "lastName": "test",
                                    "email": "test1@mail.com",
                                    "position": "test"
                                }
                                   """))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(401))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ERROR: User has not authority to update this user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());

    }
}
