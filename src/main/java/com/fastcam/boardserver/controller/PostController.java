package com.fastcam.boardserver.controller;

import com.fastcam.boardserver.aop.LoginCheck;
import com.fastcam.boardserver.dto.PostDTO;
import com.fastcam.boardserver.dto.UserDTO;
import com.fastcam.boardserver.dto.response.CommonResponse;
import com.fastcam.boardserver.service.impl.PostServiceImpl;
import com.fastcam.boardserver.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Log4j2
public class PostController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    public PostController(UserServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }

    // 게시글 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId, @RequestBody PostDTO postDTO) {
        postService.register(accountId, postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost", postDTO);

        return ResponseEntity.ok(commonResponse);
    }
    // 게시글 등록
    @PostMapping("create1000")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<String>> registerPost1000(String accountId) {
        UserDTO memberInfo = userService.getUserInfo(accountId);

        for(int i = 1; i <= 1000; i++) {
            PostDTO dto = PostDTO.builder()
                    .name("테스트이름" + i)
                    .fileId(i)
                    .views(0)
                    .categoryId(1)
                    .userId(memberInfo.getId())
                    .contents("테스트컨텐츠" + i)
                    .createTime(new Date())
                    .build();


            postService.register(accountId, dto);
        }
        CommonResponse<String> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost", "5000");

        return ResponseEntity.ok(commonResponse);
    }

    // 본인 게시글 조회
    @GetMapping("my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String accountId) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        List<PostDTO> postDTOList = postService.getMyPosts(memberInfo.getId());
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "myPostInfo", postDTOList);

        return ResponseEntity.ok(commonResponse);
    }

    // 게시글 수정
    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> updatePosts (String accountId,
                                                                     @PathVariable("postId") int postId,
                                                                     @RequestBody PostRequest postRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(postRequest.getUserId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();
        postService.updatePosts(postDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "updstePosts", postDTO);

        return ResponseEntity.ok(commonResponse);
    }

    // 게시글 삭제
    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deleteposts (String accountId,
                                                                           @PathVariable("postId") int postId,
                                                                           @RequestBody PostDeleteRequest postDeleteRequest) {

        UserDTO memberInfo = userService.getUserInfo(accountId);
        postService.deletePosts(memberInfo.getId(), postId);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "deletePosts", postDeleteRequest);

        return ResponseEntity.ok(commonResponse);
    }

    // -- response 객체 -- (캡슐화: 다른클래스에서 사용 불가)
    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTOs;
    }

    // -- 수정 request 객체 --
    @Getter
    @Setter
    private static class PostRequest{
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    // -- 삭제 request 객체
    @Setter
    @Getter
    private static class PostDeleteRequest {
        private int id;
        private int accountId;
    }
}
