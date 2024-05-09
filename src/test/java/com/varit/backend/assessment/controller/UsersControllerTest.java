package com.varit.backend.assessment.controller;

import com.varit.backend.assessment.exception.DataNotFoundException;
import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.user.User;
import com.varit.backend.assessment.model.user.address.Address;
import com.varit.backend.assessment.model.user.address.Geo;
import com.varit.backend.assessment.model.user.company.Company;
import com.varit.backend.assessment.service.UsersService;
import com.varit.backend.assessment.testutil.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsersController.class)
@ImportAutoConfiguration(exclude = {OAuth2ClientAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class})
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersService usersService;

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetUserById_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        when(usersService.getUserById(1)).thenReturn(getUser1());
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("johndoe"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetUserById_NotFound() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        when(usersService.getUserById(1)).thenThrow(new DataNotFoundException("No user found for id: 1"));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("No user found for id: 1"));
    }

    @Test
    @WithAnonymousUser
    void testGetUserById_Unauthorized() throws Exception {
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/1"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetUserByPostId_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        List<User> users = Arrays.asList(getUser1());
        when(usersService.getUserByPostId(1)).thenReturn(users);
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/by-postid?postId=1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].username").value("johndoe"));
    }

    @Test
    @WithAnonymousUser
    void testGetUserByPostId_Unauthorized() throws Exception {
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get/by-postid?postId=1")
                        .header("Authorization", "Bearer "))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetAllUsers_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        List<User> users = Arrays.asList(getUser1(), getUser2());
        when(usersService.getAllUser()).thenReturn(users);
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].username").value("johndoe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].username").value("janesmith"));
    }

    @Test
    @WithAnonymousUser
    void testGetAllUsers_Unauthorized() throws Exception {
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get")
                        .header("Authorization", "Bearer "))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testPatchUser_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        User user = getUser1();
        doNothing().when(usersService).patchUser(any(User.class));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/patch")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testPatchUser_MissingId() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        User user = getUser1();
        user.setId(null);
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/patch")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Input validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("{id=must not be null}"));
    }

    @Test
    @WithAnonymousUser
    void testPatchUser_Unauthorized() throws Exception {
        // Mock
        User user = getUser1();
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/patch")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testUpdateUser_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        User user = getUser1();
        doNothing().when(usersService).updateUser(any(User.class));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testUpdateUser_MissingIdAndName() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        User user = getUser1();
        user.setId(null);
        user.setName(null);
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Input validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("{name=must not be blank, id=must not be null}"));
    }

    @Test
    @WithAnonymousUser
    void testUpdateUser_Unauthorized() throws Exception {
        // Mock
        User user = getUser1();
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testDeleteUser_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        doNothing().when(usersService).deleteUser(anyInt());
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"));
    }

    @Test
    @WithAnonymousUser
    void testDeleteUser_Unauthorized() throws Exception {
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testCreateUser_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        User user = getUser1();
        when(usersService.createUser(any(User.class))).thenReturn(new CreateResourceResponse(1));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testCreateUser_MissingNAme() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        User user = getUser1();
        user.setName(null);
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Input validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("{name=must not be blank}"));
    }

    @Test
    @WithAnonymousUser
    void testCreateUser_Unauthorized() throws Exception {
        // Mock
        User user = getUser1();
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/user")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private User getUser1() {
        return User.builder()
                .id(1)
                .name("John Doe")
                .username("johndoe")
                .email("johndoe@example.com")
                .address(Address.builder()
                        .street("123 Main St")
                        .suite("Apt 101")
                        .city("Springfield")
                        .zipcode("12345")
                        .geo(Geo.builder()
                                .lat("40.7128")
                                .lng("-74.0060")
                                .build())
                        .build())
                .phone("123-456-7890")
                .website("www.example.com")
                .company(Company.builder()
                        .name("Example Company")
                        .catchPhrase("Making examples since 2000")
                        .bs("Business services")
                        .build())
                .build();
    }

    private User getUser2() {
        return User.builder()
                .id(2)
                .name("Jane Smith")
                .username("janesmith")
                .email("janesmith@example.com")
                .address(Address.builder()
                        .street("456 Oak St")
                        .suite("Suite 202")
                        .city("Oakland")
                        .zipcode("54321")
                        .geo(Geo.builder()
                                .lat("37.8044")
                                .lng("-122.2711")
                                .build())
                        .build())
                .phone("987-654-3210")
                .website("www.example.org")
                .company(Company.builder()
                        .name("Another Company")
                        .catchPhrase("Creating new ideas")
                        .bs("Innovation services")
                        .build())
                .build();

    }
}