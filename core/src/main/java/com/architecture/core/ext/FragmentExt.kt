package com.architecture.core.ext

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun Fragment.showSnackbar(snackbarText: String, timeLength: Int) {
    activity?.let {
        Snackbar.make(
            it.findViewById<View>(android.R.id.content),
            snackbarText,
            timeLength
        ).show()
    }
}

/**
 * To add a fragment using `commitNow()`.
 */
inline fun <reified T : Fragment> FragmentActivity.addFragmentNow(id: Int) {
    val fragment = T::class.java
    supportFragmentManager.beginTransaction().add(id, fragment.newInstance()).commitNow()
}

/**
 * To replace a fragment using `commitNow()`.
 */
inline fun <reified T : Fragment> FragmentActivity.replaceFragmentNow(id: Int) {
    val fragment = T::class.java
    supportFragmentManager.beginTransaction().replace(id, fragment.newInstance()).commitNow()
}

/**
 * To add a fragment using `commit()`.
 */
inline fun <reified T : Fragment> FragmentActivity.addFragment(id: Int) {
    val fragment = T::class.java
    supportFragmentManager.beginTransaction().add(id, fragment.newInstance()).commit()
}

/**
 * To replace a fragment using `commit()`.
 */
inline fun <reified T : Fragment> FragmentActivity.replaceFragment(id: Int) {
    val fragment = T::class.java
    supportFragmentManager.beginTransaction().replace(id, fragment.newInstance()).commit()
}

fun FragmentActivity.replaceFragment(
    container: Int,
    fragment: Fragment,
    tag: String
) {
    val f = supportFragmentManager.findFragmentByTag(tag)
    if (f != null) {
        supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    with(supportFragmentManager.beginTransaction()) {
        replace(container, fragment, tag)
            .addToBackStack(tag)
        setReorderingAllowed(true)
        commitAllowingStateLoss()
    }
}



enum class TransitionType {
    SIBLING, DETAIL, MODAL
}

inline fun <reified T : Fragment> FragmentManager.handleReplace(
    containerId: Int,
    transitionType: TransitionType? = null,
    fragment: T
) {
    val tag: String = T::class.java.name
    beginTransaction().apply {
        transitionType?.let { setTransition(it) }
        replace(containerId, findFragmentByTag(tag) ?: fragment, tag)
        addToBackStack(tag)
        setReorderingAllowed(true)
        commitAllowingStateLoss()
    }
}

fun FragmentTransaction.setTransition(transitionType: TransitionType) {
//    setCustomAnimations(
//        when (transitionType) {
//            TransitionType.SIBLING -> R.anim.fade_in
//            TransitionType.DETAIL -> R.anim.slide_from_end
//            TransitionType.MODAL -> R.anim.slide_from_bottom
//        },
//        R.anim.fade_out,
//        R.anim.fade_in,
//        when (transitionType) {
//            TransitionType.SIBLING -> R.anim.fade_out
//            TransitionType.DETAIL -> R.anim.slide_to_end
//            TransitionType.MODAL -> R.anim.slide_to_bottom
//        }
//    )
}