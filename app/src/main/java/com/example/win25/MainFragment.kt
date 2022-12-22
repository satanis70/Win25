package com.example.win25

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (getPrefBank()!!.isEmpty()){
            val showDialog = WriteBankFragment()
            showDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
        }
        textView_capital.text = getPrefBank()
        floatingActionButton.setOnClickListener {
            val showDialog = AddBetFragment()
            showDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
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