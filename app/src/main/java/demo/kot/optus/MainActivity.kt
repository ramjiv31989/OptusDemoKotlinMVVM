package demo.kot.optus

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import demo.kot.optus.ViewModels.AndroidViewModel
import demo.kot.optus.adapter.UserDataAdapter
import demo.kot.optus.databinding.ActivityMainBinding
import demo.kot.optus.interfaces.ItemClickListener
import demo.kot.optus.model.userinfo.MyUserInfo
import demo.kot.optus.ui.UserImageActivity
import demo.kot.optus.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var mBindings: ActivityMainBinding? = null
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //data binding
        mBindings = DataBindingUtil.setContentView(this, R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        mBindings!!.recyclerView.layoutManager = linearLayoutManager
        if (Utils.hasNetwork(this) == true) {
            getUserProfileDatas()
        } else {
            Toast.makeText(this, getString(R.string.internet_connection_not_available), Toast.LENGTH_SHORT)
                .show()
        }
    }

    //live data
    private fun getUserProfileDatas() {
        val mAndroidViewModel = ViewModelProviders.of(this@MainActivity).get(AndroidViewModel::class.java)
        mAndroidViewModel.getUserInfoData()?.observe(this, Observer<List<MyUserInfo>> { androidList ->
            recyclerView.adapter = UserDataAdapter(this@MainActivity, androidList as ArrayList<MyUserInfo>, object :
                ItemClickListener {
                override fun onItemClick(pos: Int, name: String) {
                    intent = Intent(this@MainActivity, UserImageActivity::class.java)
                    intent.putExtra("id", name)
                    startActivity(intent)
                }
            })
        })

    }
}

