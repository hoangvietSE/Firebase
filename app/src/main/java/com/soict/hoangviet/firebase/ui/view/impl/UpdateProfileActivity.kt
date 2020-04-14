package com.soict.hoangviet.firebase.ui.view.impl

import android.app.Dialog
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import beetech.com.carbooking.util.DateUtil
import com.soict.hoangviet.baseproject.extension.onAvoidDoubleClick
import com.soict.hoangviet.baseproject.extension.toast
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.UpdateProfileAdapter
import com.soict.hoangviet.firebase.common.BasePhotoActivity
import com.soict.hoangviet.firebase.data.network.request.UpdateProfileRequest
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.extension.loadImageUri
import com.soict.hoangviet.firebase.module.GlideApp
import com.soict.hoangviet.firebase.ui.interactor.impl.UpdateProfileInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.SplashPresenter
import com.soict.hoangviet.firebase.ui.presenter.UpdateProfilePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.UpdateProfilePresenterImpl
import com.soict.hoangviet.firebase.ui.view.UpdateProfileView
import com.soict.hoangviet.firebase.utils.DialogUtil
import com.soict.hoangviet.firebase.widget.DatePickerDialogWidget
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.dialog_update_profile_choose.*
import java.util.*
import javax.inject.Inject

class UpdateProfileActivity : BasePhotoActivity(), UpdateProfileView {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override val mLayoutRes: Int
        get() = R.layout.activity_update_profile

    @Inject
    lateinit var mPresenter: UpdateProfilePresenter

    private var mUpdateProfileAdapter: UpdateProfileAdapter? = null
    private var mDatePickerDialogWidget: DatePickerDialogWidget? = null

    override fun initView() {
        mPresenter.onAttach(this)
        initDatePicker()
        setListGender()
        getDataIntent()
    }


    private fun initDatePicker() {
        mDatePickerDialogWidget =
            DatePickerDialogWidget(this, object : DatePickerDialogWidget.DatePickerListener {
                override fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
                    val selectedCalendar: Calendar = Calendar.getInstance()
                    selectedCalendar.set(year, month - 1, dayOfMonth)
                    val checkCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
                    if (selectedCalendar.after(checkCalendar)) {
                        toast(resources.getString(R.string.update_profile_error_date))
                        return
                    }
                    tv_birthday.text =
                        "${DateUtil.getDateValue(dayOfMonth.toString())}/${DateUtil.getDateValue(
                            month.toString()
                        )}/" +
                                "${DateUtil.getDateValue(year.toString())}"
                }
            })
    }

    private fun setListGender() {
        mUpdateProfileAdapter = UpdateProfileAdapter(this)
        spinner_gender.adapter = mUpdateProfileAdapter
        spinner_gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mPresenter.setGender(position)
            }

        }
    }

    private fun getDataIntent() {
        val user: User = intent.getParcelableExtra(EXTRA_USER)
        edt_name.setText(user.fullname)
        tv_phone.text = user.phone
        tv_email.text = user.email
        tv_birthday.text = user.birthday
        user.gender?.let { spinner_gender.setSelection(it) }
    }

    override fun initListener() {
        rowBirthday.onAvoidDoubleClick {
            mDatePickerDialogWidget?.showDatePickerDialog()
        }
        imv_choose_image.onAvoidDoubleClick {
            val dialog = DialogUtil.customDialog(
                this,
                R.layout.dialog_update_profile_choose,
                true,
                object : DialogUtil.BaseCustomDialog {
                    override fun addDataToDialog(dialog: Dialog) {
                        GlideApp.with(this@UpdateProfileActivity)
                            .load(R.drawable.update_profile)
                            .into(dialog.imv_gif)
                    }
                }
            )
            dialog.show()
            dialog.btn_album.onAvoidDoubleClick {
                dialog.dismiss()
                openGallery()
            }
            dialog.btn_capture.onAvoidDoubleClick {
                dialog.dismiss()
                openCamera()
            }
        }
        btn_update.onAvoidDoubleClick {
            val mUpdateProfileRequest = UpdateProfileRequest()
            mUpdateProfileRequest.apply {
                email = tv_email.text.toString()
                phone = tv_phone.text.toString()
                fullname = edt_name.text.toString()
                birthday = tv_birthday.text.toString()
            }
            mPresenter.updateProfile(mUpdateProfileRequest)
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onTakeAbsolutePathImageSuccess(absoluteFilePathImage: String, uriImage: Uri) {
        imv_choose_image.loadImageUri(
            this,
            uriImage,
            R.drawable.ic_avatar,
            R.drawable.ic_avatar
        )
        mPresenter.setAvatar(uriImage)
    }

    override fun onTakeImageFileCaptureSuccess(cameraFilePath: String, uriImage: Uri) {
        imv_choose_image.loadImageUri(
            this,
            uriImage,
            R.drawable.ic_avatar,
            R.drawable.ic_avatar
        )
        mPresenter.setAvatar(uriImage)
    }

    override fun onFullNameEmpty() {
        toast(resources.getString(R.string.update_profile_name_empty))
    }

    override fun onBirthdayEmpty() {
        toast(resources.getString(R.string.update_profile_birthday_empty))
    }

    override fun onAvatarUploadError() {
        toast(resources.getString(R.string.update_profile_avatar_upload_error))
    }

    override fun uploadSuccess() {
        toast("Cập nhật thành công")
        finish()
    }

    override fun uploadError() {
        toast("Cập nhật thất bại. Vui lòng thử lại")
    }
}