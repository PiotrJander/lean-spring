package ai.neptune.usermanager.common.converters;

import ai.neptune.usermanager.api.model.OrganizationDto;
import ai.neptune.usermanager.api.model.ProjectCreationDto;
import ai.neptune.usermanager.api.model.ProjectDto;
import ai.neptune.usermanager.api.model.ProjectInOrganizationDto;
import ai.neptune.usermanager.common.UuidProvider;
import ai.neptune.usermanager.database.generated.tables.pojos.Organization;
import ai.neptune.usermanager.database.generated.tables.pojos.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectConverter {

    private final UuidProvider uuidProvider;

    public ProjectInOrganizationDto toInOrganizationDto(Project project) {
        return ProjectInOrganizationDto.builder().name(project.getName()).build();
    }

    public ProjectDto toDto(Project project, Organization organization) {
        OrganizationDto organizationDto = OrganizationDto.builder()
                .name(organization.getName())
                .build();
        return ProjectDto.builder()
                .name(project.getName())
                .organization(organizationDto)
                .build();
    }

    public Project fromCreationDto(UUID organizationId, ProjectCreationDto projectCreationDto) {
        return new Project(uuidProvider.newUuid(), organizationId, projectCreationDto.getProjectName());
    }
}
