package space.uselessidea.uibackend.api.controller

import space.uselessidea.uibackend.RestAssuredSpecification

class SpockMockTest extends RestAssuredSpecification {


    def setup() {

    }

    def "should mock eveApiFeignClient"() {
        when: "Mock is applied"

        then: "mock response should be returned"
        eveApiFeignClient.getStatus() == "ok"
    }
}
