package com.varit.backend.assessment.service;

import com.varit.backend.assessment.exception.BusinessException;
import com.varit.backend.assessment.mapper.PostMapper;
import com.varit.backend.assessment.mapper.PostsRecordMapper;
import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import com.varit.backend.assessment.model.post.Post;
import com.varit.backend.assessment.repository.PostsRepository;
import com.varit.backend.assessment.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
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

    public List<Post> getPostByUserId(int userId) {
        List<PostsRecord> postsRecordListDao = postsRepository.getPostByUserId(userId);
        log.info("Done query posts data by user id then start mapping records to dto.");
        return postMapper.postsRecordListToPostList(postsRecordListDao);
    }

    public void patchPost(Post post) {
        if (Objects.nonNull(post.getUserId())) {
            this.checkUserExist(post.getUserId());
        }
        var postsRecord = postsRepository.getPostById(post.getId());
        postsRecordMapper.postMapToPostsRecordIgnoreNull(post, postsRecord);
        postsRepository.insertOrUpdatePost(postsRecord);
    }

    public void updatePost(Post post) {
        this.checkUserExist(post.getUserId());
        var postsRecord = postsRepository.getPostById(post.getId());
        postsRecordMapper.postMapToPostsRecord(post, postsRecord);
        postsRepository.insertOrUpdatePost(postsRecord);
    }

    public CreateResourceResponse createPost(Post post) {
        this.checkUserExist(post.getUserId());
        var postsRecord = postsRepository.getNewPostsRecord();
        postsRecordMapper.postMapToPostsRecord(post, postsRecord);
        var id = postsRepository.insertOrUpdatePost(postsRecord);
        return new CreateResourceResponse(id);
    }

    public void deletePost(int id) {
        postsRepository.deletePost(id);
    }

    private void checkUserExist(int userId) {
        var isUserExist = usersRepository.checkUserExistsById(userId);
        if (!isUserExist) {
            var msg = String.format("User id %d isn't exist on users table", userId);
            log.error(msg);
            throw new BusinessException(msg);
        }
    }
}
