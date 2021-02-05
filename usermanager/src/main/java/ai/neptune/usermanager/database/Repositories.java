package ai.neptune.usermanager.database;

import ai.neptune.usermanager.database.generated.tables.daos.OrganizationDao;
import ai.neptune.usermanager.database.generated.tables.daos.ProjectDao;
import ai.neptune.usermanager.database.generated.tables.daos.UserDao;
import ai.neptune.usermanager.database.generated.tables.daos.UserInOrganizationDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Repositories {

    @Autowired
    private DSLContext dslContext;

    @Bean
    public UserDao getUserDao() {
        return new UserDao(dslContext.configuration());
    }

    @Bean
    public OrganizationDao getOrganizationDao() {
        return new OrganizationDao(dslContext.configuration());
    }

    @Bean
    public ProjectDao getProjectDao() {
        return new ProjectDao(dslContext.configuration());
    }

    @Bean
    public UserInOrganizationDao getUserInOrganizationDao() {
        return new UserInOrganizationDao(dslContext.configuration());
    }
}
