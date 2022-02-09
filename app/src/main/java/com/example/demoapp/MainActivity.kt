package com.example.demoapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.demoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        initViews()
        initListeners()
        observer()
    }

    private fun initViews() {
        binding.apply {
            vm = viewModel

            val color = ContextCompat.getColor(this@MainActivity, R.color.violet)
            subTitleTv.makeLink(
                pair = Pair(
                    "Learn more",
                    makeClickableSpanWithoutUnderline(color) {
                        toast("Learn more clicked!")
                    })
            )

            /**
             * GenericTextWatcher here works only for moving to next EditText when a number is entered
             * first parameter is the current EditText and second parameter is next EditText
             */
            dateEt.addTextChangedListener(GenericTextWatcher(currentView = dateEt, nextView = monthEt))
            monthEt.addTextChangedListener(GenericTextWatcher(currentView = monthEt, nextView = yearEt))
            yearEt.addTextChangedListener(GenericTextWatcher(currentView = yearEt, nextView = null))

            /**
             *  GenericKeyEvent here works for deleting the element and to switch back to previous EditText
             *  first parameter is the current EditText and second parameter is previous EditText
             */
            dateEt.setOnKeyListener(GenericKeyEvent(currentView = dateEt, previousView = null))
            monthEt.setOnKeyListener(GenericKeyEvent(currentView = monthEt, previousView = dateEt))
            yearEt.setOnKeyListener(GenericKeyEvent(currentView = yearEt, previousView = monthEt))
        }
    }

    private fun initListeners() {
        binding.apply {
            nextBtn.setOnClickListener {
                toast(getString(R.string.success_message))
                finish()
            }
            noPanTv.setOnClickListener {
                finish()
            }
        }
    }

    private fun observer() {
        viewModel.apply {
            pan.observe(this@MainActivity) {
                if (it.validatePanCard()) {
                    binding.dateEt.apply {
                        requestFocus()
                        hideKeyboard(binding.dateEt.windowToken)
                    }
                }
                viewModel.validateData()
            }
            year.observe(this@MainActivity) {
                // dob format: dd/mm/yyyy
                viewModel.dob.value =
                    "${binding.dateEt.text}/${binding.monthEt.text}/${binding.yearEt.text}"
                viewModel.validateData()
            }
            buttonState.observe(this@MainActivity) {
                binding.nextBtn.isEnabled = it
            }
        }
    }
}