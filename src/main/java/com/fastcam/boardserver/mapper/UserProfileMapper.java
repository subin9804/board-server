package com.fastcam.boardserver.mapper;

import com.fastcam.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserProfileMapper {
    public UserDTO getUserProfile(@Param("id") String id);

    // 회원가입
    int insertUserProfile(@Param("id") String id, @Param("password") String password,
                          @Param("nickName") String nickName, @Param("isAdmin") boolean isAdmin,
                          @Param("createTime") Date createTime, @Param("isWithDraw") boolean isWithDraw,
                          @Param("status") UserDTO.Status status,
                          @Param("updateTime") Date updateTime);

    // 탈퇴
    int deleteUserProfile(@Param("id") String id);

    public UserDTO findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    // 중복 체크
    int idCheck(@Param("userId") String id);

    public int updatePassword(UserDTO user);
    public int updateAddress(UserDTO user);
    public int register(UserDTO user);
}
