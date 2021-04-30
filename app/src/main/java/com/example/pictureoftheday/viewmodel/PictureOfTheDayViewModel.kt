package com.example.pictureoftheday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.server.PODRetrofitImpl
import com.example.pictureoftheday.server.PODServerResponseData
import com.example.pictureoftheday.server.PictureOfTheDayData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
        private val liveData: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
        private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getData(date: String?): LiveData<PictureOfTheDayData> {
        sendServerRequest(date)
        return liveData
    }

    private fun sendServerRequest(date: String?) {
        liveData.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("Need api key"))
        } else {
            if (date.isNullOrEmpty()) {
                retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object : Callback<PODServerResponseData> {
                    override fun onResponse(
                            call: Call<PODServerResponseData>,
                            response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveData.value = PictureOfTheDayData.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveData.value = PictureOfTheDayData.Error(Throwable("Unknown error"))
                            } else {
                                liveData.value = PictureOfTheDayData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        liveData.value = PictureOfTheDayData.Error(Throwable(t))
                    }
                })
            } else {
                retrofitImpl.getRetrofitImpl().getPictureOfTheDay(date, apiKey).enqueue(object : Callback<PODServerResponseData> {
                    override fun onResponse(
                            call: Call<PODServerResponseData>,
                            response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveData.value = PictureOfTheDayData.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveData.value = PictureOfTheDayData.Error(Throwable("Unknown error"))
                            } else {
                                liveData.value = PictureOfTheDayData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        liveData.value = PictureOfTheDayData.Error(Throwable(t))
                    }
                })
            }
        }
    }
}