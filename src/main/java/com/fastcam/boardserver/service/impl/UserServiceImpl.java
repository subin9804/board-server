package com.fastcam.boardserver.service.impl;

import com.fastcam.boardserver.dto.UserDTO;
import com.fastcam.boardserver.exception.DuplicateIdException;
import com.fastcam.boardserver.mapper.UserProfileMapper;
import com.fastcam.boardserver.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.fastcam.boardserver.utils.SHA256Util.encryptSHA256;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserProfileMapper userProfileMapper;

    @Autowired
    public UserServiceImpl (UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    // 회원가입
    @Override
    public void register(UserDTO userProfile) {
        boolean dupleIdResult = isDuplicatedId(userProfile.getUserId());
        if (dupleIdResult) {
           throw new DuplicateIdException("중복된 아이디입니다.");
        }

        userProfile.setCreateTime(new Date());
        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));

        int insertCount = userProfileMapper.register(userProfile);

        if(insertCount != 1) {
            log.error("insertMember ERROR! {}", userProfile.getUserId());
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + userProfile
            );
        }
    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        return memberInfo;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = encryptSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
           memberInfo.setPassword(encryptSHA256(afterPassword));
           int insertCount = userProfileMapper.updatePassword(memberInfo);

        } else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteId(String id, String password) {
        String cryptoPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptoPassword);

        if(memberInfo != null) {
            userProfileMapper.deleteUserProfile(id);
        } else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
