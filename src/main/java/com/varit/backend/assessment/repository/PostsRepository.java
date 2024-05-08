package com.varit.backend.assessment.repository;

import com.varit.backend.assessment.exception.BusinessException;
import com.varit.backend.assessment.exception.DataNotFoundException;
import com.varit.backend.assessment.model.jooq.tables.Posts;
import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.jooq.exception.TooManyRowsException;
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
        try {
            return dslContext.selectFrom(Posts.POSTS).where(Posts.POSTS.ID.eq(id)).fetchSingle();
        } catch (NoDataFoundException ex) {
            var msg = String.format("No post found for id: %d", id);
            log.error(msg, ex);
            throw new DataNotFoundException(msg);
        } catch (TooManyRowsException ex) {
            var msg = String.format("Got more than one post for id: %d", id);
            log.error(msg, ex);
            throw new BusinessException(msg);
        }
    }

    public List<PostsRecord> getPostByUserId(int userId) {
        log.debug("Start query get posts by user id: {}", userId);
        return dslContext.selectFrom(Posts.POSTS).where(Posts.POSTS.USER_ID.eq(userId)).fetch();
    }

    @Transactional
    public int insertOrUpdatePost(PostsRecord postsRecord) {
        // If not query from DB it will insert otherwise update
        int rowsUpdated = postsRecord.store();
        if (rowsUpdated > 0) {
            log.info("Post insert/update successfully");
        } else {
            var msg = String.format("No changes detected when insert/update post or post with id %d not found", postsRecord.getId());
            log.error(msg);
            throw new BusinessException(msg);
        }
        postsRecord.refresh();
        return postsRecord.getId();
    }

    @Transactional
    public void deletePost(int id) {
        int rowsUpdated = dslContext.deleteFrom(Posts.POSTS).where(Posts.POSTS.ID.eq(id)).execute();
        if (rowsUpdated > 0) {
            log.info("Post with id {} delete successfully", id);
        } else {
            var msg = String.format("No changes detected when delete post or post with id %d not found", id);
            log.error(msg);
            throw new BusinessException(msg);
        }
    }

    public PostsRecord getNewPostsRecord() {
        return dslContext.newRecord(Posts.POSTS);
    }
}
