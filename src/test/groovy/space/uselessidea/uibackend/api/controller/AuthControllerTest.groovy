package space.uselessidea.uibackend.api.controller

import io.restassured.http.ContentType
import org.springframework.test.annotation.DirtiesContext
import space.uselessidea.uibackend.RestAssuredSpecification

import static io.restassured.RestAssured.given

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerTest extends RestAssuredSpecification {

    def "get my info as ADMIN"() {
        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get('/api/v1/auth/me')
        then:
        response.statusCode() == 200
        verifyExpectedResponse(response, 'src/test/resources/__json/auth/me/getMyInfoAsAdmin.json')
    }

}
