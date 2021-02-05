package ai.neptune.usermanager.common.converters;

import ai.neptune.usermanager.api.model.OrganizationRoleEnumDto;
import ai.neptune.usermanager.database.OrganizationRole;
import org.springframework.stereotype.Component;

@Component
public class OrganizationRoleConverter {

    public OrganizationRole fromDto(OrganizationRoleEnumDto organizationRoleEnumDto) {
        switch (organizationRoleEnumDto) {
            case owner:
                return OrganizationRole.OWNER;
            case member:
                return OrganizationRole.MEMBER;
            default:
                throw new IllegalStateException();
        }
    }

    public OrganizationRoleEnumDto toDto(OrganizationRole organizationRole) {
        switch (organizationRole) {
            case OWNER:
                return OrganizationRoleEnumDto.owner;
            case MEMBER:
                return OrganizationRoleEnumDto.member;
            default:
                throw new IllegalStateException();
        }
    }
}
