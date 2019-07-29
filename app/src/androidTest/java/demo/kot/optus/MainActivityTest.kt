package demo.kot.optus

import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import demo.kot.optus.CommonService.RetrofitService
import demo.kot.optus.model.userinfo.Address
import demo.kot.optus.model.userinfo.Company
import demo.kot.optus.model.userinfo.Geo
import demo.kot.optus.model.userinfo.MyUserInfo
import demo.kot.optus.model.userinfo.userprofile.MyUserProfile
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback

class MainActivityTest {
    val liveUserInfoResponse: MutableLiveData<List<MyUserProfile>> = MutableLiveData()
    val geo = Geo(1.0, 1.0)
    val address = Address("", "", "", "", geo)
    val company = Company("", "", "")
    val myUserInfo =
        MyUserInfo(1, "Leanne Graham", "", "Sincere@april.biz", address, "1-770-736-8031 x56442", "", company)
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun recyclerViewDisplay() {
        onView(withId(R.id.recyclerView))
            .check(matches(not(isDisplayed())));
    }

    @Test
    fun testMail() {
        assertEquals("Sincere@april.biz", myUserInfo.email)
    }

    @Test
    fun testName() {
        assertEquals("Leanne Graham", myUserInfo.name)
    }

    @Test
    fun testPhone() {
        assertEquals("1-770-736-8031 x56442", myUserInfo.phone)
    }

    @Test
    fun testUserId() {
        assertEquals(1, myUserInfo.id)
    }

    //Failure Cases. So 3 failure cases

    @Test
    fun testNotMail() {
        assertNotEquals("Sincere@april.bi", myUserInfo.email)
    }

    @Test
    fun testNotName() {
        assertNotEquals("Leanne", myUserInfo.name)
    }

    @Test
    fun testNotPhone() {
        assertNotEquals("1-770-736-8031", myUserInfo.phone)
    }

    @Test
    fun testNotUserId() {
        assertNotEquals(10, myUserInfo.id)
    }

    @Test
    fun testRetrofitUserInfoCall() {
        val retrofitCall = RetrofitService.create().getUserInfo()
        val result = retrofitCall.execute()
        assertNotEquals("Some data", result.body()!!)
    }

    @Test
    fun testRetrofitUserProfileCall() {
        val retrofitCall = RetrofitService.create().getUserProfile()
        val result = retrofitCall.execute()
        assertNotEquals("Some datas", result.body()!!)
    }

    @Test
    fun testRetrofitSuccessUserProfileCall() {
        val retrofitCall = RetrofitService.create().getUserProfile()
        retrofitCall.enqueue(object : Callback<List<MyUserProfile>> {
            override fun onFailure(call: Call<List<MyUserProfile>>, t: Throwable?) {
            }

            override fun onResponse(call: Call<List<MyUserProfile>>, response: retrofit2.Response<List<MyUserProfile>>) {

                assertEquals(true, response.isSuccessful())

            }

        })
    }

    @Test
    fun testRetrofitSuccessUserInfoCall() {
        val retrofitCall = RetrofitService.create().getUserInfo()
        retrofitCall.enqueue(object : Callback<List<MyUserInfo>> {
            override fun onFailure(call: Call<List<MyUserInfo>>, t: Throwable?) {
            }

            override fun onResponse(call: Call<List<MyUserInfo>>, response: retrofit2.Response<List<MyUserInfo>>) {

                assertEquals(true, response.isSuccessful())

            }

        })
    }


}