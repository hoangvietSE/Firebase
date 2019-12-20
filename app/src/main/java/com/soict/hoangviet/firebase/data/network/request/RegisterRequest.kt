package com.soict.hoangviet.firebase.data.network.request

import android.os.Parcel
import android.os.Parcelable

class RegisterRequest() : Parcelable {
    var fullName: String = ""
    var email: String = ""
    var phoneNumber: String = ""
    var password: String = ""
    var birthday: String = ""
    var gender: Int = 0

    constructor(parcel: Parcel) : this() {
        fullName = parcel.readString()
        email = parcel.readString()
        phoneNumber = parcel.readString()
        password = parcel.readString()
        birthday = parcel.readString()
        gender = parcel.readInt()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(fullName)
        dest?.writeString(email)
        dest?.writeString(phoneNumber)
        dest?.writeString(password)
        dest?.writeString(birthday)
        dest?.writeInt(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegisterRequest> {
        override fun createFromParcel(parcel: Parcel): RegisterRequest {
            return RegisterRequest(parcel)
        }

        override fun newArray(size: Int): Array<RegisterRequest?> {
            return arrayOfNulls(size)
        }
    }
}