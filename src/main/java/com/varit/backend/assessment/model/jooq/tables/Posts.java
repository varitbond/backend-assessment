/*
 * This file is generated by jOOQ.
 */
package com.varit.backend.assessment.model.jooq.tables;


import com.varit.backend.assessment.model.jooq.Backend;
import com.varit.backend.assessment.model.jooq.Indexes;
import com.varit.backend.assessment.model.jooq.Keys;
import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Posts extends TableImpl<PostsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>backend.posts</code>
     */
    public static final Posts POSTS = new Posts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PostsRecord> getRecordType() {
        return PostsRecord.class;
    }

    /**
     * The column <code>backend.posts.id</code>.
     */
    public final TableField<PostsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>backend.posts.user_id</code>.
     */
    public final TableField<PostsRecord, Integer> USER_ID = createField(DSL.name("user_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>backend.posts.title</code>.
     */
    public final TableField<PostsRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>backend.posts.body</code>.
     */
    public final TableField<PostsRecord, String> BODY = createField(DSL.name("body"), SQLDataType.CLOB.nullable(false), this, "");

    private Posts(Name alias, Table<PostsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Posts(Name alias, Table<PostsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>backend.posts</code> table reference
     */
    public Posts(String alias) {
        this(DSL.name(alias), POSTS);
    }

    /**
     * Create an aliased <code>backend.posts</code> table reference
     */
    public Posts(Name alias) {
        this(alias, POSTS);
    }

    /**
     * Create a <code>backend.posts</code> table reference
     */
    public Posts() {
        this(DSL.name("posts"), null);
    }

    public <O extends Record> Posts(Table<O> child, ForeignKey<O, PostsRecord> key) {
        super(child, key, POSTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Backend.BACKEND;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.POSTS_IDX_USER_ID);
    }

    @Override
    public Identity<PostsRecord, Integer> getIdentity() {
        return (Identity<PostsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<PostsRecord> getPrimaryKey() {
        return Keys.KEY_POSTS_PRIMARY;
    }

    @Override
    public Posts as(String alias) {
        return new Posts(DSL.name(alias), this);
    }

    @Override
    public Posts as(Name alias) {
        return new Posts(alias, this);
    }

    @Override
    public Posts as(Table<?> alias) {
        return new Posts(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Posts rename(String name) {
        return new Posts(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Posts rename(Name name) {
        return new Posts(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Posts rename(Table<?> name) {
        return new Posts(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super Integer, ? super Integer, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Integer, ? super Integer, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
