package pe.com.dar.daniel.currencyconverter

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import pe.com.dar.daniel.currencyconverter.databinding.ActivityMainBinding
import pe.com.dar.daniel.currencyconverter.presentation.CurrencyEvent
import pe.com.dar.daniel.currencyconverter.presentation.CurrencyViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val vm: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm.showSymbols()

        binding.btnConvert.setOnClickListener {
            vm.convert(
                binding.etAmount.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spTo.selectedItem.toString()
            )
        }

        lifecycleScope.launchWhenStarted {
            vm.conversion.collectLatest { event ->
                when (event) {
                    is CurrencyEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.isVisible = true
                        binding.tvResult.text = event.resultText
                    }
                    is CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.isVisible = true
                        binding.tvResult.text = event.errorText

                    }
                    is CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }




        lifecycleScope.launchWhenStarted {
            vm.symbol.collectLatest { state ->
                if (state.listSymbol.isNotEmpty()) {
                    binding.spTo.adapter = ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_list_item_1,
                        state.listSymbol.map { it.symbol }
                    )
                    binding.spFromCurrency.adapter = ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_list_item_1,
                        state.listSymbol.map { it.symbol }
                    )
                }
            }
        }
    }

}
