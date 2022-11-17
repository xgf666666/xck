package com.example.xck.ui.person.activity


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.UtilsTransActivity
import com.example.xck.BuildConfig
import com.example.xck.R
import com.example.xck.base.BaseMvpActivity
import com.example.xck.bean.Select
import com.example.xck.bean.UpLoadFile
import com.example.xck.common.Constants
import com.example.xck.dialog.SelectDialog
import com.example.xck.ui.person.mvp.contract.ProjectMessageEditContract
import com.example.xck.ui.person.mvp.persenter.ProjectMessageEditPersenter
import com.example.xck.utils.filechoose.FileBrowseActivity
import com.example.xck.utils.filechoose.FxFileDialogArgs
import com.example.xck.utils.filechoose.FxHelp
import com.xx.baseutilslibrary.common.ImageChooseHelper
import kotlinx.android.synthetic.main.activity_project_message_edit.*
import kotlinx.android.synthetic.main.activity_project_message_edit.ivPerson
import kotlinx.android.synthetic.main.activity_project_message_edit.llAddress
import kotlinx.android.synthetic.main.activity_project_message_edit.llFinance
import kotlinx.android.synthetic.main.activity_project_message_edit.llFleid
import kotlinx.android.synthetic.main.activity_project_message_edit.tvAddress
import kotlinx.android.synthetic.main.activity_project_message_edit.tvFinance
import kotlinx.android.synthetic.main.ic_title.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class ProjectMessageEditActivity : BaseMvpActivity<ProjectMessageEditPersenter>(),ProjectMessageEditContract.View,
    View.OnClickListener {
    private var imageChooseHelper: ImageChooseHelper? = null
    private var image=""
    private var fileString=""
    private var selectDialog:SelectDialog?=null
    private var filids: ArrayList<Int>?= ArrayList()
    private var fanances: ArrayList<Int>?= ArrayList()
    private var addresss: ArrayList<Int>?= ArrayList()
    private var isImage=false
    override fun getActivityLayoutId(): Int =R.layout.activity_project_message_edit

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initEvent() {
        rlFile.setOnClickListener {
            isImage=false
            showFileChooser()
        }
        ivPerson.setOnClickListener {
            isImage=true
            showEditAvatarDialog()
        }
        llTime.setOnClickListener {
            showDatePickerDialog(this,2,tvTime,Calendar.getInstance(Locale.CHINA))
        }
        tvSend.setOnClickListener {
            getPresenter().setProject(Constants.getToken(),etWx.text.toString(),image,tvTime.text.toString(),
                etProjectIntroduce.text.toString(),etFinance.text.toString(),etoperation.text.toString(),etAdvantage.text.toString()
            ,etHistory.text.toString(),fileString,etPersonNum.text.toString(),
                filids!!.toIntArray(),fanances!!.toIntArray(),addresss!!.toIntArray())
        }
        etProjectIntroduce.addTextChangedListener {
            tvProjectIntroduce.text="${etProjectIntroduce.text.length}/300"
        }
        etFinance.addTextChangedListener {
            tvFinances.text="${etFinance.text.length}/300"
        }
        etoperation.addTextChangedListener {
            tvOperation.text="${etoperation.text.length}/300"
        }
        etAdvantage.addTextChangedListener {
            tvAdvantage.text="${etAdvantage.text.length}/300"
        }
        etHistory.addTextChangedListener {
            tvHistory.text="${etHistory.text.length}/300"
        }
        etPersonNum.addTextChangedListener {
            tvPersonNum.text="${etPersonNum.text.length}/300"
        }
        llFinance.setOnClickListener(this)
        llFleid.setOnClickListener(this)
        llAddress.setOnClickListener(this)
    }

    override fun createPresenter(): ProjectMessageEditPersenter =ProjectMessageEditPersenter(this)
    override fun initData() {
        tvTilte.text="创业项目信息完善"
        initImageChooseHelper()

    }
    private fun initImageChooseHelper(){
        imageChooseHelper = ImageChooseHelper.Builder()
            .setUpActivity(this)
            .setAuthority("${BuildConfig.APPLICATION_ID}.fileprovider")//设置文件提供者
            .setDirPath(Constants.DOWNLOAD_PATH)//设置文件存储路径
            .isCrop(false)//开启裁剪
            .setCompressQuality(100)//压缩质量[1,100]
            .setSize(120, 120)//裁剪尺寸
            .setOnFinishChooseAndCropImageListener { bitmap, file ->
                //                    显示选好得图片
//                iv_hand.setImageBitmap(bitmap)
                ivPerson.setImageBitmap(bitmap)
                showLoadingDialog()
                getPresenter().upload(file)
            }
            .setOnFinishChooseImageListener { uri, file ->
                ivPerson.setImageURI(uri)
                showLoadingDialog()
                getPresenter().upload(file)
            }
            .create()
    }
    /**
     * 显示修改头像弹窗
     */
    private fun showEditAvatarDialog() {
        //选图弹窗
        //请求相机和内存读取权限
        PermissionUtils.permission(
//            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE)
            .callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    //被给予权限,调起选图弹窗
                    imageChooseHelper!!.startImageChoose();
                }

                override fun onDenied() {

                }
            })
            .rationale { utilsTransActivity: UtilsTransActivity, shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest -> shouldRequest.again(true)
            }
            .request()
    }
    /**
     * 显示文件上传选择器
     */
    private fun showFileChooser() {
        val _Args = FxFileDialogArgs()
        _Args.DialogTitle = "选择文件"
        _Args.DialogType = FxHelp.DLG_OPEN_FILE
        //需要展示的文件的后缀名
        _Args.Filter = Constants.SELECT_FILE_SUFFIX
        _Args.IsMultiSelect = false
        FxHelp.changeActivity(this, FileBrowseActivity::class.java, _Args, true, 0)
    }
    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    @RequiresApi(Build.VERSION_CODES.N)
    public fun showDatePickerDialog(activity:Activity, themeResId:Int, tv: TextView, calendar:Calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        var datePickerDialog=DatePickerDialog(activity,themeResId, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            tv.text = "$year - ${month+1}- $dayOfMonth"
        } , calendar.get(Calendar.YEAR)
            ,calendar.get(Calendar.MONTH)
            ,calendar.get(Calendar.DAY_OF_MONTH))
       /* var datePicker=datePickerDialog.datePicker
        datePicker.setBackgroundResource(R.color.white)*/
        datePickerDialog.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (!isImage){
            if (requestCode === 1 && resultCode === 1) { //上传文件的回调
                if (data != null) {
                    val args = data.getSerializableExtra(FxHelp.ACTIVITY_ARG_PARAM_NAME) as FxFileDialogArgs?
                    if (args != null) {
                        if (args.DialogResult == FxHelp.DLGRES_OK) {
                            getPresenter().upload(File(args.FileName))
                        }
                    }
                }
            }
        }else{
            imageChooseHelper!!.onActivityResult(requestCode,resultCode,data)
        }
    }

    override fun getloadFile(upLoadFile: UpLoadFile) {
        if (!isImage){
            fileString=upLoadFile.url
        }else{
            image=upLoadFile.url
        }

    }

    override fun setProject() {
        finish()
    }

    override fun onClick(v: View?) {
        if (selectDialog==null){
            selectDialog=SelectDialog(this)
        }
        selectDialog?.setOnSureListener( object : SelectDialog.OnSureListener{
            override fun onSure(
                filid: ArrayList<Select.ChildrenBean>,
                fanance: ArrayList<Select.ChildrenBean>,
                address: ArrayList<Select.ChildrenBean>
            ) {
                var field=""
                var fananceStr=""
                var addressStr=""
                for (i in 0 until filid.size){
                    filids!!.add(filid[i].id)
                    if (i==0){
                        field+=filid[i].attr_name
                    }else{
                        field+="、${filid[i].attr_name}"
                    }
                }
                for (i in 0 until fanance.size){
                    fanances!!.add(fanance[i].id)
                    if (i==0){
                        fananceStr+=fanance[i].attr_name
                    }else{
                        fananceStr+="、${fanance[i].attr_name}"
                    }
                }

                for (i in 0 until address.size){
                    addresss!!.add(filid[i].id)
                    if (i==0){
                        addressStr+=address[i].attr_name
                    }else{
                        addressStr+="、${address[i].attr_name}"
                    }
                }

                tvFleid.text=field
                tvFinance.text=fananceStr
                tvAddress.text=addressStr
            }

        })
        selectDialog?.show()
    }

}