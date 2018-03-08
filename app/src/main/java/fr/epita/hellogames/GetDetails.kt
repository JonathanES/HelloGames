package fr.epita.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by jonat on 07/03/2018.
 */
interface GetDetails {
    @GET("game/details")
    fun listDetails(@Query("game_id") game_id: Int): Call<GameDetails>
}