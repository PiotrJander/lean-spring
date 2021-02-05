package ai.neptune.usermanager.api.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
public class AddUserToOrganizationDto {
    @NotNull String username;
    @NotNull OrganizationRoleEnumDto role;
}
