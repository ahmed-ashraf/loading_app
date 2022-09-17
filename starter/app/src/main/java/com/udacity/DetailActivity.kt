package com.udacity

import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.udacity.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var activityDetailBinding: ActivityDetailBinding

    companion object {
        const val EXTRA_STATUS = "status"
        const val EXTRA_FILENAME = "fileName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setSupportActionBar(activityDetailBinding.toolbar)


        notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.cancelAll()
        activityDetailBinding.contentDetail.fileName.text = intent.getStringExtra(EXTRA_FILENAME)
        activityDetailBinding.contentDetail.status.text = intent.getStringExtra(EXTRA_STATUS)


        activityDetailBinding.contentDetail.okButton.setOnClickListener{
            onBackPressed()
        }

    }

}
