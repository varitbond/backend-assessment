package com.varit.backend.assessment.mapper;

import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;
import com.varit.backend.assessment.model.user.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsersRecordMapper {
    @Mapping(source = "user.address.street", target = "usersRecord.street")
    @Mapping(source = "user.address.suite", target = "usersRecord.suite")
    @Mapping(source = "user.address.city", target = "usersRecord.city")
    @Mapping(source = "user.address.zipcode", target = "usersRecord.zipcode")
    @Mapping(source = "user.address.geo.lat", target = "usersRecord.lat")
    @Mapping(source = "user.address.geo.lng", target = "usersRecord.lng")
    @Mapping(source = "user.company.name", target = "usersRecord.companyName")
    @Mapping(source = "user.company.catchPhrase", target = "usersRecord.catchPhrase")
    @Mapping(source = "user.company.bs", target = "usersRecord.bs")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void userMapToUserRecordIgnoreNull(User user, @MappingTarget UsersRecord usersRecord);

    @Mapping(source = "user.address.street", target = "usersRecord.street")
    @Mapping(source = "user.address.suite", target = "usersRecord.suite")
    @Mapping(source = "user.address.city", target = "usersRecord.city")
    @Mapping(source = "user.address.zipcode", target = "usersRecord.zipcode")
    @Mapping(source = "user.address.geo.lat", target = "usersRecord.lat")
    @Mapping(source = "user.address.geo.lng", target = "usersRecord.lng")
    @Mapping(source = "user.company.name", target = "usersRecord.companyName")
    @Mapping(source = "user.company.catchPhrase", target = "usersRecord.catchPhrase")
    @Mapping(source = "user.company.bs", target = "usersRecord.bs")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void userMapToUserRecord(User user, @MappingTarget UsersRecord usersRecord);

}
