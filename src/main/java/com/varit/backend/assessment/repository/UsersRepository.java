package com.varit.backend.assessment.repository;

import com.varit.backend.assessment.model.jooq.tables.Users;
import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
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
        return dslContext.selectFrom(Users.USERS).where(Users.USERS.ID.eq(id)).fetchSingle();
    }

    @Transactional
    public void createOrUpdateUser(UsersRecord usersRecord) {
        // If not query from DB it will insert otherwise update
        int rowsUpdated = usersRecord.store();
        if (rowsUpdated > 0) {
            log.info("User insert/update successfully.");
        } else {
            log.error("No changes detected or user with ID " + usersRecord.getId() + " not found.");
        }
    }

    @Transactional
    public void deleteUser(int id) {
        int rowsUpdated = dslContext.deleteFrom(Users.USERS).where(Users.USERS.ID.eq(id)).execute();
        if (rowsUpdated > 0) {
            log.info("User delete successfully.");
        } else {
            log.error("No changes detected or user with ID " + id + " not found.");
        }
    }
}
