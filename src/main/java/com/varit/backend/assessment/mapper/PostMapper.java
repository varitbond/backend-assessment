package com.varit.backend.assessment.mapper;

import com.varit.backend.assessment.model.jooq.tables.records.PostsRecord;
import com.varit.backend.assessment.model.post.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post postsRecordToPost(PostsRecord postsRecord);

    List<Post> postsRecordListToPostList(List<PostsRecord> postsRecordList);

}
