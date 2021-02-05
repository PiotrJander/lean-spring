package ai.neptune.usermanager.api.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
public class ProjectDto {
    @Valid @NotNull OrganizationDto organization;
    @NotNull String name;
}
