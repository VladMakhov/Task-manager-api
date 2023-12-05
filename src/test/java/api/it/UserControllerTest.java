package api.it;

import api.config.TestConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
    @Order(20)
    public void updateUser_return_updated_user() throws Exception {
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
}
