package ai.neptune.usermanager.common.converters;

import ai.neptune.usermanager.api.model.OrganizationDetailsDto;
import ai.neptune.usermanager.api.model.OrganizationDto;
import ai.neptune.usermanager.api.model.UserInOrganizationDto;
import ai.neptune.usermanager.database.generated.tables.pojos.Organization;
import ai.neptune.usermanager.database.generated.tables.pojos.Project;
import ai.neptune.usermanager.database.generated.tables.pojos.User;
import ai.neptune.usermanager.database.generated.tables.pojos.UserInOrganization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class OrganizationConverter {

    private final ProjectConverter projectConverter;
    private final OrganizationRoleConverter organizationRoleConverter;

    public OrganizationDto toDto(Organization organization) {
        return OrganizationDto.builder()
                .name(organization.getName())
                .build();
    }

    private UserInOrganizationDto toUserInOrganizationDto(UserInOrganization userInOrganization) {
        return UserInOrganizationDto.builder()
                .username(userInOrganization.getUserId()) // TODO fix, use username instead of user_id, maybe join
                .organizationRole(organizationRoleConverter.toDto(userInOrganization.getRole()))
                .build();
    }

    public OrganizationDetailsDto toDetailsDto(Organization organization,
                                               Stream<UserInOrganization> usersInOrganization,
                                               Stream<Project> projects) {
        return OrganizationDetailsDto.builder()
                .organization(toDto(organization))
                .projects(projects.map(projectConverter::toInOrganizationDto).collect(Collectors.toList()))
                .users(usersInOrganization.map(this::toUserInOrganizationDto).collect(Collectors.toList()))
                .build();
    }

    public Organization fromDto(UUID organizationId, OrganizationDto organizationDto) {
        return new Organization(organizationId, organizationDto.getName());
    }
}
