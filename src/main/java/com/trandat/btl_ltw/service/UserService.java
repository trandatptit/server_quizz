package com.trandat.btl_ltw.service;


import com.trandat.btl_ltw.dto.request.UserCreationRequest;
import com.trandat.btl_ltw.dto.request.UserUpdateRequest;
import com.trandat.btl_ltw.dto.response.UserResponse;
import com.trandat.btl_ltw.entity.User;
import com.trandat.btl_ltw.enums.Role;
import com.trandat.btl_ltw.exception.AppException;
import com.trandat.btl_ltw.exception.ErrorCode;
import com.trandat.btl_ltw.mapper.KetQuaMapper;
import com.trandat.btl_ltw.mapper.UserMapper;
import com.trandat.btl_ltw.repository.DeThiRepository;
import com.trandat.btl_ltw.repository.KetQuaRepository;
import com.trandat.btl_ltw.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService{

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    DeThiRepository deThiRepository;
    KetQuaRepository ketQuaRepository;
    KetQuaMapper ketQuaMapper;

    public UserResponse creatUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }


    // truoc khi method duoc goi
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        log.info("in method getUsers");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }


    @PostAuthorize("returnObject.username == authentication.name") // sau khi method duoc goi
    public UserResponse getUser(Long userId){
        log.info("in method get user byId");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(Long userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(request.getPassword().equals("")){
            request.setPassword(user.getPassword());
            userMapper.updateUser(user, request);
        }
        else{
            userMapper.updateUser(user, request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.getKetQuas().clear();
        userRepository.deleteById(userId);
    }

}
