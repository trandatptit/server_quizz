package com.trandat.btl_ltw.mapper;


import com.trandat.btl_ltw.dto.request.UserCreationRequest;
import com.trandat.btl_ltw.dto.request.UserUpdateRequest;
import com.trandat.btl_ltw.dto.response.UserResponse;
import com.trandat.btl_ltw.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);
    @Mapping(target = "id", ignore = false)
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
