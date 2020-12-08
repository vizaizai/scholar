package com.github.vizaizai.scholar.application.converter;

import com.github.vizaizai.scholar.web.dto.User;
import com.github.vizaizai.scholar.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author liaochongwei
 * @date 2020/8/14 10:55
 */
@Mapper(componentModel = "spring", uses = CommonQualifier.class)
public interface UserConverter {

    @Mapping(target = "birthday", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "balance", numberFormat = "#.00")
    @Mapping(target = "createTime", qualifiedByName = "dateTimeToTimestamp")
    UserDTO toDto(User user);

    //@InheritInverseConfiguration
    //User toEntity(UserDTO userDTO);

}
