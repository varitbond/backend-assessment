package com.varit.backend.assessment.controller;

import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.post.Post;
import com.varit.backend.assessment.model.response.ResponseModel;
import com.varit.backend.assessment.model.response.ResponseStatus;
import com.varit.backend.assessment.model.response.ResponseStatusEnum;
import com.varit.backend.assessment.service.PostService;
import com.varit.backend.assessment.validation.UpdateAndPatchGroup;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostsController {

    private final PostService postService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseModel<Post>> getPostById(@PathVariable(name = "id") int id) {
        var post = postService.getPostById(id);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<Post>();
        response.setStatus(status);
        response.setData(post);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/by-userid")
    public ResponseEntity<ResponseModel<List<Post>>> getPostByUserId(@RequestParam(name = "userId") int userId) {
        var posts = postService.getPostByUserId(userId);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<List<Post>>();
        response.setStatus(status);
        response.setData(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel<List<Post>>> getAllPosts() {
        var allPosts = postService.getAllPosts();
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<List<Post>>();
        response.setStatus(status);
        response.setData(allPosts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/patch")
    public ResponseEntity<ResponseModel<Void>> patchPost(@RequestBody @Validated(UpdateAndPatchGroup.class) Post post) {
        postService.patchPost(post);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseModel<Void>> updatePost(@RequestBody @Validated({Default.class, UpdateAndPatchGroup.class}) Post post) {
        postService.updatePost(post);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseModel<Void>> deletePost(@PathVariable(name = "id") int id) {
        postService.deletePost(id);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseModel<CreateResourceResponse>> createPost(@RequestBody @Validated(Default.class) Post post) {
        var createResponse = postService.createPost(post);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<CreateResourceResponse> response = new ResponseModel<>();
        response.setStatus(status);
        response.setData(createResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
