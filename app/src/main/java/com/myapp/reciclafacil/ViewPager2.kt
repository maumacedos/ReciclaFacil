package com.myapp.reciclafacil

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RecyclableAdapter(activity: FragmentActivity, private val infoList: List<RecyclableInfo>) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = infoList.size

    override fun createFragment(position: Int): Fragment {
        val info = infoList[position]
        return RecyclableFragment.newInstance(info)
    }
}