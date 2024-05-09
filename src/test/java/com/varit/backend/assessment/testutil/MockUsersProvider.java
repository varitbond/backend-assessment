package com.varit.backend.assessment.testutil;

import com.varit.backend.assessment.model.jooq.tables.Users;
import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import java.math.BigDecimal;

public class MockUsersProvider implements MockDataProvider {
    @Override
    public MockResult[] execute(MockExecuteContext ctx) {
        DSLContext dslContext = DSL.using(SQLDialect.MYSQL);
        MockResult[] mock = new MockResult[1];
        String sql = ctx.sql();
        if (sql.toUpperCase().startsWith("SELECT")) {
            Result<UsersRecord> result = dslContext.newResult(Users.USERS);
            result.add(dslContext.newRecord(Users.USERS)
                    .values(
                            1,
                            "John Doe",
                            "johndoe",
                            "johndoe@example.com",
                            "123 Main St",
                            "Apt 101",
                            "Springfield",
                            "12345",
                            new BigDecimal("40.7128"),
                            new BigDecimal("-74.0060"),
                            "123-456-7890",
                            "www.example.com",
                            "Example Company",
                            "Making examples since 2000",
                            "Business services"
                    )
            );
            mock[0] = new MockResult(1, result);
        }
        return mock;
    }
}
