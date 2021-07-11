package au.org.fastbank.ledger

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class LedgerService {

    var accounts = mutableMapOf<String,Account>()

    var postings = mutableListOf<PostingRequest>()

    fun addAccount(accountName: String) : Multi<Account> {
        val id = (accounts.size + 1).toString()
        accounts.putIfAbsent(id, Account(id, accountName))
        return Multi.createFrom().iterable(accounts.values)
    }
    fun getAccounts() = Multi.createFrom().iterable(accounts.values)

    fun getAccountDetails() = Multi.createFrom().iterable(accounts.values.map { detailsFromAccount(it) } )
    fun getPostings() = Multi.createFrom().iterable(postings)

    fun makePosting(posting : PostingGroup) : Boolean {
        // ensure that accounts exist and group is balanced
        val validIds = accounts.keys.containsAll(posting.postings.map { it.accountId })
        val totalCredits = posting.postings.filter { it.postingType.equals("CREDIT") }.sumByDouble { it.amount }
        val totalDebits = posting.postings.filter { it.postingType.equals("DEBIT") }.sumByDouble { it.amount }
        val balanced = totalCredits.equals(totalDebits)
        if (validIds and  balanced) {
            postings.addAll(posting.postings)
            return true
        }
        return false
    }

    private fun detailsFromAccount(account : Account) : AccountDetails =
        AccountDetails(account.accountId, account.accountName,
            postings.filter { it.accountId.equals(account.accountId) }.fold(
                0.0
            ) { acc, posting ->
                when (posting.postingType) {
                    "CREDIT" -> acc - posting.amount
                    "DEBIT" -> acc + posting.amount
                    else -> acc
                }
            }
        )
}