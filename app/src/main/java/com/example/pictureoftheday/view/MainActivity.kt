package com.example.pictureoftheday.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pictureoftheday.R
import com.example.pictureoftheday.ViewPagerAdapter
import com.example.pictureoftheday.databinding.MainActivityBinding
import com.example.pictureoftheday.view.fragments.NAME_THEME
import com.example.pictureoftheday.view.fragments.PictureOfTheDayFragment
import com.example.pictureoftheday.view.fragments.THEME_ID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences(NAME_THEME, Context.MODE_PRIVATE)
                .getInt(THEME_ID, R.style.Theme_PictureOfTheDay)
        setTheme(sharedPreferences)
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                    .commitNow()
        }
    }
}