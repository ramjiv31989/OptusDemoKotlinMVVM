package demo.kot.optus.ViewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import demo.kot.optus.CommonService.RetrofitService
import demo.kot.optus.model.userinfo.MyUserInfo
import demo.kot.optus.model.userinfo.userprofile.MyUserProfile

class AndroidViewModel : ViewModel() {
    private val mService = RetrofitService()

    fun getUserInfoData(): MutableLiveData<List<MyUserInfo>>? {
        return mService.loadUserInfoData()
    }

    fun getUserProfileData(): MutableLiveData<List<MyUserProfile>>? {
        return mService.loadUserProfileData()
    }
}