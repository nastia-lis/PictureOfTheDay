package com.example.pictureoftheday

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pictureoftheday.view.fragments.DayBeforeYesterdayFragment
import com.example.pictureoftheday.view.fragments.PictureOfTheDayFragment
import com.example.pictureoftheday.view.fragments.TodayFragment
import com.example.pictureoftheday.view.fragments.YesterdayFragment

private const val TODAY = 0
private const val YESTERDAY = 1
private const val DAY_BEFORE_YESTERDAY = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(TodayFragment(), YesterdayFragment(), DayBeforeYesterdayFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> fragments[TODAY]
            1 -> fragments[YESTERDAY]
            2 -> fragments[DAY_BEFORE_YESTERDAY]
            else -> fragments[TODAY]
        }
    }

}