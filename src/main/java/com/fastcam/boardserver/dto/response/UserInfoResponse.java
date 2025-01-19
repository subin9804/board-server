package com.fastcam.boardserver.dto.response;

import com.fastcam.boardserver.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private  UserDTO userDTO;
}
