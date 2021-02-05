package ai.neptune.usermanager.api.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
public class OrganizationDto {
    @NotNull String name;
}
