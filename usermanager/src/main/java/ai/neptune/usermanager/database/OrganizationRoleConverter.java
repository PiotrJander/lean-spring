package ai.neptune.usermanager.database;

import org.jooq.impl.EnumConverter;

public class OrganizationRoleConverter extends EnumConverter<String, OrganizationRole> {
    public OrganizationRoleConverter() {
        super(String.class, OrganizationRole.class);
    }
}
