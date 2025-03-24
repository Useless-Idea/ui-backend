package space.uselessidea.uibackend

import io.restassured.RestAssured
import io.restassured.response.ResponseOptions
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import space.uselessidea.uibackend.domain.character.dto.CharactedData
import space.uselessidea.uibackend.domain.character.port.secondary.CharacterSecondaryPort
import spock.lang.Specification

import java.time.Instant

import static org.mockito.Mockito.when

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Transactional
class RestAssuredSpecification extends Specification {

    static def UI_ADMIN_TOKEN = 'mock-lord-paridae-jwt-token'

    static def UI_ADMIN_ID = 2120732484

    @LocalServerPort
    int port

    @MockitoBean
    JwtDecoder jwtDecoder

    @MockitoBean
    RabbitTemplate rabbitTemplate

    @Autowired
    CharacterSecondaryPort characterSecondaryPort;

    def objectMapper = new ObjectMapper()

    def setup() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port

        when(jwtDecoder.decode(UI_ADMIN_TOKEN)).thenReturn(createMockJwtTokenFromDB(UI_ADMIN_TOKEN, UI_ADMIN_ID))
    }

    def verifyExpectedResponse(ResponseOptions response, String expectedResponseFilePath) {
        response.getBody().asString() ==
                objectMapper.writeValueAsString(
                        objectMapper.readTree(new File(expectedResponseFilePath).text))
    }

    private static def createMockJwtToken(String token, UUID userId, String name, String role, UUID companyId) {
        Jwt.withTokenValue(token)
                .header('alg', 'RS256')
                .header('typ', 'JWT')
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .claim('ums_uuid', userId)
                .claim('company_uuid', companyId)
                .claim('given_name', 'Test')
                .claim('family_name', name)
                .claim('email', 'test@email.com')
                .claim('company_name', 'Test Company Name')
                .claim('roles', Collections.singletonList(role))
                .build()
    }


    def createMockJwtTokenFromDB(String token, Long characterId) {
        CharactedData characterData = characterSecondaryPort.getCharacterData(characterId)
        Jwt.withTokenValue(token)
                .header('alg', 'RS256')
                .header('typ', 'JWT')
                .header('kid', 'JWT-Signature-Key')
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .claim('sub', 'CHARACTER:EVE:' + characterData.getCharacterId())
                .claim('name', characterData.getCharacterName())
                .build()


    }

    private static def createMockJwtToken(String token, UUID userId, String firstName, String lastName,
                                          Set<String> roles, UUID companyUUID, String companyName, String email) {

        Jwt.withTokenValue(token)
                .header('alg', 'RS256')
                .header('typ', 'JWT')
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .claim('ums_uuid', userId)
                .claim('company_uuid', companyUUID)
                .claim('given_name', firstName)
                .claim('family_name', lastName)
                .claim('company_name', companyName)
                .claim('roles', roles)
                .claim('email', email)
                .build()
    }
}
