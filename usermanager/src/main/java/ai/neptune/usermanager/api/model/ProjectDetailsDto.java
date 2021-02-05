package ai.neptune.usermanager.api.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class ProjectDetailsDto {
    @Valid @NotNull ProjectDto project;
    @Valid @NotNull List<UserDto> users;
}
