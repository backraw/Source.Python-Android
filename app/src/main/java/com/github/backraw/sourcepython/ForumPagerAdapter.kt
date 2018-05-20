package com.github.backraw.sourcepython


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.github.backraw.sourcepython.models.Forums


class ForumPagerAdapter(fragmentManager: FragmentManager,
                        private val fragments: ArrayList<Fragment> = ArrayList())
    : FragmentStatePagerAdapter(fragmentManager) {

    fun refreshFragments() {
        fragments.clear()

        for (forum in Forums) {
            fragments.add(ForumFragment.newInstance(forum))
        }
    }


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return fragments.indexOf(`object`)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return Forums[position].title
    }
}
