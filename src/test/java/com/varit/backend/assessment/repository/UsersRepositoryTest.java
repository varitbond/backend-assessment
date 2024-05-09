package com.varit.backend.assessment.repository;

import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;
import com.varit.backend.assessment.testutil.MockUsersProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UsersRepositoryTest {

    private DSLContext dslContext;
    private UsersRepository usersRepository;

    /**
     * MockDataProvider is support to test on select query
     */

    @BeforeEach
    void setUp() {
        MockDataProvider provider = new MockUsersProvider();
        MockConnection connection = new MockConnection(provider);
        this.dslContext = DSL.using(connection, SQLDialect.MYSQL);
        this.usersRepository = new UsersRepository(dslContext);
    }

    @Test
    void testGetAllUsers_Success() {
        // Call
        List<UsersRecord> users = usersRepository.getAllUsers();
        // Verify
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("John Doe", users.get(0).getName());
    }

    @Test
    void testGetUserById_Success() {
        // Call
        UsersRecord user = usersRepository.getUserById(1);
        // Verify
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
    }

    @Test
    void testGetUserByPostId_Success() {
        // Call
        List<UsersRecord> users = usersRepository.getUserByPostId(1);
        // Verify
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("John Doe", users.get(0).getName());
    }

    @Test
    void testCheckUserExistsById_Success() {
        // Call
        boolean result = usersRepository.checkUserExistsById(1);
        // Verify
        assertTrue(result);

    }
}