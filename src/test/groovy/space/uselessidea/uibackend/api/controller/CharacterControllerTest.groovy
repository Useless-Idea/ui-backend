package space.uselessidea.uibackend.api.controller

import io.restassured.http.ContentType
import org.spockframework.spring.SpringBean
import org.springframework.test.annotation.DirtiesContext
import space.uselessidea.uibackend.RestAssuredSpecification
import space.uselessidea.uibackend.domain.token.port.primary.TokenPrimaryPort

import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CharacterControllerTest extends RestAssuredSpecification {

    @SpringBean
    TokenPrimaryPort tokenPrimaryPort = Mock()

    def "should get user token"() {
        given:
        tokenPrimaryPort.getAccessToken(2120732484L) >> Optional.of("eyJhbGciOiJSUzI1NiIsImtpZCI6IkpXVC1TaWduYXR1cmUtS2V5IiwidHlwIjoiSldUIn0.eyJzY3AiOlsiZXNpLXNraWxscy5yZWFkX3NraWxscy52MSIsImVzaS1jaGFyYWN0ZXJzLnJlYWRfc3RhbmRpbmdzLnYxIl0sImp0aSI6IjliMGUwYmMyLTVjM2MtNDRlZS05YWVlLWZlOTYyMGEzMzI4NyIsImtpZCI6IkpXVC1TaWduYXR1cmUtS2V5Iiwic3ViIjoiQ0hBUkFDVEVSOkVWRToyMTE2NDk2MzE1IiwiYXpwIjoiOTJmNDFkNjI1YTU2NGVkYTgzZjQ1MWE2ODNhZTg0N2EiLCJ0ZW5hbnQiOiJ0cmFucXVpbGl0eSIsInRpZXIiOiJsaXZlIiwicmVnaW9uIjoid29ybGQiLCJhdWQiOlsiOTJmNDFkNjI1YTU2NGVkYTgzZjQ1MWE2ODNhZTg0N2EiLCJFVkUgT25saW5lIl0sIm5hbWUiOiJQYXVsIFRpdCIsIm93bmVyIjoieGZKNjJtYTkyN3Vhb3drY0JLMDMrMGl4ZFdZPSIsImV4cCI6MTc2MzYyMjA2NCwiaWF0IjoxNzYzNjIwODY0LCJpc3MiOiJodHRwczovL2xvZ2luLmV2ZW9ubGluZS5jb20ifQ.MctNFGHdmavwTezchtRSvLw183p3QszGm-CvRZjNe-2T36aIaVOAykgt21FSQ2bgB4ICi3qL1tzpFZaXfXSBGH-HICQRSieDbb7wZt6HYyw8Tn6opkNXDvHa98MNKeDj3-PImgqNd3ed1wWb1xPOb-JSK-4VV1oCn0ryeyi9jwT8puRcnONWfJcLM4OMW_NiIVd7X4tw3doSluu5qQWSz7_wjkTO8E4JCOIL5RGZP0YW2FZWXsGl7GX63ZrngxvcLlvIBzgqS9sF5gQNzyspGKvujYePLPAuGqrOs7AXz1h3MHMKS6niog8LDwGsKTpPeRVYY77YCmhUjsIqf68Fhw")
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

}
