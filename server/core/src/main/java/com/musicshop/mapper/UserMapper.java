package com.musicshop.mapper;


import com.musicshop.dto.response.UserInfoResponse;
import com.musicshop.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserInfoResponse userToDto(AppUser user);
}
