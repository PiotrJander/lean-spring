package ai.neptune.usermanager.api;

import ai.neptune.usermanager.api.model.AddUserToOrganizationDto;
import ai.neptune.usermanager.api.model.OrganizationDetailsDto;
import ai.neptune.usermanager.api.model.OrganizationDto;
import ai.neptune.usermanager.impl.organization.OrganizationResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/organizations")
@RequiredArgsConstructor
public class OrganizationsController {

    private final OrganizationResource organizationResource;

    @GetMapping
    public List<OrganizationDto> list() {
        return organizationResource.list();
    }

    @GetMapping(path = "/{name}")
    public OrganizationDetailsDto get(@PathVariable("name") String name) {
        return organizationResource.get(name);
    }

    @PostMapping
    public OrganizationDto create(@Valid @RequestBody OrganizationDto organizationDto) {
        return organizationResource.create(organizationDto);
    }

    @PutMapping(path = "/{name}")
    public OrganizationDto update(@PathVariable("name") String name,
                                  @Valid @RequestBody OrganizationDto organizationDto) {
        if (!name.equals(organizationDto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return organizationResource.update(organizationDto);
    }

    @DeleteMapping(path = "/{name}")
    public void delete(@PathVariable("name") String name) {
        organizationResource.delete(name);
    }

    @PostMapping(path = "/{organizationName}/users")
    public void addUser(@PathVariable("organizationName") String organizationName,
                        @Valid @RequestBody AddUserToOrganizationDto addUserToOrganizationDto) {
        organizationResource.addUser(organizationName, addUserToOrganizationDto);
    }

    @DeleteMapping(path = "/{organizationName}/users/{username}")
    public void removeUser(@PathVariable("organizationName") String organizationName,
                           @PathVariable("username") String username) {
        organizationResource.removeUser(organizationName, username);
    }
}
