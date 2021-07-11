package au.org.fastbank.ledger

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import org.jboss.resteasy.reactive.MultipartForm
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/fastbank")
class LedgerResource {

    @Inject
    @field: Default
    lateinit var service : LedgerService

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("accounts")
    fun getAccounts() : Multi<Account> = service.getAccounts()

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("accountDetails")
    fun getAccountDetails() : Multi<AccountDetails> = service.getAccountDetails()

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("accounts")
    fun addAccount(accountName : String) : Multi<Account> {
        return service.addAccount(accountName)
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("postings")
    fun makePosting(postingGroup: PostingGroup) : Uni<PostingResponse> {
        return Uni.createFrom().item(PostingResponse(postingGroup.groupId, service.makePosting(postingGroup)))
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("postings")
    fun getPostings() : Multi<PostingRequest> = service.getPostings()
}