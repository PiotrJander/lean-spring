package ai.neptune.usermanager.api.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
public class UserDto {
    @NotNull String username;
    @NotNull String email;
    @NotNull String name;
}
