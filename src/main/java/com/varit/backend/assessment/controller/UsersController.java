package com.varit.backend.assessment.controller;


import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.response.ResponseModel;
import com.varit.backend.assessment.model.response.ResponseStatus;
import com.varit.backend.assessment.model.response.ResponseStatusEnum;
import com.varit.backend.assessment.model.user.User;
import com.varit.backend.assessment.service.UsersService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseModel<User>> getUserById(@PathVariable(name = "id") int id) {
        var user = usersService.getUserById(id);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<User>();
        response.setStatus(status);
        response.setData(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel<List<User>>> getAllUsers() {
        var userList = usersService.getAllUser();
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        var response = new ResponseModel<List<User>>();
        response.setStatus(status);
        response.setData(userList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/patch")
    public ResponseEntity<ResponseModel<Void>> patchUser(@RequestBody @Validated(UpdateAndPatchGroup.class) User user) {
        usersService.patchUser(user);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseModel<Void>> updateUser(@RequestBody @Validated({Default.class, UpdateAndPatchGroup.class}) User user) {
        usersService.updateUser(user);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseModel<Void>> deleteUser(@PathVariable(name = "id") int id) {
        usersService.deleteUser(id);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<Void> response = new ResponseModel<>();
        response.setStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseModel<CreateResourceResponse>> createUser(@RequestBody @Validated(Default.class) User user) {
        var createResponse =  usersService.createUser(user);
        var status = new ResponseStatus(ResponseStatusEnum.SUCCESS);
        ResponseModel<CreateResourceResponse> response = new ResponseModel<>();
        response.setStatus(status);
        response.setData(createResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
