package io.sabitovka.tms.api.util.mapper;

import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.dto.UserInfoDto;
import io.sabitovka.tms.api.model.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterUserDto registerUserDto);

    RegisterUserDto toRegisterDto(User user);

    User toEntity(UserInfoDto userInfoDto);

    UserInfoDto toInfoDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserInfoDto userInfoDto, @MappingTarget User user);
}
