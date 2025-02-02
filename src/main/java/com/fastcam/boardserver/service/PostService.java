package com.fastcam.boardserver.service;

import com.fastcam.boardserver.dto.PostDTO;

import java.util.List;

public interface PostService {
    void register(String id, PostDTO postDTO);
    List<PostDTO> getMyPosts(int accountId);
    void updatePosts(PostDTO postDTO);
    void deletePosts(int userId, int postId);
}
