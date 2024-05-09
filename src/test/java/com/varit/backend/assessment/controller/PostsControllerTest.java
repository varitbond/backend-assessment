package com.varit.backend.assessment.controller;

import com.varit.backend.assessment.exception.DataNotFoundException;
import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.post.Post;
import com.varit.backend.assessment.service.PostService;
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
@WebMvcTest(PostsController.class)
@ImportAutoConfiguration(exclude = {OAuth2ClientAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class})
class PostsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetAllPosts_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        List<Post> posts = Arrays.asList(new Post(1, 1, "Test Title", "Test Body"), new Post(2, 2, "Test Title", "Test Body"));
        when(postService.getAllPosts()).thenReturn(posts);
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].userId").value(2));
    }

    @Test
    @WithAnonymousUser
    void testGetAllPosts_Unauthorized() throws Exception {
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get")
                        .header("Authorization", "Bearer "))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetPostById_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        when(postService.getPostById(1)).thenReturn(new Post(1, 1, "Test Title", "Test Body"));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Test Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.body").value("Test Body"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetPostById_NotFound() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        when(postService.getPostById(1)).thenThrow(new DataNotFoundException("No post found for id: 1"));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("No post found for id: 1"));
    }

    @Test
    @WithAnonymousUser
    void testGetPostById_Unauthorized() throws Exception {
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get/1"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetPostByUserId_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        List<Post> posts = List.of(new Post(1, 1, "Test Title", "Test Body"));
        when(postService.getPostByUserId(anyInt())).thenReturn(posts);
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get/by-userid?userId=1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userId").value(1));

    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testGetPostByUserId_NotFound() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        when(postService.getPostByUserId(1)).thenThrow(new DataNotFoundException("No post found for user id: 1"));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get/by-userid?userId=1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("No post found for user id: 1"));
    }

    @Test
    @WithAnonymousUser
    void testGetPostByUserId_Unauthorized() throws Exception {
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/get/by-userid?userId=1"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testPatchPost_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        Post post = new Post(1, 1, "Test Title", "Test Body");
        doNothing().when(postService).patchPost(any(Post.class));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/patch")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testPatchPost_MissingId() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        Post post = new Post(null, 1, "Test Title", "Test Body");
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/patch")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Input validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("{id=must not be null}"));
    }

    @Test
    @WithAnonymousUser
    void testPatchPost_Unauthorized() throws Exception {
        // Mock
        Post post = new Post(1, 1, "Test Title", "Test Body");
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/patch")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testUpdatePost_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        Post post = new Post(1, 1, "Test Title", "Test Body");
        doNothing().when(postService).updatePost(any(Post.class));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.put("/posts/update")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testUpdatePost_MissingIdAndUserId() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        Post post = new Post(null, null, "Test Title", "Test Body");
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.put("/posts/update")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Input validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("{id=must not be null, userId=must not be null}"));
    }

    @Test
    @WithAnonymousUser
    void testUpdatePost_Unauthorized() throws Exception {
        // Mock
        Post post = new Post(1, 1, "Test Title", "Test Body");
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.put("/posts/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testDeletePost_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        doNothing().when(postService).deletePost(anyInt());
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/delete/1")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"));
    }

    @Test
    @WithAnonymousUser
    void testDeletePost_Unauthorized() throws Exception {
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/delete/1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testCreatePost_Success() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        Post post = new Post(null, 1, "Test Title", "Test Body");
        when(postService.createPost(any(Post.class))).thenReturn(new CreateResourceResponse(1));
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/create")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void testCreatePost_MissingUserId() throws Exception {
        // Mock
        String token = "mocked_jwt_token";
        Post post = new Post(null, null, "Test Title", "Test Body");
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/create")
                        .with(csrf())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.description").value("Input validation error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status.errorMessage").value("{userId=must not be null}"));
    }

    @Test
    @WithAnonymousUser
    void testCreatePost_Unauthorized() throws Exception {
        // Mock
        Post post = new Post(1, 1, "Test Title", "Test Body");
        // Call & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(post)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}