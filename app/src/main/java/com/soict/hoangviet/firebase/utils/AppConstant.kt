package com.soict.hoangviet.firebase.utils

object AppConstant {
    const val PREF_DEFAULT = "app_sharePreference"
    const val OFFLINE = 0
    const val ONLINE = 1
    const val UNSEND = -1
    const val UNSEEN = 0
    const val SEEN = 1

    object SharePreference {
        const val USER = "user"
        const val DEVICE_TOKEN = "deviceToken"
        const val FIRST_TIME_APP = "firstTimeApp"
        const val LANGUAGE = "language"
    }

    object DataBaseRef {
        const val CHATS = "chats"
        const val CHATS_LIST = "ChatsList"
        const val USERS = "Users"
    }

    object TypeMessage {
        const val TEXT = 0
        const val EMOJI = 1
        const val IMAGE = 2
        const val ALBUM = 3
    }

    object DialogFragmentTag {
        const val LANGUAGE = "language"
    }

    object Language {
        const val VIETNAMESE = "vi"
        const val ENGLISH = "en"
    }

}