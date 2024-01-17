package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchBtn.setOnClickListener {
            RetrofitService().api.getWeather(binding.firstEd.text.toString())
                .enqueue(object : Callback<Model> {
                    override fun onResponse(call: Call<Model>, response: Response<Model>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                check(it)
                                binding.result.text =
                                    "temp:${it.main.temp}\nfeels=${it.main.feels_like}\n city:${it.name}"
                            }
                        }
                    }

                    override fun onFailure(call: Call<Model>, t: Throwable) {
                        binding.result.text = t.toString()
                    }

                })
        }
    }

    private fun check(model: Model) {
        binding.apply {
            if (model.main.temp >= 20) {
                ivBoard.setAnimation(R.raw.sun)
                ivBoard.playAnimation()
                textTv.text= "Пусть твой день будет полон света, но не слишком жаркого."
            } else if (model.main.temp <= 0) {
                ivBoard.setAnimation(R.raw.snow)
                ivBoard.playAnimation()
                textTv.text="Снежные дни – время для уюта и тепла. Желаю уютных моментов в компании близких."
            } else if (model.main.temp <= 10 && model.main.temp > 0) {
                ivBoard.setAnimation(R.raw.smog)
                ivBoard.playAnimation()
                textTv.text="С желанием заботы о вас в дни смога. Пожалуйста, следите за своим здоровьем и используйте меры предосторожности."

            } else if (model.main.temp > 10 && model.main.temp < 20) {
                ivBoard.setAnimation(R.raw.rain)
                ivBoard.playAnimation()
                textTv.text="Пусть звук капель дождя создаст мелодию уюта в твоем дне."

            }
        }
    }
}