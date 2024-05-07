package com.varit.backend.assessment.service;

import com.varit.backend.assessment.mapper.PostMapper;
import com.varit.backend.assessment.mapper.PostsRecordMapper;
import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import com.varit.backend.assessment.model.post.Post;
import com.varit.backend.assessment.repository.PostsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostsRepository postsRepository;
    private final PostMapper postMapper;
    private final PostsRecordMapper postsRecordMapper;

    public List<Post> getAllPosts() {
        List<PostsRecord> postsRecordListDao = postsRepository.getAllPosts();
        log.info("Done query all posts data then start mapping records to dto.");
        return postMapper.postsRecordListToPostList(postsRecordListDao);
    }

    public Post getPostById(int id) {
        PostsRecord postsRecordDao = postsRepository.getPostById(id);
        log.info("Done query post data by id then start mapping record to dto.");
        return postMapper.postsRecordToPost(postsRecordDao);
    }

    public Post getPostByUserId(int userId) {
        PostsRecord postsRecordDao = postsRepository.getPostByUserId(userId);
        log.info("Done query post data by user id then start mapping record to dto.");
        return postMapper.postsRecordToPost(postsRecordDao);
    }

    public void patchPost(Post post) {
        var postsRecord = postsRepository.getPostById(post.getId());
        postsRecordMapper.postMapToPostsRecordIgnoreNull(post, postsRecord);
        postsRepository.createOrUpdatePost(postsRecord);
    }

    public void updatePost(Post post) {
        var postsRecord = postsRepository.getPostById(post.getId());
        postsRecordMapper.postMapToPostsRecord(post, postsRecord);
        postsRepository.createOrUpdatePost(postsRecord);
    }

    public void createPost(Post post) {
        var postsRecord = new PostsRecord();
        postsRecordMapper.postMapToPostsRecord(post, postsRecord);
        postsRepository.createOrUpdatePost(postsRecord);
    }

    public void deletePost(int id) {
        postsRepository.deletePost(id);
    }
}
