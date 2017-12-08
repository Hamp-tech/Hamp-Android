package com.hamp.firebase

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FirebaseInstanceIDService : FirebaseInstanceIdService() {

    private val tag = "FirebaseIIDService"

    override fun onTokenRefresh() {
        super.onTokenRefresh()

        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(tag, "Refreshed token: " + refreshedToken)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendTokenToServer(refreshedToken)
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
//    private fun sendTokenToServer(token: String) {
//        api.saveFirebaseToken(prefs.userID, token)
//                .subscribeOn(Schedulers.io())
//                .subscribeBy(
//                        onNext = {
//                            Log.d("FirebaseToken", "Successfully saved")
//                        },
//                        onError = {
//                            Log.d("FirebaseToken", "Error to saved: " + it.message)
//                        }
//                )
//    }
}