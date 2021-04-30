package com.example.pictureoftheday.view.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.YesterdayFragmentBinding
import com.example.pictureoftheday.server.PictureOfTheDayData
import com.example.pictureoftheday.viewmodel.PictureOfTheDayViewModel
import kotlinx.android.synthetic.main.bottom_sheet.*
import java.text.SimpleDateFormat
import java.util.*

class YesterdayFragment: Fragment() {

    private var _binding: YesterdayFragmentBinding? = null
    private val binding get() = _binding!!

    private val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    companion object {
        fun newInstance() = YesterdayFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = YesterdayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getData(date.format(showDay())).observe(viewLifecycleOwner, { renderData(it) })
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when(data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    toast("Empty")
                } else {
                    binding.imageView.load(url) {
                        lifecycle(this@YesterdayFragment)
                        error(R.drawable.video)
                        placeholder(R.drawable.video)
                    }
                    binding.titleMain.text = serverResponseData.title
                }
            }
            is PictureOfTheDayData.Loading -> {

            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0 , 250)
            show()
        }
    }

    private fun showDay() : Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        return calendar.time
    }
}