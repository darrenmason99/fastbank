package au.org.fastbank.ledger

data class Account(
    val accountId : String,
    val accountName : String
)

data class AccountDetails(
    val accountId : String,
    val accountName : String,
    val balance : Double
)

data class PostingRequest(
    val accountId : String,
    val amount : Double,
    val postingType : String
)

data class PostingResponse(
    val groupId : String,
    val success : Boolean
)

data class PostingGroup(
    val groupId : String,
    val postings : List<PostingRequest>
)