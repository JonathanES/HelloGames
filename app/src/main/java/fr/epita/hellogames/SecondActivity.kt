package fr.epita.hellogames

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val data = arrayListOf<GameDetails>()
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: GetDetails = retrofit.create(GetDetails::class.java)
        val callback = object : Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>?, t: Throwable?) {
                Log.d("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<GameDetails>?,
                                    response: Response<GameDetails>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null) {

                            fun instatianteText(item: GameDetails){
                                name_def.text = item.name
                                type_def.text = item.type
                                nb_def.text = item.players
                                year_def.text = item.year.toString()
                                description.text = item.description_en
                            }
                            instatianteText(responseData)

                            button.setOnClickListener{
                                val browse = Intent( Intent.ACTION_VIEW , Uri.parse(responseData.url))
                                startActivity( browse )
                            }
                        }
                    }
                }
            }
        }

        val intent = intent
        val id = intent.getIntExtra("id",0)
        val image = intent.getIntExtra("image",0)

        setContentView(R.layout.activity_second)
        imageId.setImageResource(image)
        imageId.setScaleType(ImageView.ScaleType.FIT_XY)
        service.listDetails(id).enqueue(callback)

    }

}
