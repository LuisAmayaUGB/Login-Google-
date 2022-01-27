package com.c1ctech.googleloginexp

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object Utility {

    // Check for existing Google Sign In account, if the user is already signed in
    // the GoogleSignInAccount will be non-null.
    fun isUserSignedIn(context: Context): Boolean {

        val account = GoogleSignIn.getLastSignedInAccount(context)
        return account != null

    }

    fun getGoogleSigninClient(activity: Activity): GoogleSignInClient {

         // Configure sign-in to request the user's ID, email address, and basic
         // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

         // Build a GoogleSignInClient with the options specified by gso.
        return GoogleSignIn.getClient(activity, gso)
    }
}