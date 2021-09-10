package com.sarftec.lifequotesandcaptions.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sarftec.lifequotesandcaptions.R
import com.sarftec.lifequotesandcaptions.data.DatabaseSetup
import com.sarftec.lifequotesandcaptions.presentation.dialog.PreparationDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : BaseActivity() {

    @Inject
    lateinit var databaseSetup: DatabaseSetup

    private val preparationDialog by lazy {
        PreparationDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            if (!databaseSetup.isPrepared()) {
                preparationDialog.show()
                databaseSetup.prepare()
            }

            imageStore.loadImages()

            startActivity(Intent(this@StartActivity, SplashActivity::class.java))
            finish()
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim)
        }
    }
}