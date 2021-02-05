package ai.neptune.usermanager.impl.user;

import ai.neptune.usermanager.api.model.UserDto;
import ai.neptune.usermanager.common.UuidProvider;
import ai.neptune.usermanager.common.converters.UserConverter;
import ai.neptune.usermanager.database.generated.tables.daos.UserDao;
import ai.neptune.usermanager.database.generated.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserResource {

    private final UserDao userDao;
    private final UserConverter userConverter;
    private final UuidProvider uuidProvider;
    private final UserDatabaseProxy userDatabaseProxy;

    @Transactional(readOnly = true)
    public UserDto get(String username) {
        User user = userDao.fetchOneByUsername(username);
        return userConverter.toDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> list() {
        return userDao.findAll().stream().map(userConverter::toDto).collect(Collectors.toList());
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        String userId = uuidProvider.newUuid().toString();
        userDao.insert(userConverter.fromDto(userId, userDto));
        return userDto;
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        userDatabaseProxy.update(userDto);
        return userDto;
    }

    @Transactional
    public void delete(String username) {
        userDatabaseProxy.delete(username);
    }
}
