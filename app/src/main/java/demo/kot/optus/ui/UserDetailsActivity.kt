package demo.kot.optus.ui

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import demo.kot.optus.R
import demo.kot.optus.databinding.ActivityDetailsBinding

class UserDetailsActivity : AppCompatActivity() {
    lateinit var mBindings: ActivityDetailsBinding //it generates based on xml
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBindings = DataBindingUtil.setContentView(this, R.layout.activity_details)
        linearLayoutManager = LinearLayoutManager(this)
        val bundle = intent
        val albumId: String? = bundle.getStringExtra("albumId")
        val photoId: String? = bundle.getStringExtra("photoId")
        val title: String? = bundle.getStringExtra("title")
        val url: String? = bundle.getStringExtra("url")
        mBindings.albumId.text = getString(R.string.album_id) + albumId
        mBindings.photoId.text = getString(R.string.photo_id) + photoId
        mBindings.userImageText.text = title
        Glide.with(this)
            .load(url)
            .dontAnimate()
            .placeholder(getDrawable(R.drawable.image_not_available))
            .into(mBindings.userImage)
    }
}
