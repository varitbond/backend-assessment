package com.varit.backend.assessment.testutil;

import com.varit.backend.assessment.model.jooq.tables.Posts;
import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

public class MockPostsProvider implements MockDataProvider {
    @Override
    public MockResult[] execute(MockExecuteContext ctx) {
        DSLContext dslContext = DSL.using(SQLDialect.MYSQL);
        MockResult[] mock = new MockResult[1];
        String sql = ctx.sql();
        if (sql.toUpperCase().startsWith("SELECT")) {
            Result<PostsRecord> result = dslContext.newResult(Posts.POSTS);
            result.add(dslContext.newRecord(Posts.POSTS)
                    .values(
                            1,
                            1,
                            "Title",
                            "Body"
                    )
            );
            mock[0] = new MockResult(1, result);
        }
        return mock;
    }
}
