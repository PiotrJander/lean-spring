package ai.neptune.usermanager.api.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class UserInOrganizationDto {
    @NotNull String username;
    @NotNull OrganizationRoleEnumDto organizationRole;
}
