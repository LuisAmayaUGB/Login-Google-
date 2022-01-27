package com.c1ctech.googleloginexp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.c1ctech.googleloginexp.Utility.getGoogleSigninClient
import com.c1ctech.googleloginexp.Utility.isUserSignedIn
import com.c1ctech.googleloginexp.databinding.ActivitySecondBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SecondActivity : AppCompatActivity() {

    private lateinit var activitySecondBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(activitySecondBinding.root)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            activitySecondBinding.pName.text = account.displayName
            activitySecondBinding.pEmail.text = account.email
        }


        activitySecondBinding.btnSignOut.setOnClickListener {
            signout()
        }
    }

    private fun signout() {

        if (isUserSignedIn(this)) {

            //clears the account, which is connected to the app
            getGoogleSigninClient(this@SecondActivity).signOut().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this@SecondActivity, " Signed out ", Toast.LENGTH_SHORT).show()
                    //opens login activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SecondActivity, " Error ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}