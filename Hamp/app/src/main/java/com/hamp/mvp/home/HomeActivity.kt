package com.hamp.mvp.home

import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.FADE)
class HomeActivity : BaseActivity() {

//    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button2.onClick {
            //            auth.signOut()
        }

//        auth.addAuthStateListener { firebaseAuth ->
//            val user = firebaseAuth.currentUser
//            if (user == null) {
//                startActivity(Intent(this, StartActivity::class.java))
//                finish()
//            }
//        }
    }
}
