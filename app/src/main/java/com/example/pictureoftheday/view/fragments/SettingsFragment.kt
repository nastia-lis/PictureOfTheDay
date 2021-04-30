package com.example.pictureoftheday.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.SettingsFragmentBinding
import kotlin.properties.Delegates

const val NAME_THEME: String = "NAME"
const val THEME_ID: String = "THEME ID"
const val APP_THEME: String = "APP THEME"
const val MAIN_THEME: Int = 0
const val SUNSHINE_THEME: Int = 1
const val SPACE_THEME: Int = 2

class SettingsFragment: Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private var settings: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSettings()
        binding.chipMain.apply {
            setOnClickListener {
                if(settings != MAIN_THEME) {
                    requireActivity().setTheme(R.style.Theme_PictureOfTheDay)
                    requireActivity().recreate()
                    setAppTheme(R.style.Theme_PictureOfTheDay)
                }
            }
        }
        binding.chipSunshine.apply {
            setOnClickListener {
                if (settings != SUNSHINE_THEME) {
                    requireActivity().setTheme(R.style.Theme_Sunshine)
                    requireActivity().recreate()
                    setAppTheme(R.style.Theme_Sunshine)
                }
            }
        }
        binding.chipSpace.apply {
            setOnClickListener {
                if (settings != SPACE_THEME) {
                    requireActivity().setTheme(R.style.Theme_Space)
                    requireActivity().recreate()
                    setAppTheme(R.style.Theme_Space)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sharedPreferences = requireActivity().getSharedPreferences(NAME_THEME, Context.MODE_PRIVATE)
                .getInt(THEME_ID, R.style.Theme_PictureOfTheDay)
        val inflaterThemes = LayoutInflater.from(ContextThemeWrapper(context, sharedPreferences))
        _binding = SettingsFragmentBinding.inflate(inflaterThemes, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAppTheme(id: Int) {
        activity?.let {
            with(it.getSharedPreferences(NAME_THEME, Context.MODE_PRIVATE).edit()) {
                putInt(THEME_ID, id).apply()
            }
        }
    }

    private fun setSettings() {
        activity?.let {
            settings = it.getSharedPreferences(NAME_THEME, Context.MODE_PRIVATE)
                    .getInt(APP_THEME, MAIN_THEME)
            when (settings) {
                SUNSHINE_THEME -> {
                    binding.chipSunshine.isChecked = true
                }
                SPACE_THEME -> {
                    binding.chipSpace.isChecked = true
                }
                else -> {
                    binding.chipMain.isChecked = true
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}