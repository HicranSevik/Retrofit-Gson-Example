package io.githup.hicransevik.navigationbottom

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import io.githup.hicransevik.navigationbottom.databinding.ActivityMainBinding
import io.githup.hicransevik.navigationbottom.model.Categories
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.button1.setOnClickListener{
            binding.textView2.setText("All")
        }
        */
        /*val buttons = Arrays


        Array.size 4
        // all
        Array[0].setOnClickListener {

        }
        Array[1]
        Array[2]
        Array[3]*/

        //var buttons[]:
        /*doNetworkRequest {
            createButtons(it)
        }*/
        doNetworkRequest()

        /*navigationButtons[0].setOnClickListener {
            binding.textView2.setText("Apple")
        }

        navigationButtons[1].setOnClickListener {
            binding.textView2.setText("Banana")
        }

        navigationButtons[2].setOnClickListener {
            binding.textView2.setText("Cherry")
        }

        navigationButtons[3].setOnClickListener {
            binding.textView2.setText("WaterMelon")
        }*/
    }

    fun doNetworkRequest(){
    //fun doNetworkRequest( cb:(String)-> ArrayList<Button> ) {
        var buttons: ArrayList<Button> = ArrayList<Button>()
        val apiInterface = CategoriesService.create().getCategories()
        apiInterface.enqueue(object : Callback<List<Categories>> {
            override fun onResponse( call: Call<List<Categories>>?, response: Response<List<Categories>>?) {

                if (response?.body() != null) {

                    val gsonBuilder = GsonBuilder().create()
                    val bodyResponse = gsonBuilder.toJson(response.body())
                    val jsonArray = JSONArray(bodyResponse)

                    for (i in 0 until jsonArray.length()) {
                        val categoryName = jsonArray.getJSONObject(i).getString("name")
                        Log.i("categoryName: ", categoryName)
                        //cb(categoryName, buttons)
                        /*---------------delete here later*/
                        val buttonDynamic = Button(applicationContext)
                        buttonDynamic.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        buttonDynamic.text = categoryName

                        binding.buttonLayout.addView(buttonDynamic)
                        buttons.add(buttonDynamic)
                    }
                }
                buttons[0].setOnClickListener {
                    binding.textView2.setText("elma")
                }
                buttons[1].setOnClickListener {
                    binding.textView2.setText("armut")
                }
                buttons[2].setOnClickListener {
                    binding.textView2.setText("kiraz")
                }
                buttons[3].setOnClickListener {
                    binding.textView2.setText("karpuz")
                }
            }

            override fun onFailure(call: Call<List<Categories>>?, t: Throwable?) = Unit
        })
    }
        fun createButtons(categoryName: String, buttons:ArrayList<Button>): Unit {
                val buttonDynamic = Button(applicationContext)
                buttonDynamic.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                buttonDynamic.text = categoryName

                binding.buttonLayout.addView(buttonDynamic)
                buttons.add(buttonDynamic)
                //return buttons

            //TODO delete here later
                buttonDynamic.setOnClickListener {
                    binding.textView2.setText("Elma")
                }
        }
}



/*
response.body =>

[
  Categories(category_id="0", name=All),
  Categories(category_id="1", name=Popular),
  Categories(category_id="2", name=Recent),
  Categories(category_id="3", name=Recommended)
]
 */

/*
With GSON
[
    {"category_id":"0","name":"All"},
    {"category_id":"1","name":"Popular"},
    {"category_id":"2","name":"Recent"},
    {"category_id":"3","name":"Recommended"}
]
 */

interface CategoriesService {
    @GET("categories/")
    fun getCategories(): Call<List<Categories>>

    companion object {

        var BASE_URL = "https://6155694393e3550017b089b3.mockapi.io/api/products/"

        fun create() : CategoriesService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(CategoriesService::class.java)

        }
    }
}
