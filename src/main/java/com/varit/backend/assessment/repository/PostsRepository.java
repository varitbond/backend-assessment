package com.varit.backend.assessment.repository;

import com.varit.backend.assessment.model.jooq.tables.Posts;
import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostsRepository {

    private final DSLContext dslContext;

    public List<PostsRecord> getAllPosts() {
        log.debug("Start query get all posts");
        return dslContext.selectFrom(Posts.POSTS).fetch();
    }

    public PostsRecord getPostById(int id) {
        log.debug("Start query get post by id: {}", id);
        return dslContext.selectFrom(Posts.POSTS).where(Posts.POSTS.ID.eq(id)).fetchSingle();
    }

    public PostsRecord getPostByUserId(int userId) {
        log.debug("Start query get post by user id: {}", userId);
        return dslContext.selectFrom(Posts.POSTS).where(Posts.POSTS.USER_ID.eq(userId)).fetchSingle();
    }

    @Transactional
    public void createOrUpdatePost(PostsRecord postsRecord) {
        // If not query from DB it will insert otherwise update
        int rowsUpdated = postsRecord.store();
        if (rowsUpdated > 0) {
            log.info("User insert/update successfully.");
        } else {
            log.error("No changes detected or post with ID " + postsRecord.getId() + " not found.");
        }
    }

    @Transactional
    public void deletePost(int id) {
        int rowsUpdated = dslContext.deleteFrom(Posts.POSTS).where(Posts.POSTS.ID.eq(id)).execute();
        if (rowsUpdated > 0) {
            log.info("Post delete successfully.");
        } else {
            log.error("No changes detected or post with ID " + id + " not found.");
        }
    }
}
