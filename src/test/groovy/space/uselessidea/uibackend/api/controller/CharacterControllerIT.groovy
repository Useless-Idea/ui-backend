package space.uselessidea.uibackend.api.controller

import io.restassured.http.ContentType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.bean.override.mockito.MockitoBean
import space.uselessidea.uibackend.RestAssuredSpecification
import space.uselessidea.uibackend.api.config.security.CharacterPrincipal
import space.uselessidea.uibackend.api.controller.character.CharacterApiService
import space.uselessidea.uibackend.domain.FeatureEnum
import space.uselessidea.uibackend.domain.character.dto.CharacterFeature
import space.uselessidea.uibackend.domain.token.port.primary.TokenPrimaryPort

import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo
import static org.mockito.ArgumentMatchers.nullable
import static org.mockito.Mockito.when

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CharacterControllerIT extends RestAssuredSpecification {

    @MockitoBean
    TokenPrimaryPort tokenPrimaryPort

    @MockitoBean
    CharacterApiService characterApiService

    def "should get user token"() {
        given:
        when(characterApiService.getCharacterFeatures(nullable(Long.class))).thenReturn([
                CharacterFeature.builder().feature(FeatureEnum.USER_STANDINGS).active(true).build(),
                CharacterFeature.builder().feature(FeatureEnum.OPEN_CONTRACTS).active(false).build(),
                CharacterFeature.builder().feature(FeatureEnum.USER_SKILL).active(true).build()
        ] as Set)
        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get('/api/v1/character/scope')
        then:
        response.then().statusCode(200)
        response.then().body("size()", equalTo(3))
        response.then().body("find { it.feature.name == 'USER_STANDINGS' }.active", equalTo(true))
        response.then().body("find { it.feature.name == 'OPEN_CONTRACTS' }.active", equalTo(false))
        response.then().body("find { it.feature.name == 'USER_SKILL' }.active", equalTo(true))
    }

    def "should return empty character map"() {
        given:
        when(characterApiService.getCharacterIdNameMap(nullable(CharacterPrincipal.class))).thenReturn([:])
        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get('/api/v1/character/map')

        then:
        response.then().statusCode(200)
        response.then().body("size()", equalTo(0))
    }

    def "should return character map with entries"() {
        given:
        when(characterApiService.getCharacterIdNameMap(nullable(CharacterPrincipal.class))).thenReturn([
                (1L): "Alpha",
                (2L): "Beta"
        ])
        when:
        def response = given()
                .header("Authorization", "Bearer ${UI_ADMIN_TOKEN}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get('/api/v1/character/map')

        then:
        response.then().statusCode(200)
        response.then().body("size()", equalTo(2))
        response.then().body("1", equalTo("Alpha"))
        response.then().body("2", equalTo("Beta"))
    }

}
