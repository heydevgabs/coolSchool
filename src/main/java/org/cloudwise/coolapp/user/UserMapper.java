package org.cloudwise.coolapp.user;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getSchoolId());
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .schoolId(userDTO.getSchoolId())
                .build();
    }
}
