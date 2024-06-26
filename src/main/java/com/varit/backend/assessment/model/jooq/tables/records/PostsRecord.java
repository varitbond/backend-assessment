/*
 * This file is generated by jOOQ.
 */
package com.varit.backend.assessment.model.jooq.tables.records;


import com.varit.backend.assessment.model.jooq.tables.Posts;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PostsRecord extends UpdatableRecordImpl<PostsRecord> implements Record4<Integer, Integer, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>backend.posts.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>backend.posts.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>backend.posts.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>backend.posts.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>backend.posts.title</code>.
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>backend.posts.title</code>.
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>backend.posts.body</code>.
     */
    public void setBody(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>backend.posts.body</code>.
     */
    public String getBody() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Integer, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Posts.POSTS.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Posts.POSTS.USER_ID;
    }

    @Override
    public Field<String> field3() {
        return Posts.POSTS.TITLE;
    }

    @Override
    public Field<String> field4() {
        return Posts.POSTS.BODY;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getUserId();
    }

    @Override
    public String component3() {
        return getTitle();
    }

    @Override
    public String component4() {
        return getBody();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getUserId();
    }

    @Override
    public String value3() {
        return getTitle();
    }

    @Override
    public String value4() {
        return getBody();
    }

    @Override
    public PostsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public PostsRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    @Override
    public PostsRecord value3(String value) {
        setTitle(value);
        return this;
    }

    @Override
    public PostsRecord value4(String value) {
        setBody(value);
        return this;
    }

    @Override
    public PostsRecord values(Integer value1, Integer value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PostsRecord
     */
    public PostsRecord() {
        super(Posts.POSTS);
    }

    /**
     * Create a detached, initialised PostsRecord
     */
    public PostsRecord(Integer id, Integer userId, String title, String body) {
        super(Posts.POSTS);

        setId(id);
        setUserId(userId);
        setTitle(title);
        setBody(body);
        resetChangedOnNotNull();
    }
}
