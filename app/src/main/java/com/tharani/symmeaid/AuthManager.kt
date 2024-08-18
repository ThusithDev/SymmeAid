package com.tharani.symmeaid

object AuthManager {
    private var sessionToken: String? = null
    private var validate: Int? = null
    private var profilePictureUrl: String? = null
    private var id: Int? = null

    fun isLoggedIn(): Boolean {
        return sessionToken != null
    }

    fun setSessionToken(token: String) {
        sessionToken = token
    }

    fun getSessionToken(): String? {
        return sessionToken
    }

    fun setValidate(validate: Int) {
        this.validate = validate
    }

    fun getValidate(): Int? {
        return validate
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int? {
        return id
    }

    fun clearSession() {
        sessionToken = null
        // Add any other cleanup tasks if needed
    }

    fun setProfilePictureUrl(url: String) {
        profilePictureUrl = url
    }

    fun getProfilePictureUrl(param: (Any) -> Unit): String? {
        return profilePictureUrl
    }
}