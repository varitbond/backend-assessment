package com.varit.backend.assessment.mapper;

import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import com.varit.backend.assessment.model.post.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PostsRecordMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void postMapToPostsRecordIgnoreNull(Post post, @MappingTarget PostsRecord postsRecord);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void postMapToPostsRecord(Post post, @MappingTarget PostsRecord postsRecord);

}
