package com.ecommerce.projectapp.mapper;

import com.ecommerce.projectapp.dto.UserDto;
import com.ecommerce.projectapp.model.User;

public class UserMapper {

    public static UserDto toUserDto(User user){

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
