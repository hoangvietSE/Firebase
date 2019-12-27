package com.soict.hoangviet.firebase.data.network.response

import android.os.Parcel
import android.os.Parcelable

class User() : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    var id: String = ""
    var fullname: String = ""
    var email: String = ""
    var phone: String = ""
    var gender: Int = 0
    var birthday: String = ""
    var avatar: String = ""
    var status: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        fullname = parcel.readString()
        email = parcel.readString()
        phone = parcel.readString()
        gender = parcel.readInt()
        birthday = parcel.readString()
        avatar = parcel.readString()
        status = parcel.readInt()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(fullname)
        dest?.writeString(email)
        dest?.writeString(phone)
        dest?.writeInt(gender)
        dest?.writeString(birthday)
        dest?.writeString(avatar)
        dest?.writeInt(status)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}