package ai.neptune.usermanager.impl.organization;

import ai.neptune.usermanager.api.model.OrganizationDto;
import static ai.neptune.usermanager.database.generated.Tables.ORGANIZATION;
import ai.neptune.usermanager.database.generated.tables.pojos.User;
import ai.neptune.usermanager.database.generated.tables.pojos.UserInOrganization;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static ai.neptune.usermanager.database.generated.Tables.USER;
import static ai.neptune.usermanager.database.generated.Tables.USER_IN_ORGANIZATION;

@Component
@RequiredArgsConstructor
public class OrganizationDatabaseProxy {

    private final DSLContext jooq;

    public Stream<User> getUsersInOrganization(UUID organizationId) {
        return jooq.select(USER.asterisk())
                .from(USER.join(USER_IN_ORGANIZATION).onKey())
                .where(USER_IN_ORGANIZATION.ORGANIZATION_ID.eq(organizationId))
                .fetchStreamInto(User.class);
    }

    public void update(OrganizationDto organization) {
        ; // Unimplemented, because `name` is only field. Changing `name` would require exposing `id` in the API.
    }

    public void delete(String organizationName) {
        jooq.deleteFrom(ORGANIZATION).where(ORGANIZATION.NAME.eq(organizationName)).execute();
    }

    public Optional<UserInOrganization> fetchUserInOrganization(UUID organizationId, String userId) {
        return jooq.selectFrom(USER_IN_ORGANIZATION)
                .where(USER_IN_ORGANIZATION.ORGANIZATION_ID.eq(organizationId)
                .and(USER_IN_ORGANIZATION.USER_ID.eq(userId)))
                .fetchOptional()
                .map(record -> record.into(UserInOrganization.class));
    }
}
