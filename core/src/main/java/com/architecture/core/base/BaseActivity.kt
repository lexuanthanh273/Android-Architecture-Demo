package com.architecture.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.architecture.core.R
import com.architecture.core.ext.isBackPressFinish
import com.architecture.core.ext.showShotToast

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getViewModel(): BaseSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (isDebug()) {
//            Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
//        }
//        setupStatusBar()
    }

    override fun onBackPressed() {
        if (getViewModel().isShowLoading) {
            return
        }
        val allFragment = supportFragmentManager.fragments
        var isCanBackPress = true
        if (allFragment.size > 0) {
            val currentFragment = allFragment[allFragment.size - 1]
            if (currentFragment is BaseFragment) {
                isCanBackPress = currentFragment.isCanBackPress()
            }
        }
        if (!isCanBackPress) return
        val backStackCnt = supportFragmentManager.backStackEntryCount
        if (backStackCnt > getMinBackstackCount()) {
            super.onBackPressed()
        } else {
            if (isBackPressFinish) {
                finish()
            } else {
                showShotToast(getString(R.string.all_back_again_to_exit))
            }
        }
    }

    open fun getMinBackstackCount() = 1

    private fun setupStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

}
