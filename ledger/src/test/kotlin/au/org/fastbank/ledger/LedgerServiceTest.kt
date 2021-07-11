package au.org.fastbank.ledger

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class LedgerServiceTest {

    @Inject
    lateinit var service : LedgerService

    @Test
    fun `Add account`() {
        val accName = "New account"
        val allAccounts = service.addAccount(accName)
        //Assertions.assertEquals(allAccounts.collect())
    }
}