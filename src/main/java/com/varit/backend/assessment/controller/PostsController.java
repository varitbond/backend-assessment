package com.varit.backend.assessment.controller;

import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.post.Post;
import com.varit.backend.assessment.model.response.ResponseModel;
import com.varit.backend.assessment.model.response.ResponseStatus;
import com.varit.backend.assessment.model.response.ResponseStatusEnum;
import com.varit.backend.assessment.service.PostService;
import com.varit.backend.assessment.validation.UpdateAndPatchGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Tag(name = "Posts", description = "Access to posts resource")
@SecurityRequirement(name = "security_auth")
public class PostsController {

    private final PostService postService;

    @Operation(summary = "Get Post By Id", description = "This endpoint retrieves post by using id.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved post.")
    @ApiResponse(responseCode = "404", description = "Post not found.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "409", description = "Found more than one post on the same id.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<Post>> getPostById(@PathVariable(name = "id") int id) {
        var post = postService.getPostById(id);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<Post>();
        response.setStatus(status);
        response.setData(post);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get Post By userId", description = "This endpoint retrieves post by using user id.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved post.")
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @GetMapping(value = "/get/by-userid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<List<Post>>> getPostByUserId(@RequestParam(name = "userId") int userId) {
        var posts = postService.getPostByUserId(userId);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<List<Post>>();
        response.setStatus(status);
        response.setData(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get all Posts", description = "This endpoint retrieves all posts.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all posts.")
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<List<Post>>> getAllPosts() {
        var allPosts = postService.getAllPosts();
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<List<Post>>();
        response.setStatus(status);
        response.setData(allPosts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Patch post", description = "This api use for patch post in posts database.")
    @ApiResponse(responseCode = "200", description = "Successfully patch post.")
    @ApiResponse(responseCode = "400", description = "Missing required fields.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "404", description = "Post not found.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "409", description = "No changes detected when patch post.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @PatchMapping(value = "/patch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<Void>> patchPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Only id field is required to patch post. (Model re-use swagger will shown as all required.)")
            @RequestBody @Validated(UpdateAndPatchGroup.class)
            Post post
    ) {
        postService.patchPost(post);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update post", description = "This api use for update post in posts database.")
    @ApiResponse(responseCode = "200", description = "Successfully update post.")
    @ApiResponse(responseCode = "400", description = "Missing required fields.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "404", description = "Post not found.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "409", description = "No changes detected when update post.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<Void>> updatePost(@RequestBody @Validated({Default.class, UpdateAndPatchGroup.class}) Post post) {
        postService.updatePost(post);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete post", description = "This api use for delete post in posts database.")
    @ApiResponse(responseCode = "200", description = "Successfully delete post.")
    @ApiResponse(responseCode = "409", description = "No changes detected when delete post or post not found.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<Void>> deletePost(@PathVariable(name = "id") int id) {
        postService.deletePost(id);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Create post", description = "This api use for create post in posts database.")
    @ApiResponse(responseCode = "201", description = "Successfully create post.")
    @ApiResponse(responseCode = "409", description = "User id for create post is not exist.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<CreateResourceResponse>> createPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "For id field is not required to create post. (Model re-use swagger will shown as all required.)")
            @RequestBody @Validated(Default.class)
            Post post
    ) {
        var createResponse = postService.createPost(post);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<CreateResourceResponse> response = new ResponseModel<>();
        response.setStatus(status);
        response.setData(createResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
