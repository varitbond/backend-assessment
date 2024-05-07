package com.varit.backend.assessment.service;

import com.varit.backend.assessment.mapper.UserMapper;
import com.varit.backend.assessment.mapper.UsersRecordMapper;
import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;
import com.varit.backend.assessment.model.user.User;
import com.varit.backend.assessment.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final UsersRecordMapper usersRecordMapper;

    public List<User> getAllUser() {
        List<UsersRecord> usersRecordListDao = usersRepository.getAllUsers();
        log.info("Done query all users data then start mapping records to dto.");
        return userMapper.usersRecordListToUserList(usersRecordListDao);
    }

    public User getUserById(int id) {
        UsersRecord usersRecordDao = usersRepository.getUserById(id);
        log.info("Done query user data by id then start mapping record to dto.");
        return userMapper.usersRecordToUser(usersRecordDao);
    }

    public void patchUser(User user) {
        var usersRecord = usersRepository.getUserById(user.getId());
        usersRecordMapper.userMapToUserRecordIgnoreNull(user, usersRecord);
        usersRepository.createOrUpdateUser(usersRecord);
    }

    public void updateUser(User user) {
        var usersRecord = usersRepository.getUserById(user.getId());
        usersRecordMapper.userMapToUserRecord(user, usersRecord);
        usersRepository.createOrUpdateUser(usersRecord);
    }

    public void createUser(User user) {
        var usersRecord = new UsersRecord();
        usersRecordMapper.userMapToUserRecord(user, usersRecord);
        usersRepository.createOrUpdateUser(usersRecord);
    }

    public void deleteUser(int id) {
        usersRepository.deleteUser(id);
    }
}
