package com.varit.backend.assessment.repository;

import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import com.varit.backend.assessment.testutil.MockPostsProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PostsRepositoryTest {

    private DSLContext dslContext;
    private PostsRepository postsRepository;

    /**
     * MockDataProvider is support to test on select query
     */

    @BeforeEach
    void setUp() {
        MockDataProvider provider = new MockPostsProvider();
        MockConnection connection = new MockConnection(provider);
        this.dslContext = DSL.using(connection, SQLDialect.MYSQL);
        this.postsRepository = new PostsRepository(dslContext);
    }

    @Test
    void testGetAllPosts_Success() {
        // Call
        List<PostsRecord> posts = postsRepository.getAllPosts();
        // Verify
        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals(1, posts.get(0).getId());
        assertEquals("Title", posts.get(0).getTitle());
    }

    @Test
    void testGetPostById_Success() {
        // Call
        PostsRecord post = postsRepository.getPostById(1);
        // Verify
        assertNotNull(post);
        assertEquals(1, post.getId());
        assertEquals("Title", post.getTitle());
    }

    @Test
    void testGetPostByUserId_Success() {
        // Call
        List<PostsRecord> posts = postsRepository.getPostByUserId(1);
        // Verify
        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals(1, posts.get(0).getId());
        assertEquals("Title", posts.get(0).getTitle());
    }
}
