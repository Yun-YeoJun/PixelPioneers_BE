package com.example.PixelPioneers.controller;

import com.example.PixelPioneers.DTO.UserRequest;
import com.example.PixelPioneers.DTO.UserResponse;
import com.example.PixelPioneers.Service.UserService;
import com.example.PixelPioneers.config.jwt.JWTTokenProvider;
import com.example.PixelPioneers.config.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import java.util.List;

@Api(tags = {"유저 API"})
@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final UserService userService;

    /**
     * 이메일 중복 확인
     */
    @ApiOperation(value="이메일 중복 확인", notes = "중복된 이메일이 있는지 확인합니다.")
    @PostMapping("/email/check")
    public ResponseEntity<?> emailCheck(@RequestBody @Valid UserRequest.EmailCheckDTO emailCheckDTO, Errors errors) {
        userService.emailCheck(emailCheckDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(true));
    }

    /**
     * 회원가입
     */
    @ApiOperation(value="회원가입", notes = "맨 아래 requestDTO만 입력하면 됩니당.")
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Errors errors) {
        userService.join(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(true));
    }

    /**
     * 로그인
     */
    @ApiOperation(value="로그인", notes = "맨 아래 requestDTO만 입력하면 됩니당.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        String jwt = userService.login(requestDTO);
        return ResponseEntity.ok().header(JWTTokenProvider.HEADER, jwt).body(ApiUtils.success(true));
    }

    /**
     * 카카오 로그인
     */
    @GetMapping("/login/kakao")
    public void kakaoLogin(@RequestParam String code) {
        String access_token = userService.getKakaoAccessToken(code);
    }

    @PostMapping("/users")
    public ResponseEntity<?> userList(@RequestBody @Valid UserRequest.UserListDTO requestDTO, Errors errors) {
        List<UserResponse.UserListDTO> responseDTOs = userService.findUserList(requestDTO);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }
}
