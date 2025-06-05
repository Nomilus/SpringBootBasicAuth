package com.example.example_auth.mapper;

import com.example.example_auth.model.dto.req.UserRequest;
import com.example.example_auth.model.dto.res.UserResponse;
import com.example.example_auth.model.entity.User;
import com.example.example_auth.utils.PasswordUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL, imports = PasswordUtils.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", expression = "java(PasswordUtils.hashPassword(userRequest.getPassword()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRequest userRequest);

    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", expression = "java(PasswordUtils.hashPassword(userRequest.getPassword()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UserRequest userRequest, @MappingTarget User user);
}