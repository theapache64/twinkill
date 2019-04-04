package com.theapache64.twinkill.ui.activities.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.theapache64.twinkill.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper

@SuppressLint("Registered")
open class BaseAppCompatActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        super.onBackPressed()
    }

    /**
     * Returns a confirm dialog with two button 'YES' and 'CANCEL'.
     */
    fun getConfirmDialog(@StringRes title: Int, @StringRes message: Int, onYes: () -> Unit): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.action_yes) { _: DialogInterface, _: Int ->
                onYes()
            }
            .setCancelable(false)
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }
}