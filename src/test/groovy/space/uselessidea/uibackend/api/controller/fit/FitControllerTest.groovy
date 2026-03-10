package space.uselessidea.uibackend.api.controller.fit

import io.restassured.http.ContentType
import org.spockframework.spring.SpringBean
import org.springframework.data.domain.PageImpl
import org.springframework.test.annotation.DirtiesContext
import space.uselessidea.uibackend.RestAssuredSpecification
import space.uselessidea.uibackend.api.controller.fit.dto.SimpleListFit
import space.uselessidea.uibackend.domain.fit.dto.FitDto
import space.uselessidea.uibackend.domain.fit.dto.FitForm
import space.uselessidea.uibackend.domain.fit.dto.SearchFitDto

import java.util.UUID

import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FitControllerTest extends RestAssuredSpecification {

    @SpringBean
    FitApiService fitApiService = Mock()

    def "should add fit"() {
        given:
        def fitDto = FitDto.builder()
                .uuid(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .name("Alpha Fit")
                .shipId(587L)
                .shipName("Rifter")
                .eft("[Rifter, Alpha Fit]")
                .description("PVP fit")
                .build()

        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body('{"fit":"[Rifter, Alpha Fit]","description":"PVP fit"}')
                .post('/api/v1/fit')

        then:
        1 * fitApiService.addFit(_ as FitForm) >> fitDto

        response.then().statusCode(200)
        response.then().body("uuid", equalTo("11111111-1111-1111-1111-111111111111"))
        response.then().body("name", equalTo("Alpha Fit"))
        response.then().body("shipId", equalTo(587))
        response.then().body("shipName", equalTo("Rifter"))
    }

    def "should return paged fits"() {
        given:
        def simpleListFit = SimpleListFit.builder()
                .uuid(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .name("Beta Fit")
                .shipId(17715L)
                .shipName("Orthrus")
                .build()

        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body('{"fitName":"Beta","pilots":[],"ships":[],"page":0,"size":10}')
                .request("GET", '/api/v1/fit')

        then:
        1 * fitApiService.getFits(_ as SearchFitDto) >> new PageImpl<>([simpleListFit])

        response.then().statusCode(200)
        response.then().body("content.size()", equalTo(1))
        response.then().body("content[0].uuid", equalTo("22222222-2222-2222-2222-222222222222"))
        response.then().body("content[0].name", equalTo("Beta Fit"))
        response.then().body("content[0].shipId", equalTo(17715))
        response.then().body("content[0].shipName", equalTo("Orthrus"))
    }

}


