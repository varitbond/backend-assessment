package com.varit.backend.assessment.repository;

import com.varit.backend.assessment.exception.BusinessException;
import com.varit.backend.assessment.exception.DataNotFoundException;
import com.varit.backend.assessment.model.jooq.tables.Posts;
import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectWhereStep;
import org.jooq.Table;
import org.jooq.exception.NoDataFoundException;
import org.jooq.exception.TooManyRowsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostsRepositoryTest {

    @Mock
    private DSLContext dslContext;

    @InjectMocks
    private PostsRepository postsRepository;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(dslContext);
    }

    @Test
    void testGetAllPosts_Success() {
        // Mock data
        SelectWhereStep<PostsRecord> selectWhereStep = mock(SelectWhereStep.class);
        Result<PostsRecord> result = mock(Result.class);
        List<PostsRecord> posts = Arrays.asList(new PostsRecord(), new PostsRecord());
        when(dslContext.selectFrom(any(Table.class))).thenReturn(mock(SelectWhereStep.class));
        when(selectWhereStep.fetch()).thenReturn(result);
        when(result.into(PostsRecord.class)).thenReturn(posts);

        // Test method
        List<PostsRecord> resultPosts = postsRepository.getAllPosts();

        // Verify interactions and assertions
        verify(dslContext).selectFrom(Posts.POSTS);
        assertEquals(posts, resultPosts);
    }
    @Test
    void testGetPostById_DataFound() {
        // Mock data
        PostsRecord postRecord = new PostsRecord();
        when(dslContext.selectFrom(any(Table.class)).where(any(Condition.class)).fetchSingle()).thenReturn(postRecord);

        // Test method
        PostsRecord resultPost = postsRepository.getPostById(1);

        // Verify interactions and assertions
        verify(dslContext).selectFrom(Posts.POSTS);
        verify(dslContext).selectFrom(Posts.POSTS).where(Posts.POSTS.ID.eq(1));
        assertEquals(postRecord, resultPost);
    }

    @Test
    void testGetPostById_DataNotFound() {
        // Mock NoDataFoundException
        when(dslContext.selectFrom(any(Table.class)).where(any(Condition.class)).fetchSingle()).thenThrow(NoDataFoundException.class);

        // Test method and assert exception
        assertThrows(DataNotFoundException.class, () -> postsRepository.getPostById(1));
    }

    @Test
    void testGetPostById_TooManyRows() {
        // Mock TooManyRowsException
        when(dslContext.selectFrom(any(Table.class)).where(any(Condition.class)).fetchSingle()).thenThrow(TooManyRowsException.class);

        // Test method and assert exception
        assertThrows(BusinessException.class, () -> postsRepository.getPostById(1));
    }

    @Test
    void testGetPostByUserId_Success() {
        // Mock data
        List<PostsRecord> posts = Arrays.asList(new PostsRecord(), new PostsRecord());
        Result<PostsRecord> result = mock(Result.class);
        when(dslContext.selectFrom(any(Table.class)).where(any(Condition.class)).fetch()).thenReturn(result);
        when(result.into(PostsRecord.class)).thenReturn(posts);

        // Test method
        List<PostsRecord> resultPosts = postsRepository.getPostByUserId(1);

        // Verify interactions and assertions
        verify(dslContext).selectFrom(Posts.POSTS);
        verify(dslContext).selectFrom(Posts.POSTS).where(Posts.POSTS.USER_ID.eq(1));
        assertEquals(posts, resultPosts);
    }

    @Test
    void testInsertOrUpdatePost_Success() {
        // Mock store operation
        PostsRecord postRecord = new PostsRecord();
        when(postRecord.store()).thenReturn(1);

        // Test method
        assertDoesNotThrow(() -> postsRepository.insertOrUpdatePost(postRecord));
    }

    @Test
    void testInsertOrUpdatePost_NoChangesDetected() {
        // Mock store operation
        PostsRecord postRecord = new PostsRecord();
        when(postRecord.store()).thenReturn(0);

        // Test method and assert exception
        BusinessException exception = assertThrows(BusinessException.class, () -> postsRepository.insertOrUpdatePost(postRecord));
        assertEquals("No changes detected when insert/update post or post with id 0 not found", exception.getMessage());
    }

    @Test
    void testDeletePost_Success() {
        // Mock delete operation
        when(dslContext.deleteFrom(any(Table.class)).where(any(Condition.class)).execute()).thenReturn(1);

        // Test method
        assertDoesNotThrow(() -> postsRepository.deletePost(1));
    }

    @Test
    void testDeletePost_NoChangesDetected() {
        // Mock delete operation
        when(dslContext.deleteFrom(any(Table.class)).where(any(Condition.class)).execute()).thenReturn(0);

        // Test method and assert exception
        BusinessException exception = assertThrows(BusinessException.class, () -> postsRepository.deletePost(1));
        assertEquals("No changes detected when delete post or post with id 1 not found", exception.getMessage());
    }
}
