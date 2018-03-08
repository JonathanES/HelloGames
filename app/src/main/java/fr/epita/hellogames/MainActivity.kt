package fr.epita.hellogames


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.gson.GsonBuilder
import fr.epita.hellogames.R.layout.activity_main
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.HashMap

class MainActivity : Activity() {

    fun ImageDirectory (id: Int):Int{
        var res = 0
        when (id){
            1 -> res = R.drawable.tic_tac_toe
            2 -> res = R.drawable.hangman
            3 -> res = R.drawable.sudoku
            4 -> res = R.drawable.battleship
            5 -> res = R.drawable.minesweeper
            6 -> res = R.drawable.gameoflife
            7 -> res = R.drawable.memory
            8 -> res = R.drawable.simon
            9 -> res = R.drawable.sliding
            10 -> res = R.drawable.mastermind
        }
        return res

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val data = arrayListOf<ListGame>()
        val gameName: MutableSet<String> = mutableSetOf()
        var gameId : MutableSet<Int> = mutableSetOf()
        var gameImage : HashMap<Int, Int> = hashMapOf()
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)
        val callback = object : Callback<List<ListGame>> {
            override fun onFailure(call: Call<List<ListGame>>?, t: Throwable?) {
                Log.d("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<List<ListGame>>?,
                                    response: Response<List<ListGame>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null) {
                            data.addAll(responseData)
                            val random = Random()
                            fun rand(from: Int, to: Int) : Int {
                                return random.nextInt(to - from) + from
                            }

                            while (gameName.size < 4) {
                                val value = data[rand(0, data.size - 1)]
                                gameName.add(value.name)
                                gameId.add(value.id)
                            }
                            val x = ImageDirectory(gameId.elementAt(0))
                            first_text.setImageResource(ImageDirectory(gameId.elementAt(0)))
                            second_text.setImageResource(ImageDirectory(gameId.elementAt(1)))
                            third_text.setImageResource(ImageDirectory(gameId.elementAt(2)))
                            fourth_text.setImageResource(ImageDirectory(gameId.elementAt(3)))
                            first_text.setScaleType(ImageView.ScaleType.FIT_XY)
                            second_text.setScaleType(ImageView.ScaleType.FIT_XY)
                            third_text.setScaleType(ImageView.ScaleType.FIT_XY)
                            fourth_text.setScaleType(ImageView.ScaleType.FIT_XY)
                        }
                    }
                }
            }
        }
        setContentView(activity_main)
        first_text.setOnClickListener {// Handler code here.
        val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("id",gameId.elementAt(0))
            intent.putExtra("image",ImageDirectory(gameId.elementAt(0)))
            startActivity(intent);
        }
        second_text.setOnClickListener {// Handler code here.
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("id",gameId.elementAt(1))
            intent.putExtra("image",ImageDirectory(gameId.elementAt(1)))
            startActivity(intent);
        }
        third_text.setOnClickListener {// Handler code here.
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("id",gameId.elementAt(2))
            intent.putExtra("image",ImageDirectory(gameId.elementAt(2)))
            startActivity(intent);
        }
        fourth_text.setOnClickListener {// Handler code here.
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("id",gameId.elementAt(3))
            intent.putExtra("image",ImageDirectory(gameId.elementAt(3)))
            startActivity(intent);
        }
        service.listToDos(2).enqueue(callback)
    }
}
