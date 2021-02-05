package ai.neptune.usermanager.impl.user;

import ai.neptune.usermanager.api.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import static ai.neptune.usermanager.database.generated.Tables.USER;

@Component
@RequiredArgsConstructor
public class UserDatabaseProxy {

    private final DSLContext jooq;

    public void update(UserDto userDto) {
        jooq.update(USER)
                .set(USER.NAME, userDto.getName())
                .set(USER.EMAIL, userDto.getEmail())
                .set(USER.NAME, userDto.getName())
                .where(USER.USERNAME.eq(userDto.getUsername()))
                .execute();
    }

    public void delete(String username) {
        jooq.deleteFrom(USER)
                .where(USER.USERNAME.eq(username))
                .execute();
    }
}
