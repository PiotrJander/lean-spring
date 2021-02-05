package ai.neptune.usermanager.api;

import ai.neptune.usermanager.api.model.ProjectCreationDto;
import ai.neptune.usermanager.api.model.ProjectDto;
import ai.neptune.usermanager.impl.project.ProjectResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/organizations/{organizationName}/projects")
public class ProjectController {

    private final ProjectResource projectResource;

    @GetMapping
    public Iterable<ProjectDto> list(@PathVariable("organizationName") String organizationName) {
        return projectResource.list(organizationName);
    }

    @GetMapping(path = "/{projectName}")
    public ProjectDto get(@PathVariable("organizationName") String organizationName,
                          @PathVariable("projectName") String projectName) {
        return projectResource.get(organizationName, projectName);
    }

    @PostMapping
    public ProjectDto create(@PathVariable("organizationName") String organizationName,
                             @Valid @RequestBody ProjectCreationDto projectCreationDto) {
        return projectResource.create(organizationName, projectCreationDto);
    }

    @DeleteMapping(path = "/{projectName}")
    public void delete(@PathVariable("organizationName") String organizationName,
                       @PathVariable("projectName") String projectName) {
        projectResource.delete(organizationName, projectName);
    }
}
