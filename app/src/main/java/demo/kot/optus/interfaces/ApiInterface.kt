package demo.kot.optus.interfaces

import demo.kot.optus.model.userinfo.MyUserInfo
import demo.kot.optus.model.userinfo.userprofile.MyUserProfile
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("users")
    fun getUserInfo(): Call<List<MyUserInfo>>

    @GET("photos")
    fun getUserProfile(): Call<List<MyUserProfile>>
}