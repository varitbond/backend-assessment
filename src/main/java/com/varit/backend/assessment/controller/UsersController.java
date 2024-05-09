package com.varit.backend.assessment.controller;


import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.response.ResponseModel;
import com.varit.backend.assessment.model.response.ResponseStatus;
import com.varit.backend.assessment.model.response.ResponseStatusEnum;
import com.varit.backend.assessment.model.user.User;
import com.varit.backend.assessment.service.UsersService;
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
@RequestMapping("/users")
@Tag(name = "Users", description = "Access to users resource")
@SecurityRequirement(name = "security_auth")
public class UsersController {

    private final UsersService usersService;

    @Operation(summary = "Get User By Id", description = "This endpoint retrieves user by using id.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user.")
    @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "409", description = "Found more than one user on the same id.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<User>> getUserById(@PathVariable(name = "id") int id) {
        var user = usersService.getUserById(id);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<User>();
        response.setStatus(status);
        response.setData(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get User By Post id", description = "This endpoint retrieves user by using post id.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user.")
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @GetMapping(value = "/get/by-postid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<List<User>>> getUserByPostId(@RequestParam(name = "postId") int postId) {
        var userList = usersService.getUserByPostId(postId);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<List<User>>();
        response.setStatus(status);
        response.setData(userList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get all Users", description = "This endpoint retrieves all users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users.")
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<List<User>>> getAllUsers() {
        var userList = usersService.getAllUser();
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<List<User>>();
        response.setStatus(status);
        response.setData(userList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Patch user", description = "This api use for patch user in users database.")
    @ApiResponse(responseCode = "200", description = "Successfully patch user.")
    @ApiResponse(responseCode = "400", description = "Missing required fields.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "409", description = "No changes detected when patch user.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @PatchMapping(value = "/patch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<Void>> patchUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Only id field is required to patch user. (Model re-use swagger will shown as all required.)")
            @RequestBody @Validated(UpdateAndPatchGroup.class)
            User user
    ) {
        usersService.patchUser(user);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update user", description = "This api use for update user in users database.")
    @ApiResponse(responseCode = "200", description = "Successfully update user.")
    @ApiResponse(responseCode = "400", description = "Missing required fields.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "409", description = "No changes detected when update user.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<Void>> updateUser(@RequestBody @Validated({Default.class, UpdateAndPatchGroup.class}) User user) {
        usersService.updateUser(user);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete user", description = "This api use for delete user in users database.")
    @ApiResponse(responseCode = "200", description = "Successfully delete user.")
    @ApiResponse(responseCode = "409", description = "No changes detected when delete user or user not found.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<Void>> deleteUser(@PathVariable(name = "id") int id) {
        usersService.deleteUser(id);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Create user", description = "This api use for create user in users database.")
    @ApiResponse(responseCode = "201", description = "Successfully create user.")
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ResponseModel.class)))
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<CreateResourceResponse>> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "For id field is not required to create user. (Model re-use swagger will shown as all required.)")
            @RequestBody @Validated(Default.class)
            User user
    ) {
        var createResponse = usersService.createUser(user);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<CreateResourceResponse> response = new ResponseModel<>();
        response.setStatus(status);
        response.setData(createResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
