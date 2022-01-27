package com.c1ctech.googleloginexp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c1ctech.googleloginexp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.c1ctech.googleloginexp.Utility.getGoogleSigninClient
import com.c1ctech.googleloginexp.Utility.isUserSignedIn

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    lateinit var mGoogleSignInClient: GoogleSignInClient

    lateinit var startActivityForResult: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mGoogleSignInClient = getGoogleSigninClient(this@MainActivity)

        activityMainBinding.signInButton.setSize(SignInButton.SIZE_WIDE)

        activityMainBinding.signInButton.setOnClickListener {
            GoogleSignIn()
        }

        startActivityForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback<ActivityResult>()
                {

                    if (it.resultCode == Activity.RESULT_OK) {
                        handleSignData(it.data)
                    }
                })

    }

    private fun GoogleSignIn() {

        if (!isUserSignedIn(this)) {

            //creating a sign-in intent with the getSignInIntent method,
                // and starting the intent by calling startActivityForResult.
            // Starting the intent prompts the user to select a Google account to sign in with.

            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult.launch(signInIntent)


        } else {
            Toast.makeText(this, "User already signed-in", Toast.LENGTH_SHORT).show()
        }

    }

    private fun handleSignData(data: Intent?) {

        //After the user signs in, you can get a GoogleSignInAccount object for the user,
        //The GoogleSignInAccount object contains information about the signed-in user.
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener {
                "isSuccessful ${it.isSuccessful}".print()
                if (it.isSuccessful) {
                    // user successfully logged-in
                    "account ${it.result?.account}".print()
                    "displayName ${it.result?.displayName}".print()
                    "Email ${it.result?.email}".print()

                    // start next activity
                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent)

                } else {
                    // authentication failed
                    "exception ${it.exception}".print()
                }
            }

    }

    companion object {
        const val TAG_MAINACTIVITY = "TAG_MAINACTIVITY"
    }
}

fun Any.print() {
    Log.v(MainActivity.TAG_MAINACTIVITY, " $this")
}



