package ai.neptune.usermanager.api.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class OrganizationDetailsDto {
    @Valid @NotNull OrganizationDto organization;
    @Valid @NotNull List<UserInOrganizationDto> users;
    @Valid @NotNull List<ProjectInOrganizationDto> projects;
}
