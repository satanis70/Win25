package com.example.win25

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_write_bank.*

class WriteBankFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_write_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_writeBank.setOnClickListener {
            if (editText_writeBank.text.isNotEmpty()){
                setPrefBank(editText_writeBank.text.toString())
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }
    }

    private fun setPrefBank(sum: String) {
        val sharedPrefCapital = requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("bank", sum)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }
}