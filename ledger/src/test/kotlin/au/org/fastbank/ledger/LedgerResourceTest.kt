package au.org.fastbank.ledger

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import io.vertx.core.json.Json
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

@QuarkusTest
class LedgerResourceTest {

    @Test
    fun testGetAccounts() {
        Given {
            contentType(ContentType.JSON)
        } When {
            get("/fastbank/accounts")
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun `New account added`() {
        val newAccount = "Test account"
        val accounts = Given {
            body(Json.encode(newAccount))
        } When {
            post("/fastbank/accounts")
        } Then {
            statusCode(200)
            contentType(ContentType.JSON)
        }
        accounts.log()
    }

}