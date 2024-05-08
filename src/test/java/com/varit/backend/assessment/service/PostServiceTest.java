package com.varit.backend.assessment.service;

import com.varit.backend.assessment.exception.BusinessException;
import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import com.varit.backend.assessment.model.post.Post;
import com.varit.backend.assessment.repository.PostsRepository;
import com.varit.backend.assessment.repository.UsersRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceTest {

    @MockBean
    private PostsRepository postsRepository;

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private PostService postService;

    @Test
    void testGetAllPosts_Success() {
        // Mock
        List<PostsRecord> postsRecordList = Arrays.asList(new PostsRecord(1, 10, "title", "body"));
        when(postsRepository.getAllPosts()).thenReturn(postsRecordList);
        // Call
        List<Post> result = postService.getAllPosts();
        // Verify
        assertNotNull(result);
        assertEquals(1, CollectionUtils.size(result));
        assertEquals(1, result.get(0).getId());
        assertEquals(10, result.get(0).getUserId());
    }

    @Test
    void testGetPostById_Success() {
        // Mock
        int postId = 1;
        PostsRecord postsRecord = new PostsRecord(1, 10, "title", "body");
        when(postsRepository.getPostById(postId)).thenReturn(postsRecord);
        // Call
        Post result = postService.getPostById(postId);
        // Verify
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(10, result.getUserId());
    }

    @Test
    void testGetPostByUserId_Success() {
        // Mock
        int userId = 1;
        List<PostsRecord> postsRecordList = Arrays.asList(new PostsRecord(1, 10, "title", "body"));
        when(postsRepository.getPostByUserId(userId)).thenReturn(postsRecordList);
        // Call
        List<Post> result = postService.getPostByUserId(userId);
        // Verify
        assertNotNull(result);
        assertEquals(1, CollectionUtils.size(result));
        assertEquals(1, result.get(0).getId());
        assertEquals(10, result.get(0).getUserId());
    }

    @Test
    void testPatchPost_UserExists_Success() {
        // Mock
        Post post = new Post();
        post.setId(1);
        post.setUserId(1);
        when(usersRepository.checkUserExistsById(post.getUserId())).thenReturn(true);
        PostsRecord postsRecord = new PostsRecord();
        when(postsRepository.getPostById(post.getId())).thenReturn(postsRecord);
        // Call
        assertDoesNotThrow(() -> postService.patchPost(post));
    }

    @Test
    void testPatchPost_UserNotExists_Error() {
        // Mock
        Post post = new Post();
        post.setId(1);
        post.setUserId(1);
        when(usersRepository.checkUserExistsById(post.getUserId())).thenReturn(false);
        // Call
        BusinessException exception = assertThrows(BusinessException.class, () -> postService.patchPost(post));
        assertEquals("User id 1 isn't exist on users table", exception.getMessage());
    }

    @Test
    void testUpdatePost_UserExists_Success() {
        // Mock
        Post post = new Post();
        post.setId(1);
        post.setUserId(1);
        when(usersRepository.checkUserExistsById(post.getUserId())).thenReturn(true);
        PostsRecord postsRecord = new PostsRecord();
        when(postsRepository.getPostById(post.getId())).thenReturn(postsRecord);
        // Call
        assertDoesNotThrow(() -> postService.updatePost(post));
    }

    @Test
    void testUpdatePost_UserNotExists_Error() {
        // Mock
        Post post = new Post();
        post.setId(1);
        post.setUserId(1);
        when(usersRepository.checkUserExistsById(post.getUserId())).thenReturn(false);
        // Call
        BusinessException exception = assertThrows(BusinessException.class, () -> postService.patchPost(post));
        assertEquals("User id 1 isn't exist on users table", exception.getMessage());
    }

    @Test
    void testCreatePost_UserExists_Success() {
        // Mock
        Post post = new Post();
        post.setUserId(1);
        when(postsRepository.getNewPostsRecord()).thenReturn(new PostsRecord());
        when(usersRepository.checkUserExistsById(post.getUserId())).thenReturn(true);
        when(postsRepository.insertOrUpdatePost(any())).thenReturn(1);
        // Call
        CreateResourceResponse result = postService.createPost(post);
        // Verify
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testCreatePost_UserNotExists_Error() {
        // Mock
        Post post = new Post();
        post.setUserId(1);
        when(usersRepository.checkUserExistsById(post.getUserId())).thenReturn(false);
        // Call
        BusinessException exception = assertThrows(BusinessException.class, () -> postService.createPost(post));
        assertEquals("User id 1 isn't exist on users table", exception.getMessage());
    }

    @Test
    void testDeletePost_Success() {
        int postId = 1;
        // Call
        assertDoesNotThrow(() -> postService.deletePost(postId));
    }

}
