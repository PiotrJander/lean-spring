package ai.neptune.usermanager.impl.user

import ai.neptune.usermanager.api.model.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
@Transactional
class UserResourceTest extends Specification {

  @Subject @Autowired UserResource userResource

  def "should create and get user"() {
    given:
    // TODO use random model gen
    def user = new UserDto("kanaan", "p@j.com", "Piotr")

    when:
    userResource.create(user)

    and:
    def actualUser = userResource.get("kanaan")

    then:
    actualUser == user
  }
}
