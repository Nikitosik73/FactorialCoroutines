package ru.paramonov.factorialcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import ru.paramonov.factorialcoroutines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeViewModel()
        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.edTextNumber.text.toString())
        }
    }

    private fun observeViewModel() {

        viewModel.error.observe(this) { error ->
            if(error) {
                Toast.makeText(
                    this,
                    "Вы не ввели никое число!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.progress.observe(this) { progress ->
            if(progress) {
                binding.progressCircular.visibility = View.VISIBLE
                binding.buttonCalculate.isEnabled = false
            } else {
                binding.progressCircular.visibility = View.GONE
                binding.buttonCalculate.isEnabled = true
            }
        }
        viewModel.factorial.observe(this) { result ->
            binding.textViewResult.text = result
        }
    }
}