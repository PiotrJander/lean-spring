package ai.neptune.usermanager.impl.organization;

import ai.neptune.usermanager.api.model.AddUserToOrganizationDto;
import ai.neptune.usermanager.api.model.OrganizationDetailsDto;
import ai.neptune.usermanager.api.model.OrganizationDto;
import ai.neptune.usermanager.api.model.OrganizationRoleEnumDto;
import ai.neptune.usermanager.common.UuidProvider;
import ai.neptune.usermanager.common.converters.OrganizationConverter;
import ai.neptune.usermanager.common.converters.OrganizationRoleConverter;
import ai.neptune.usermanager.database.OrganizationRole;
import ai.neptune.usermanager.database.generated.Tables;
import ai.neptune.usermanager.database.generated.tables.daos.OrganizationDao;
import ai.neptune.usermanager.database.generated.tables.daos.ProjectDao;
import ai.neptune.usermanager.database.generated.tables.daos.UserDao;
import ai.neptune.usermanager.database.generated.tables.daos.UserInOrganizationDao;
import ai.neptune.usermanager.database.generated.tables.pojos.Organization;
import ai.neptune.usermanager.database.generated.tables.pojos.Project;
import ai.neptune.usermanager.database.generated.tables.pojos.User;
import ai.neptune.usermanager.database.generated.tables.pojos.UserInOrganization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class OrganizationResource {

    private final OrganizationDao organizationDao;
    private final ProjectDao projectDao;
    private final UserDao userDao;
    private final UserInOrganizationDao userInOrganizationDao;
    private final OrganizationConverter organizationConverter;
    private final OrganizationRoleConverter organizationRoleConverter;
    private final OrganizationDatabaseProxy organizationDatabaseProxy;
    private final UuidProvider uuidProvider;

    @Transactional(readOnly = true)
    public List<OrganizationDto> list() {
        return organizationDao.findAll().stream().map(organizationConverter::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrganizationDetailsDto get(String name) {
        Organization organization = organizationDao.fetchOneByName(name);
        Stream<UserInOrganization> usersInOrganization =
                userInOrganizationDao.fetchByOrganizationId(organization.getId()).stream();
        Stream<Project> projectsInOrganization = projectDao.fetchByOrganizationId(organization.getId()).stream();
        return organizationConverter.toDetailsDto(organization, usersInOrganization, projectsInOrganization);
    }

    @Transactional
    public OrganizationDto create(OrganizationDto organizationDto) {
        UUID organizationId = uuidProvider.newUuid();
        organizationDao.insert(organizationConverter.fromDto(organizationId, organizationDto));
        return organizationDto;
    }

    @Transactional
    public OrganizationDto update(OrganizationDto organizationDto) {
        organizationDatabaseProxy.update(organizationDto);
        return organizationDto;
    }

    @Transactional
    public void delete(String name) {
        organizationDatabaseProxy.delete(name);
    }

    @Transactional
    public void addUser(String organizationName, AddUserToOrganizationDto addUserToOrganizationDto) {
        OrganizationRole organizationRole = organizationRoleConverter.fromDto(addUserToOrganizationDto.getRole());
        Organization organization = organizationDao.fetchOptional(Tables.ORGANIZATION.NAME, organizationName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = userDao.fetchOptional(Tables.USER.USERNAME, addUserToOrganizationDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userInOrganizationDao.insert(new UserInOrganization(user.getId(), organization.getId(), organizationRole));
    }

    @Transactional
    public void removeUser(String organizationName, String username) {
        Organization organization = organizationDao.fetchOptional(Tables.ORGANIZATION.NAME, organizationName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = userDao.fetchOptional(Tables.USER.USERNAME, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserInOrganization userInOrganization =
                organizationDatabaseProxy.fetchUserInOrganization(organization.getId(), user.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userInOrganizationDao.delete(userInOrganization);
    }
}
