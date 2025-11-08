package space.uselessidea.uibackend

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.response.ResponseOptions
import org.spockframework.spring.SpringBean
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.ApplicationContext
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import space.uselessidea.uibackend.domain.character.dto.CharactedData
import space.uselessidea.uibackend.domain.character.port.secondary.CharacterSecondaryPort
import space.uselessidea.uibackend.infrastructure.eve.api.EveApiFeignClient
import spock.lang.Shared
import spock.lang.Specification

import java.time.Instant

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Transactional
@ActiveProfiles("test")
class RestAssuredSpecification extends Specification {


    @Shared
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        postgres.start()
        registry.add("spring.datasource.url", postgres.&getJdbcUrl)
        registry.add("spring.datasource.username", postgres.&getUsername)
        registry.add("spring.datasource.password", postgres.&getPassword)
    }

    static def UI_ADMIN_TOKEN = 'mock-lord-paridae-jwt-token'
    static def UI_ADMIN_ID = 2120732484

    @LocalServerPort
    int port

    @SpringBean
    //Rejestruje mocka przed startem kontekstu
    JwtDecoder jwtDecoder = Mock()

    @MockitoBean
    RabbitTemplate rabbitTemplate

    EveApiFeignClient eveApiFeignClient = Mock()

    @Autowired
    CharacterSecondaryPort characterSecondaryPort

    @Autowired
    ApplicationContext context
    @Autowired
    ObjectMapper objectMapper


    def setup() {
        context.getBeanFactory().registerSingleton("eveApiFeignClient", eveApiFeignClient)
        //context.getBeanFactory().registerSingleton("jwtDecoder", jwtDecoder)
        //mock eveApiStatus Response
        eveApiFeignClient.getStatus() >> "ok"
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port

        // Włącz logowanie RestAssured
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        def jwt = createMockJwtTokenFromDB(UI_ADMIN_TOKEN, UI_ADMIN_ID)
        println "Created JWT token: ${jwt.claims}"

        jwtDecoder.decode(UI_ADMIN_TOKEN) >> jwt

    }

    def verifyExpectedResponse(ResponseOptions response, String expectedResponseFilePath) {
        response.getBody().asString() ==
                objectMapper.writeValueAsString(
                        objectMapper.readTree(new File(expectedResponseFilePath).text))
    }

    def createMockJwtTokenFromDB(String token, Long characterId) {
        //to gówno muszę zmienić bo strzela mi do eve api
        CharactedData characterData = characterSecondaryPort.getCharacterData(characterId)
        return Jwt.withTokenValue(token)
                .header('alg', 'RS256')
                .header('typ', 'JWT')
                .header('kid', 'JWT-Signature-Key')
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .claim('sub', 'CHARACTER:EVE:' + characterData.getCharacterId())
                .claim('name', characterData.getCharacterName())
                .claim('scope', ['read', 'write'])
                .build()
    }
}