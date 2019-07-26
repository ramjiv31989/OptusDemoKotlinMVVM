package demo.kot.optus.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.RxView
import demo.kot.optus.MainActivity
import demo.kot.optus.R
import demo.kot.optus.interfaces.ItemClickListener
import demo.kot.optus.model.userinfo.MyUserInfo
import kotlinx.android.synthetic.main.row_user_info.view.*

class UserDataAdapter(
    var context: MainActivity,
    var mEmpList: ArrayList<MyUserInfo>,
    private val itemClick: ItemClickListener
) : RecyclerView.Adapter<UserDataAdapter.EmpHolder>() {

    override fun getItemCount(): Int {
        return mEmpList.size
    }

    companion object {
        var mItemClickListener: ItemClickListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_user_info, parent, false)
        return EmpHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EmpHolder, position: Int) {
        mItemClickListener = itemClick
        holder.userId?.text = context.getString(R.string.id) + mEmpList[position].id.toString()
        holder.userName?.text = context.getString(R.string.name) + mEmpList[position].name
        holder.userEmail?.text = context.getString(R.string.email) + mEmpList[position].email
        holder.userPhone?.text = context.getString(R.string.phone) + mEmpList[position].phone

        RxView.clicks(holder.mView).subscribe {
            //the whole view subscribed
            mItemClickListener!!.onItemClick(position, mEmpList[position].id.toString())
        }

    }


    class EmpHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId = view.user_id
        val userName = view.name
        val userEmail = view.email
        val userPhone = view.phone
        val mView = view
    }
}