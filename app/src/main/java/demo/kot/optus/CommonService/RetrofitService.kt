package demo.kot.optus.CommonService

import android.arch.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import demo.kot.optus.interfaces.ApiInterface
import demo.kot.optus.model.userinfo.MyUserInfo
import demo.kot.optus.model.userinfo.userprofile.MyUserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    val liveUserInfoResponse: MutableLiveData<List<MyUserInfo>> = MutableLiveData()
    val liveUserProfileResponse: MutableLiveData<List<MyUserProfile>> = MutableLiveData()

    companion object Factory {
        var gson = GsonBuilder().setLenient().create()
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

    fun loadUserInfoData(): MutableLiveData<List<MyUserInfo>>? {
        val retrofitCall = create().getUserInfo()

        retrofitCall.enqueue(object : Callback<List<MyUserInfo>> {
            override fun onFailure(call: Call<List<MyUserInfo>>, t: Throwable?) {
            }

            override fun onResponse(call: Call<List<MyUserInfo>>, response: retrofit2.Response<List<MyUserInfo>>) {

                val list = response.body()

                liveUserInfoResponse.value = list

            }

        })

        return liveUserInfoResponse
    }

    fun loadUserProfileData(): MutableLiveData<List<MyUserProfile>>? {

        val retrofitCall = create().getUserProfile()

        retrofitCall.enqueue(object : Callback<List<MyUserProfile>> {
            override fun onFailure(call: Call<List<MyUserProfile>>, t: Throwable?) {
            }

            override fun onResponse(
                call: Call<List<MyUserProfile>>,
                response: retrofit2.Response<List<MyUserProfile>>
            ) {

                val lists = response.body()

                liveUserProfileResponse.value = lists

            }

        })

        return liveUserProfileResponse
    }
}