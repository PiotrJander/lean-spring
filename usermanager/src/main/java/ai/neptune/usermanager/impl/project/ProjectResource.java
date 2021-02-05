package ai.neptune.usermanager.impl.project;

import ai.neptune.usermanager.api.model.ProjectCreationDto;
import ai.neptune.usermanager.api.model.ProjectDto;
import ai.neptune.usermanager.common.converters.ProjectConverter;
import ai.neptune.usermanager.database.generated.tables.daos.OrganizationDao;
import ai.neptune.usermanager.database.generated.tables.daos.ProjectDao;
import ai.neptune.usermanager.database.generated.tables.pojos.Organization;
import ai.neptune.usermanager.database.generated.tables.pojos.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectResource {

    private final ProjectDao projectDao;
    private final OrganizationDao organizationDao;
    private final ProjectConverter projectConverter;
    private final ProjectDatabaseProxy projectDatabaseProxy;

    @Transactional(readOnly = true)
    public Iterable<ProjectDto> list(String organizationName) {
        Organization organization = organizationDao.fetchOneByName(organizationName);
        return projectDao
                .fetchByOrganizationId(organization.getId())
                .stream()
                .map(project -> projectConverter.toDto(project, organization))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDto get(String organizationName, String projectName) {
        Organization organization = organizationDao.fetchOneByName(organizationName);
        return projectDatabaseProxy.findProject(organization.getId(), projectName)
                .map(project -> projectConverter.toDto(project, organization))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ProjectDto create(String organizationName, ProjectCreationDto projectCreationDto) {
        Organization organization = organizationDao.fetchOneByName(organizationName);
        projectDao.insert(projectConverter.fromCreationDto(organization.getId(), projectCreationDto));
        return projectDatabaseProxy
                .findProject(organization.getId(), projectCreationDto.getProjectName())
                .map(project -> projectConverter.toDto(project, organization))
                .orElse(null);
    }

    @Transactional
    public void delete(String organizationName, String projectName) {
        Organization organization = organizationDao.fetchOneByName(organizationName);
        Project project = projectDatabaseProxy.findProject(organization.getId(), projectName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        projectDao.delete(project);
    }
}
