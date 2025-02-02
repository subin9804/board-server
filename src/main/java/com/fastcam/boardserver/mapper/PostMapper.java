package com.fastcam.boardserver.mapper;

import com.fastcam.boardserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface PostMapper {

    public int register(PostDTO postDTO);
    public List<PostDTO> selectMyPosts(int accountId);
    public void updatePosts(PostDTO postDTO);
    public void deletePosts(int postId);
}
