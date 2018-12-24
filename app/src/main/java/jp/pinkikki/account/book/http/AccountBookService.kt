package jp.pinkikki.account.book.http

import io.reactivex.Observable
import retrofit2.http.*

interface AccountBookService {

    @Headers("Content-Type: application/json")
    @POST("torokkorio-purchase-registration")
    fun register(@Body accountBook: AccountBook, @Header("Authorization") accessToken: String): Observable<Map<String, Any>>
}