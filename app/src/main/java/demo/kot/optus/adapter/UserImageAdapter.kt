package demo.kot.optus.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding.view.RxView
import demo.kot.optus.R
import demo.kot.optus.interfaces.ItemImageClickListener
import demo.kot.optus.model.userinfo.userprofile.MyUserProfile
import demo.kot.optus.ui.UserImageActivity
import kotlinx.android.synthetic.main.row_user_album.view.*


class UserImageAdapter(
    var context: UserImageActivity,
    var mEmpList: ArrayList<MyUserProfile>,
    private val itemClick: ItemImageClickListener
) : RecyclerView.Adapter<UserImageAdapter.ImageHolder>() {
    override fun getItemCount(): Int {
        return mEmpList.size
    }

    companion object {
        var mItemClickListener: ItemImageClickListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_user_album, parent, false)
        return ImageHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        Log.e("listbind", "listbind")
        mItemClickListener = itemClick
        holder.userImageText?.text = mEmpList[position].title
        Glide.with(context) //passing context
            .load(mEmpList[position].thumbnailUrl) //passing your url to load image.
            .dontAnimate()
            .placeholder(context.getDrawable(R.drawable.image_not_available)) //this would be your default image (like default profile or logo etc). it would be loaded at initial time and it will replace with your loaded image once glide successfully load image using url.
            .into(holder.userImage)
        RxView.clicks(holder.mView).subscribe {
            mItemClickListener!!.onItemClick(
                mEmpList[position].albumId,
                mEmpList[position].id,
                mEmpList[position].title,
                mEmpList[position].url
            )
        }

    }


    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userImageText = view.user_image_text
        val userImage: ImageView = view.user_image
        val mView = view
    }
}