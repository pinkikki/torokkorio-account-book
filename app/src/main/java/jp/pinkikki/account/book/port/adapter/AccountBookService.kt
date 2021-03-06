package jp.pinkikki.account.book.port.adapter

import io.reactivex.Observable
import jp.pinkikki.account.book.domain.model.AccountBook
import retrofit2.http.*

interface AccountBookService {

    @Headers("Content-Type: application/json")
    @POST("torokkorio-purchase-registration")
    fun register(@Body accountBook: AccountBook, @Header("Authorization") accessToken: String): Observable<Map<String, Any>>

    @Headers("Content-Type: application/json")
    @GET("torokkorio-item-list")
    fun list(): Observable<List<AccountBook>>
}