package com.varit.backend.assessment.mapper;

import com.varit.backend.assessment.model.jooq.tables.records.UsersRecord;
import com.varit.backend.assessment.model.user.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "usersRecord.street", target = "address.street")
    @Mapping(source = "usersRecord.suite", target = "address.suite")
    @Mapping(source = "usersRecord.city", target = "address.city")
    @Mapping(source = "usersRecord.zipcode", target = "address.zipcode")
    @Mapping(source = "usersRecord.lat", target = "address.geo.lat")
    @Mapping(source = "usersRecord.lng", target = "address.geo.lng")
    @Mapping(source = "usersRecord.companyName", target = "company.name")
    @Mapping(source = "usersRecord.catchPhrase", target = "company.catchPhrase")
    @Mapping(source = "usersRecord.bs", target = "company.bs")
    User usersRecordToUser(UsersRecord usersRecord);

    List<User> usersRecordListToUserList(List<UsersRecord> usersRecordList);

    @AfterMapping
    default void setNestedFieldToNull(@MappingTarget User user) {
        if (user.getCompany().getBs() == null &&
                user.getCompany().getName() == null &&
                user.getCompany().getCatchPhrase() == null
        ) {
            user.setCompany(null);
        }
        if (user.getAddress().getStreet() == null &&
                user.getAddress().getSuite() == null &&
                user.getAddress().getCity() == null &&
                user.getAddress().getZipcode() == null &&
                user.getAddress().getGeo().getLng() == null &&
                user.getAddress().getGeo().getLat() == null
        ) {
            user.setAddress(null);
        }
    }
}
