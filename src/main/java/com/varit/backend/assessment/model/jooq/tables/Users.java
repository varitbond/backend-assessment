/*
 * This file is generated by jOOQ.
 */
package com.varit.backend.assessment.model.jooq.tables;


import com.varit.backend.assessment.model.jooq.Backend;
import com.varit.backend.assessment.model.jooq.Keys;
import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;

import java.math.BigDecimal;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function15;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row15;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Users extends TableImpl<UsersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>backend.users</code>
     */
    public static final Users USERS = new Users();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UsersRecord> getRecordType() {
        return UsersRecord.class;
    }

    /**
     * The column <code>backend.users.id</code>.
     */
    public final TableField<UsersRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>backend.users.name</code>.
     */
    public final TableField<UsersRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>backend.users.username</code>.
     */
    public final TableField<UsersRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>backend.users.email</code>.
     */
    public final TableField<UsersRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>backend.users.street</code>.
     */
    public final TableField<UsersRecord, String> STREET = createField(DSL.name("street"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>backend.users.suite</code>.
     */
    public final TableField<UsersRecord, String> SUITE = createField(DSL.name("suite"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>backend.users.city</code>.
     */
    public final TableField<UsersRecord, String> CITY = createField(DSL.name("city"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>backend.users.zipcode</code>.
     */
    public final TableField<UsersRecord, String> ZIPCODE = createField(DSL.name("zipcode"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>backend.users.lat</code>.
     */
    public final TableField<UsersRecord, BigDecimal> LAT = createField(DSL.name("lat"), SQLDataType.DECIMAL(10, 7), this, "");

    /**
     * The column <code>backend.users.lng</code>.
     */
    public final TableField<UsersRecord, BigDecimal> LNG = createField(DSL.name("lng"), SQLDataType.DECIMAL(10, 7), this, "");

    /**
     * The column <code>backend.users.phone</code>.
     */
    public final TableField<UsersRecord, String> PHONE = createField(DSL.name("phone"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>backend.users.website</code>.
     */
    public final TableField<UsersRecord, String> WEBSITE = createField(DSL.name("website"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>backend.users.company_name</code>.
     */
    public final TableField<UsersRecord, String> COMPANY_NAME = createField(DSL.name("company_name"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>backend.users.catch_phrase</code>.
     */
    public final TableField<UsersRecord, String> CATCH_PHRASE = createField(DSL.name("catch_phrase"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>backend.users.bs</code>.
     */
    public final TableField<UsersRecord, String> BS = createField(DSL.name("bs"), SQLDataType.VARCHAR(255), this, "");

    private Users(Name alias, Table<UsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Users(Name alias, Table<UsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>backend.users</code> table reference
     */
    public Users(String alias) {
        this(DSL.name(alias), USERS);
    }

    /**
     * Create an aliased <code>backend.users</code> table reference
     */
    public Users(Name alias) {
        this(alias, USERS);
    }

    /**
     * Create a <code>backend.users</code> table reference
     */
    public Users() {
        this(DSL.name("users"), null);
    }

    public <O extends Record> Users(Table<O> child, ForeignKey<O, UsersRecord> key) {
        super(child, key, USERS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Backend.BACKEND;
    }

    @Override
    public Identity<UsersRecord, Integer> getIdentity() {
        return (Identity<UsersRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<UsersRecord> getPrimaryKey() {
        return Keys.KEY_USERS_PRIMARY;
    }

    @Override
    public Users as(String alias) {
        return new Users(DSL.name(alias), this);
    }

    @Override
    public Users as(Name alias) {
        return new Users(alias, this);
    }

    @Override
    public Users as(Table<?> alias) {
        return new Users(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(String name) {
        return new Users(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(Name name) {
        return new Users(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(Table<?> name) {
        return new Users(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row15 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row15<Integer, String, String, String, String, String, String, String, BigDecimal, BigDecimal, String, String, String, String, String> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function15<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super BigDecimal, ? super BigDecimal, ? super String, ? super String, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function15<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super BigDecimal, ? super BigDecimal, ? super String, ? super String, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
