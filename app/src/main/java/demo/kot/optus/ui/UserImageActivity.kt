package demo.kot.optus.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import demo.kot.optus.R
import demo.kot.optus.ViewModels.AndroidViewModel
import demo.kot.optus.adapter.UserImageAdapter
import demo.kot.optus.databinding.ActivityUserImageBinding
import demo.kot.optus.interfaces.ItemImageClickListener
import demo.kot.optus.model.userinfo.userprofile.MyUserProfile
import demo.kot.optus.utils.Utils
import kotlinx.android.synthetic.main.activity_user_image.*
import java.util.stream.Collectors


@Suppress("NAME_SHADOWING")
class UserImageActivity : AppCompatActivity() {
    lateinit var id: String
    private val TAG: String = "OptusImageActivity"
    var mBindings: ActivityUserImageBinding? = null //it generates based on xml
    private lateinit var linearLayoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //data binding
        mBindings = DataBindingUtil.setContentView(this, R.layout.activity_user_image)
        linearLayoutManager = LinearLayoutManager(this)
        mBindings!!.imageRecyclerView.layoutManager = linearLayoutManager
        var bundle = intent
        id = bundle.getStringExtra("id")

        image_recyclerView.setLayoutManager(LinearLayoutManager(this))
        album_id.text = getString(R.string.album_id) + id
        if (Utils.hasNetwork(this) == true) {
            getUserProfileImages()
        } else {
            Toast.makeText(this, getString(R.string.internet_connection_not_available), Toast.LENGTH_SHORT)
                .show()
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getUserProfileImages() {
        val mAndroidViewModel = ViewModelProviders.of(this).get(AndroidViewModel::class.java)
        mAndroidViewModel.getUserProfileData()
            ?.observe(this@UserImageActivity, Observer<List<MyUserProfile>> { androidList ->
                val articleList = androidList as ArrayList<MyUserProfile>
                val filteredArticleList =
                    articleList.stream().filter { androidList -> androidList.albumId.toString().equals(id) }
                        .collect(Collectors.toList())
                image_recyclerView.adapter =
                    UserImageAdapter(this@UserImageActivity, filteredArticleList as ArrayList<MyUserProfile>, object :
                        ItemImageClickListener {
                        override fun onItemClick(albumId: Int, photoId: Int, title: String, url: String) {
                            intent = Intent(this@UserImageActivity, UserDetailsActivity::class.java)
                            intent.putExtra("albumId", albumId.toString())
                            intent.putExtra("photoId", photoId.toString())
                            intent.putExtra("title", title)
                            intent.putExtra("url", url)
                            startActivity(intent)
                        }
                    })
            })

    }
}
