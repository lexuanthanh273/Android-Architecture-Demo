package com.architecture.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeActivityAdapter(
    private val fragments: MutableList<Fragment>,
    fm: Fragment
) : FragmentStateAdapter(fm) {

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

}
