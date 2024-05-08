package com.varit.backend.assessment.repository;

import com.varit.backend.assessment.exception.BusinessException;
import com.varit.backend.assessment.exception.DataNotFoundException;
import com.varit.backend.assessment.model.jooq.tables.Users;
import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.NoDataFoundException;
import org.jooq.exception.TooManyRowsException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UsersRepository {

    private final DSLContext dslContext;

    public List<UsersRecord> getAllUsers() {
        log.debug("Start query get all users");
        return dslContext.selectFrom(Users.USERS).fetch();
    }

    public UsersRecord getUserById(int id) {
        log.debug("Start query get user by id: {}", id);
        try {
            return dslContext.selectFrom(Users.USERS).where(Users.USERS.ID.eq(id)).fetchSingle();
        } catch (NoDataFoundException ex) {
            var msg = String.format("No user found for id: %d", id);
            log.error(msg, ex);
            throw new DataNotFoundException(msg);
        } catch (TooManyRowsException ex) {
            var msg = String.format("Got more than one user for id: %d", id);
            log.error(msg, ex);
            throw new BusinessException(msg);
        }
    }

    @Transactional
    public int createOrUpdateUser(UsersRecord usersRecord) {
        // If not query from DB it will insert otherwise update
        int rowsUpdated = usersRecord.store();
        if (rowsUpdated > 0) {
            log.info("User insert/update successfully");
        } else {
            var msg = String.format("No changes detected when insert/update user or user with id %d not found", usersRecord.getId());
            log.error(msg);
            throw new BusinessException(msg);
        }
        usersRecord.refresh();
        return usersRecord.getId();
    }

    @Transactional
    public void deleteUser(int id) {
        int rowsUpdated = dslContext.deleteFrom(Users.USERS).where(Users.USERS.ID.eq(id)).execute();
        if (rowsUpdated > 0) {
            log.info("User with id {} delete successfully", id);
        } else {
            var msg = String.format("No changes detected when delete user or user with id %d not found", id);
            log.error(msg);
            throw new BusinessException(msg);
        }
    }

    public boolean checkUserExistsById(int id) {
        Result<Record> result = dslContext.select()
                .from(Users.USERS)
                .where(Users.USERS.ID.eq(id))
                .fetch();
        return !result.isEmpty();
    }

    public UsersRecord getNewUsersRecord() {
        return dslContext.newRecord(Users.USERS);
    }
}
