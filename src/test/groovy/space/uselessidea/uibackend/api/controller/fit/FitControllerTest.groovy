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
                .queryParam("fitName", "Beta")
                .queryParam("page", 0)
                .queryParam("size", 10)
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

    def "should return paged fits filtered by pilot"() {
        given:
        def simpleListFit = SimpleListFit.builder()
                .uuid(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                .name("Pilot Fit")
                .shipId(587L)
                .shipName("Rifter")
                .build()

        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("fitName", "Pilot")
                .queryParam("pilots", "Viral")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .body('{"fitName":"Pilot","pilots":["Viral"],"ships":[],"page":0,"size":10}')
                .request("GET", '/api/v1/fit')

        then:
        1 * fitApiService.getFits({ SearchFitDto dto ->
            dto.fitName == "Pilot" &&
                    dto.pilots == ["Viral"] &&
                    dto.ships == [] &&
                    dto.page == 0 &&
                    dto.size == 10
        }) >> new PageImpl<>([simpleListFit])

        response.then().statusCode(200)
        response.then().body("content.size()", equalTo(1))
        response.then().body("content[0].uuid", equalTo("33333333-3333-3333-3333-333333333333"))
        response.then().body("content[0].name", equalTo("Pilot Fit"))
        response.then().body("content[0].shipId", equalTo(587))
        response.then().body("content[0].shipName", equalTo("Rifter"))
    }

    def "should return paged fits filtered by ship"() {
        given:
        def simpleListFit = SimpleListFit.builder()
                .uuid(UUID.fromString("44444444-4444-4444-4444-444444444444"))
                .name("Ship Fit")
                .shipId(17715L)
                .shipName("Orthrus")
                .build()

        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("fitName", "Ship")
                .queryParam("ships", "Orthrus")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .body('{"fitName":"Ship","pilots":[],"ships":["Orthrus"],"page":0,"size":10}')
                .request("GET", '/api/v1/fit')

        then:
        1 * fitApiService.getFits({ SearchFitDto dto ->
            dto.fitName == "Ship" &&
                    dto.pilots == [] &&
                    dto.ships == ["Orthrus"] &&
                    dto.page == 0 &&
                    dto.size == 10
        }) >> new PageImpl<>([simpleListFit])

        response.then().statusCode(200)
        response.then().body("content.size()", equalTo(1))
        response.then().body("content[0].uuid", equalTo("44444444-4444-4444-4444-444444444444"))
        response.then().body("content[0].name", equalTo("Ship Fit"))
        response.then().body("content[0].shipId", equalTo(17715))
        response.then().body("content[0].shipName", equalTo("Orthrus"))
    }

    def "should return paged fits filtered by pilot and ship"() {
        given:
        def simpleListFit = SimpleListFit.builder()
                .uuid(UUID.fromString("55555555-5555-5555-5555-555555555555"))
                .name("Pilot Ship Fit")
                .shipId(24698L)
                .shipName("Gila")
                .build()

        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("fitName", "Combo")
                .queryParam("pilots", "Viral")
                .queryParam("ships", "Gila")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .body('{"fitName":"Combo","pilots":["Viral"],"ships":["Gila"],"page":0,"size":10}')
                .request("GET", '/api/v1/fit')

        then:
        1 * fitApiService.getFits({ SearchFitDto dto ->
            dto.fitName == "Combo" &&
                    dto.pilots == ["Viral"] &&
                    dto.ships == ["Gila"] &&
                    dto.page == 0 &&
                    dto.size == 10
        }) >> new PageImpl<>([simpleListFit])

        response.then().statusCode(200)
        response.then().body("content.size()", equalTo(1))
        response.then().body("content[0].uuid", equalTo("55555555-5555-5555-5555-555555555555"))
        response.then().body("content[0].name", equalTo("Pilot Ship Fit"))
        response.then().body("content[0].shipId", equalTo(24698))
        response.then().body("content[0].shipName", equalTo("Gila"))
    }


    def "should return paged fits when pilots and ships params are missing"() {
        given:
        def simpleListFit = SimpleListFit.builder()
                .uuid(UUID.fromString("66666666-6666-6666-6666-666666666666"))
                .name("Null Safe Fit")
                .shipId(587L)
                .shipName("Rifter")
                .build()

        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .queryParam("fitName", "NullSafe")
                .queryParam("page", 0)
                .queryParam("size", 10)
                .body('{"fitName":"NullSafe","pilots":null,"ships":null,"page":0,"size":10}')
                .request("GET", '/api/v1/fit')

        then:
        1 * fitApiService.getFits({ SearchFitDto dto ->
            dto.fitName == "NullSafe" &&
                    dto.pilots != null &&
                    dto.ships != null &&
                    dto.pilots.isEmpty() &&
                    dto.ships.isEmpty() &&
                    dto.page == 0 &&
                    dto.size == 10
        }) >> new PageImpl<>([simpleListFit])

        response.then().statusCode(200)
        response.then().body("content.size()", equalTo(1))
        response.then().body("content[0].uuid", equalTo("66666666-6666-6666-6666-666666666666"))
        response.then().body("content[0].name", equalTo("Null Safe Fit"))
        response.then().body("content[0].shipId", equalTo(587))
        response.then().body("content[0].shipName", equalTo("Rifter"))
    }
    def "should return empty ship map"() {
        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get('/api/v1/fit/map')

        then:
        1 * fitApiService.getShipNameIdMap() >> [:]

        response.then().statusCode(200)
        response.then().body("size()", equalTo(0))
    }

    def "should return ship map with entries"() {
        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get('/api/v1/fit/map')

        then:
        1 * fitApiService.getShipNameIdMap() >> [
                "Rifter": 587L,
                "Orthrus": 17715L,
                "Gila": 24698L
        ]

        response.then().statusCode(200)
        response.then().body("size()", equalTo(3))
        response.then().body("Rifter", equalTo(587))
        response.then().body("Orthrus", equalTo(17715))
        response.then().body("Gila", equalTo(24698))
    }

    def "should return doctrines list"() {
        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get('/api/v1/fit/doctrines')

        then:
        1 * fitApiService.getDoctrines() >> ["Armor", "Shield"]

        response.then().statusCode(200)
        response.then().body("size()", equalTo(2))
        response.then().body("[0]", equalTo("Armor"))
        response.then().body("[1]", equalTo("Shield"))
    }
}
