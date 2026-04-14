package com.rtw.pro.foundation.data.auth

import com.rtw.pro.foundation.domain.auth.UserProfileGateway
import com.rtw.pro.foundation.domain.model.UserProfile

interface FirestoreProfileStore {
    fun upsertUserProfile(profile: UserProfile)
}

class FirestoreUserProfileGateway(
    private val store: FirestoreProfileStore
) : UserProfileGateway {
    override fun upsert(profile: UserProfile) {
        store.upsertUserProfile(profile)
    }
}
