package com.hamp.mvp.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.mvp.login.LoginActivity
import com.hamp.mvp.start.StartActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.FADE)
class HomeActivity : BaseActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button2.onClick {
            auth.signOut()
        }

        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(Intent(this, StartActivity::class.java))
                finish()
            }
        }
    }
}
