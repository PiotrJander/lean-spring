package ai.neptune.usermanager.impl.project;

import static ai.neptune.usermanager.database.generated.Tables.PROJECT;

import ai.neptune.usermanager.database.generated.tables.pojos.Project;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProjectDatabaseProxy {

    private final DSLContext jooq;

    public Optional<Project> findProject(UUID organizationId, String projectName) {
        return jooq.selectFrom(PROJECT)
                .where(PROJECT.ORGANIZATION_ID.eq(organizationId).and(PROJECT.NAME.eq(projectName)))
                .fetchOptional()
                .map(record -> record.into(Project.class));
    }
}
