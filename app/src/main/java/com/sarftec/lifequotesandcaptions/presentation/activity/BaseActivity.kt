package com.sarftec.lifequotesandcaptions.presentation.activity

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.sarftec.lifequotesandcaptions.R
import com.sarftec.lifequotesandcaptions.manager.NetworkManager
import com.sarftec.lifequotesandcaptions.presentation.Dependency
import com.sarftec.lifequotesandcaptions.presentation.image.ImageStore
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var imageStore: ImageStore

    @Inject
    lateinit var networkManager: NetworkManager

    protected val dependency by lazy {
        Dependency(this, imageStore)
    }

    protected fun <T> navigateTo(
        klass: Class<T>,
        finish: Boolean = false,
        slideIn: Int = R.anim.slide_in_right,
        slideOut: Int = R.anim.slide_out_left,
        parcel: Parcelable? = null
    ) {
        val intent = Intent(this, klass).also {
            it.putExtra(ACTIVITY_PARCEL, parcel)
        }
        startActivity(intent)
        if (finish) finish()
        overridePendingTransition(slideIn, slideOut)
    }

    fun statusColor(color: Int) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = color
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object {
        const val ACTIVITY_PARCEL = "activity_parcel"
        val modifiedQuoteList = hashMapOf<Int, Boolean>()
    }
}