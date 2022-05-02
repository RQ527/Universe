package com.example.universe.view

import android.R.attr
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.universe.base.BaseMvvmFragment
import com.example.universe.R
import com.example.universe.databinding.FragmentMineBinding
import com.example.universe.viewmodel.MineFragmentViewModel
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.MediaStore

import android.content.Intent
import android.R.attr.data
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.example.universe.model.toast


/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class MineFragment() : BaseMvvmFragment<MineFragmentViewModel, FragmentMineBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun afterCreate() {
        //查看本地是否有数据
        val user = mViewModel.getUserInfo()
        mViewDataBind.tvMineFragSaying.text = user[1]
        mViewDataBind.tvMineFragUsername.text = user[0]


        mViewDataBind.ivMineFragUserIcon.setOnClickListener {
            //打开相册
            val intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, 2)
        }
        mViewDataBind.tvMineFragAchievement.setOnClickListener {
            startActivity(Intent(this.activity, AchievementActivity::class.java))
        }
        //持久化签名
        mViewDataBind.tvMineFragUsername.setOnClickListener {
            InputDialog(this.requireContext(), "输入你的网名", object : InputDialog.ClickCallBack {
                override fun onYesClick(dialog: InputDialog, text: String?) {
                    if (text != null && text != "") {
                        mViewDataBind.tvMineFragUsername.text = text
                        mViewModel.saveUserInfo("username",text)
                    }
                    dialog.dismiss()
                }
            }).show()
        }
        //持久化签名
        mViewDataBind.tvMineFragSaying.setOnClickListener {
            InputDialog(this.requireContext(), "输入你的个性签名", object : InputDialog.ClickCallBack {
                override fun onYesClick(dialog: InputDialog, text: String?) {
                    if (text != null && text != "") {
                        mViewDataBind.tvMineFragSaying.text = text
                        mViewModel.saveUserInfo("usersaying",text)
                    }
                    dialog.dismiss()
                }
            }).show()
        }
        mViewDataBind.tvMineFragTimer.setOnClickListener {
            toast(this.requireContext(),"未开发~")
        }
        mViewDataBind.advice.setOnClickListener {
            toast(this.requireContext(),"未开发~")
        }
        mViewDataBind.setting.setOnClickListener {
            toast(this.requireContext(), "未开发~")
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                val uri: Uri? = data.data
                mViewDataBind.ivMineFragUserIcon.setImageURI(uri)
                val sp = this@MineFragment.requireActivity()
                    .getSharedPreferences("image", Context.MODE_PRIVATE)

                val edit = sp.edit()
                edit.putString("head", uri.toString())
                edit.apply()
            }

        }
    }


}