package com.trandat.btl_ltw.Controller;


import com.trandat.btl_ltw.dto.request.UserCreationRequest;
import com.trandat.btl_ltw.dto.request.UserUpdateRequest;
import com.trandat.btl_ltw.dto.response.ApiResponse;
import com.trandat.btl_ltw.dto.response.UserResponse;
import com.trandat.btl_ltw.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountController {

    UserService userService;

    @PostMapping("/creatUser")
    ApiResponse<UserResponse> creatUser(@RequestBody @Valid UserCreationRequest user){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.creatUser(user));
        return apiResponse;
    }

    @GetMapping("")
    ApiResponse<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("update/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }
    @DeleteMapping("/delete/{userId}")
    ApiResponse<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .message("Successfull deleted user with id = " + userId)
                .build();
    }
}
