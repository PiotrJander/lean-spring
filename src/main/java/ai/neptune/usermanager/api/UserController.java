package ai.neptune.usermanager.api;

import ai.neptune.usermanager.api.model.UserDto;
import ai.neptune.usermanager.impl.user.UserResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserResource userResource;

    @GetMapping(path = "/{username}")
    public UserDto get(@PathVariable("username") String username) {
        return userResource.get(username);
    }

    @GetMapping
    public List<UserDto> list() {
        return userResource.list();
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userResource.create(userDto);
    }

    @PutMapping(path = "/{username}")
    public UserDto update(@PathVariable("username") String username, @Valid @RequestBody UserDto userDto) {
        if (!username.equals(userDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userResource.update(userDto);
    }

    @DeleteMapping(path = "/{username}")
    public void delete(@PathVariable("username") String username) {
        userResource.delete(username);
    }
}
