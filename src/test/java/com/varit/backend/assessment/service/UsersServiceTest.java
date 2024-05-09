package com.varit.backend.assessment.service;

import com.varit.backend.assessment.model.create.resource.CreateResourceResponse;
import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;
import com.varit.backend.assessment.model.user.User;
import com.varit.backend.assessment.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UsersServiceTest {

    @Autowired
    private UsersService usersService;
    @MockBean
    private UsersRepository usersRepository;

    @Test
    void testGetAllUser_Success() {
        // Mock
        UsersRecord userRecord1 = new UsersRecord();
        userRecord1.setId(1);
        userRecord1.setName("John");

        UsersRecord userRecord2 = new UsersRecord();
        userRecord2.setId(2);
        userRecord2.setName("Jane");

        when(usersRepository.getAllUsers()).thenReturn(Arrays.asList(userRecord1, userRecord2));
        // Call
        List<User> userList = usersService.getAllUser();
        // Verify
        assertNotNull(userList);
        assertEquals(2, userList.size());
        assertEquals(1, userList.get(0).getId());
        assertEquals("John", userList.get(0).getName());
        assertEquals(2, userList.get(1).getId());
        assertEquals("Jane", userList.get(1).getName());

    }

    @Test
    void testGetUserById_Success() {
        // Mock
        UsersRecord userRecord1 = new UsersRecord();
        userRecord1.setId(1);
        userRecord1.setName("John");
        when(usersRepository.getUserById(1)).thenReturn(userRecord1);
        // Call
        User user = usersService.getUserById(1);
        // Verify
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John", user.getName());
    }

    @Test
    void testGetUserByPostId_Success() {
        // Mock
        UsersRecord userRecord1 = new UsersRecord();
        userRecord1.setId(1);
        userRecord1.setName("John");
        when(usersRepository.getUserByPostId(1)).thenReturn(Arrays.asList(userRecord1));
        // Call
        List<User> userList = usersService.getUserByPostId(1);
        // Verify
        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertEquals(1, userList.get(0).getId());
        assertEquals("John", userList.get(0).getName());
    }

    @Test
    void testPatchUser_Success() {
        // Mock
        UsersRecord userRecord1 = new UsersRecord();
        userRecord1.setId(1);
        userRecord1.setName("John");
        when(usersRepository.getUserById(anyInt())).thenReturn(userRecord1);
        when(usersRepository.createOrUpdateUser(any(UsersRecord.class))).thenReturn(1);
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        // Call
        usersService.patchUser(user);
        // Verify
        verify(usersRepository, times(1)).createOrUpdateUser(any());
    }

    @Test
    void testUpdateUser_Success() {
        // Mock
        UsersRecord userRecord1 = new UsersRecord();
        userRecord1.setId(1);
        userRecord1.setName("John");
        when(usersRepository.getUserById(anyInt())).thenReturn(userRecord1);
        when(usersRepository.createOrUpdateUser(any(UsersRecord.class))).thenReturn(1);
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        // Call
        usersService.updateUser(user);
        // Verify
        verify(usersRepository, times(1)).createOrUpdateUser(any());
    }

    @Test
    void testCreateUser() {
        // Mock
        when(usersRepository.getNewUsersRecord()).thenReturn(new UsersRecord());
        when(usersRepository.createOrUpdateUser(any(UsersRecord.class))).thenReturn(1);
        User user = new User();
        user.setName("John Doe");
        // Call
        CreateResourceResponse response = usersService.createUser(user);
        // Verify
        assertEquals(1, response.getId()); // Assuming the createOrUpdateUser method returns 0 for the id
    }

    @Test
    void testDeleteUser() {
        // Mock
        doNothing().when(usersRepository).deleteUser(1);
        // Call
        usersService.deleteUser(1);
        // Verify
        verify(usersRepository, times(1)).deleteUser(1);
    }
}
