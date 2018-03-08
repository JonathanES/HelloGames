package fr.epita.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by jonat on 07/03/2018.
 */
interface WebServiceInterface {
    @GET("game/list")
    fun listToDos(@Query("id") id: Int): Call<List<ListGame>>
}