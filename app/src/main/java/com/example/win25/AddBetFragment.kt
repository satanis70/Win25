package com.example.win25

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.win25.model.BetModel
import com.example.win25.viewmodel.AddBetViewModel
import com.example.win25.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_bet.*

class AddBetFragment : DialogFragment() {

    private var _listBet = ArrayList<BetModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_bet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this)[AddBetViewModel::class.java]
        val viewModelMain = ViewModelProvider(this)[MainViewModel::class.java]
        viewModelMain.initDatabase()
        viewModel.initDatabase()
        button_add.setOnClickListener {
            val name = editText_nameBet.text.toString()
            val odd = editText_Odd.text.toString()
            val amount = editText_amount.text.toString()
            Log.i("INFO", name+odd+amount)
            if (name.isNotEmpty()&&odd.isNotEmpty()&&amount.isNotEmpty()){
                viewModelMain.getAllBet().observe(viewLifecycleOwner) { listBet ->
                    _listBet.addAll(listBet)
                }
                viewModel.insertBet(BetModel(null,_listBet.size,name, odd, amount, "wait", getPrefBank()!!)){}
                setPrefBank((getPrefBank()!!.toDouble()-amount.toDouble()).toString())
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }

    }

    private fun getPrefBank(): String? {
        val sharedPreference = requireActivity().getSharedPreferences("bank", Context.MODE_PRIVATE)
        return sharedPreference.getString("bank", "")
    }

    private fun setPrefBank(sum: String) {
        val sharedPrefCapital = requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("bank", sum)
        }
    }
}