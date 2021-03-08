package ai.neptune.usermanager.common.converters;

import ai.neptune.usermanager.api.model.UserDto;
import ai.neptune.usermanager.database.generated.tables.pojos.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto toDto(User user) {
        // TODO use auto conversion
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public User fromDto(String userId, UserDto userDto) {
        return new User(userId, userDto.getUsername(), userDto.getEmail(), userDto.getName());
    }
}
