package api.it;

import api.config.TestConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(10)
    public void getTask_return_task_with_full_info() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/task/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("""
                                    {
                                      "id": 1,
                                      "title": "test1",
                                      "description": "test",
                                      "status": "test",
                                      "priority": "test",
                                      "author": {
                                          "id": 1,
                                          "firstName": "test",
                                          "lastName": "test",
                                          "email": "test1@mail.com",
                                          "position": "test"
                                      },
                                      "executors": [
                                          {
                                              "id": 1,
                                              "firstName": "test",
                                              "lastName": "test",
                                              "email": "test1@mail.com",
                                              "position": "test"
                                          },
                                          {
                                              "id": 2,
                                              "firstName": "test",
                                              "lastName": "test",
                                              "email": "test2@mail.com",
                                              "position": "test"
                                          }
                                      ],
                                      "comments": [
                                          {
                                              "id": 1,
                                              "comment": "test",
                                              "author": {
                                                  "id": 1,
                                                  "firstName": "test",
                                                  "lastName": "test",
                                                  "email": "test1@mail.com",
                                                  "position": "test"
                                              }
                                          }
                                      ]
                                  }
                                """)
                );
    }

    @Test
    @Order(20)
    public void getTaskComments_return_list_of_comments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/task/1/comments")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("""
                                   [
                                       {
                                           "id": 1,
                                           "comment": "test",
                                           "author": {
                                               "id": 1,
                                               "firstName": "test",
                                               "lastName": "test",
                                               "email": "test1@mail.com",
                                               "position": "test"
                                           }
                                       }
                                   ]
                                """)
                );
    }

    @Test
    @Order(30)
    public void getTaskAuthor_return_author() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/task/1/author")
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
    @Order(40)
    public void getTasksAuthoredByUser_return_list_of_tasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/user/1/backlog")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("""
                                    [
                                       {
                                           "id": 1,
                                               "title": "test1",
                                               "description": "test",
                                               "status": "test",
                                               "priority": "test",
                                               "author": {
                                           "id": 1,
                                                   "firstName": "test",
                                                   "lastName": "test",
                                                   "email": "test1@mail.com",
                                                   "position": "test"
                                       },
                                           "executors": [
                                           {
                                               "id": 1,
                                                   "firstName": "test",
                                                   "lastName": "test",
                                                   "email": "test1@mail.com",
                                                   "position": "test"
                                           },
                                           {
                                               "id": 2,
                                                   "firstName": "test",
                                                   "lastName": "test",
                                                   "email": "test2@mail.com",
                                                   "position": "test"
                                           }
                                           ],
                                           "comments": [
                                           {
                                               "id": 1,
                                                   "comment": "test",
                                                   "author": {
                                               "id": 1,
                                                       "firstName": "test",
                                                       "lastName": "test",
                                                       "email": "test1@mail.com",
                                                       "position": "test"
                                           }
                                           }
                                           ]
                                       }
                                   ]
                                """)
                );
    }

    @Test
    @Order(50)
    public void getTasksExecutedByUser_return_list_of_tasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/user/1/tasks")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("""
                                   [
                                       {
                                           "id": 1,
                                           "title": "test1",
                                           "description": "test",
                                           "status": "test",
                                           "priority": "test",
                                           "author": {
                                               "id": 1,
                                               "firstName": "test",
                                               "lastName": "test",
                                               "email": "test1@mail.com",
                                               "position": "test"
                                           },
                                           "executors": [
                                               {
                                                   "id": 1,
                                                   "firstName": "test",
                                                   "lastName": "test",
                                                   "email": "test1@mail.com",
                                                   "position": "test"
                                               },
                                               {
                                                   "id": 2,
                                                   "firstName": "test",
                                                   "lastName": "test",
                                                   "email": "test2@mail.com",
                                                   "position": "test"
                                               }
                                           ],
                                           "comments": [
                                               {
                                                   "id": 1,
                                                   "comment": "test",
                                                   "author": {
                                                       "id": 1,
                                                       "firstName": "test",
                                                       "lastName": "test",
                                                       "email": "test1@mail.com",
                                                       "position": "test"
                                                   }
                                               }
                                           ]
                                       }
                                   ]
                                """)
                );
    }

    @Test
    @Order(60)
    public void updateTask_return_updated_task() throws Exception {
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(securityContextMock.getAuthentication().getName()).thenReturn("test2@mail.com");
        SecurityContextHolder.setContext(securityContextMock);

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/task/2/updateStatus")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "status": "In Progress"
                                }
                                """))
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("""
                                    {
                                        "id": 2,
                                        "title": "test2",
                                        "description": "test",
                                        "status": "In Progress",
                                        "priority": "test",
                                        "author": {
                                            "id": 2,
                                            "firstName": "test",
                                            "lastName": "test",
                                            "email": "test2@mail.com",
                                            "position": "test"
                                        },
                                        "executors": [
                                            {
                                                "id": 2,
                                                "firstName": "test",
                                                "lastName": "test",
                                                "email": "test2@mail.com",
                                                "position": "test"
                                            }
                                        ],
                                        "comments": [
                                            {
                                                "id": 2,
                                                "comment": "test",
                                                "author": {
                                                    "id": 2,
                                                    "firstName": "test",
                                                    "lastName": "test",
                                                    "email": "test2@mail.com",
                                                    "position": "test"
                                                }
                                            }
                                        ]
                                    }
                                """)
                );
    }
    @Test
    @Order(70)
    public void addComment_return_commented_task() throws Exception {
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(securityContextMock.getAuthentication().getName()).thenReturn("test1@mail.com");
        SecurityContextHolder.setContext(securityContextMock);

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/task/1/addComment")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "comment": "test"
                                }
                                """))
                .andDo(print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json("""
                                  {
                                      "id": 1,
                                      "title": "test1",
                                      "description": "test",
                                      "status": "test",
                                      "priority": "test",
                                      "author": {
                                          "id": 1,
                                          "firstName": "test",
                                          "lastName": "test",
                                          "email": "test1@mail.com",
                                          "position": "test"
                                      },
                                      "executors": [
                                          {
                                              "id": 1,
                                              "firstName": "test",
                                              "lastName": "test",
                                              "email": "test1@mail.com",
                                              "position": "test"
                                          },
                                          {
                                              "id": 2,
                                              "firstName": "test",
                                              "lastName": "test",
                                              "email": "test2@mail.com",
                                              "position": "test"
                                          }
                                      ],
                                      "comments": [
                                          {
                                              "id": 1,
                                              "comment": "test",
                                              "author": {
                                                  "id": 1,
                                                  "firstName": "test",
                                                  "lastName": "test",
                                                  "email": "test1@mail.com",
                                                  "position": "test"
                                              }
                                          },
                                          {
                                              "id": 3,
                                              "comment": "test",
                                              "author": {
                                                  "id": 1,
                                                  "firstName": "test",
                                                  "lastName": "test",
                                                  "email": "test1@mail.com",
                                                  "position": "test"
                                              }
                                          }
                                      ]
                                  }
                                """)
                );
    }
}
