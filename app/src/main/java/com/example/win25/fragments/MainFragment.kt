package com.example.win25.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.win25.AddBetFragment
import com.example.win25.R
import com.example.win25.WriteBankFragment
import com.example.win25.adapter.BetAdapter
import com.example.win25.model.BetModel
import com.example.win25.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.recycler_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment(), BetAdapter.OnItemClickListener {

    private lateinit var adapter: BetAdapter
    private var bettingList = ArrayList<BetModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        if (getPrefBank()!!.isEmpty()) {
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

    private fun setPrefBank(sum: Double) {
        val sharedPrefCapital = requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("bank", sum.toString())
        }
    }

    private fun init() {
        bettingList.clear()
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.initDatabase()
        viewModel.getAllBet().observe(viewLifecycleOwner) { listBet ->
            bettingList.addAll(listBet)
            recycler_view.layoutManager = LinearLayoutManager(requireContext())
            adapter = BetAdapter(this, bettingList)
            recycler_view.adapter = adapter
        }

    }

    override fun onClick(
        position: Int,
        betModel: BetModel,
        s: String,
        textViewStatus: TextView,
        textViewWinSum: TextView
    ) {
        val odd = bettingList[position].betOdd
        val amount = bettingList[position].betAmount
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.initDatabase()
        if (s == "win") {
            val result = odd.toDouble() * amount.toDouble()
            setPrefBank(getPrefBank()!!.toDouble() + result)
            textView_capital.text = getPrefBank()
            textView_betAmount.text = amount
            textViewWinSum.text = result.toString()
            textViewStatus.text = s
            viewModel.updateBet("win", position) {
                CoroutineScope(Dispatchers.Main).launch {
                    bettingList.clear()
                    viewModel.getAllBet().observe(viewLifecycleOwner) { listBet ->
                        bettingList.addAll(listBet)
                        recycler_view.layoutManager = LinearLayoutManager(requireContext())
                        adapter = BetAdapter(this@MainFragment, bettingList)
                        recycler_view.adapter = adapter
                    }
                }
            }
        } else if(s=="loss"){
            val amount = bettingList[position].betAmount.toDouble()
            textViewStatus.text = s
            textViewWinSum.text = (amount * -1).toString()
            viewModel.updateBet("loss", position) {
                CoroutineScope(Dispatchers.Main).launch {
                    bettingList.clear()
                    viewModel.getAllBet().observe(viewLifecycleOwner) { listBet ->
                        Log.i("STATUS", listBet.toString())
                        bettingList.addAll(listBet)
                        recycler_view.layoutManager = LinearLayoutManager(requireContext())
                        adapter = BetAdapter(this@MainFragment, bettingList)
                        recycler_view.adapter = adapter
                    }
                }
            }
        } else if(s=="delete"){
            Log.i("DELETE", bettingList.toString())
            if (bettingList[position].betStatus=="loss"){
                var capWinLoss = getPrefBank()!!.toDouble()
                capWinLoss += bettingList[position].betAmount.toDouble()
                setPrefBank(capWinLoss)
                textView_capital.text = "$capWinLoss"
                viewModel.deleteBet(bettingList[position]){}
                bettingList.clear()
                viewModel.getAllBet().observe(viewLifecycleOwner) { listBet ->
                    bettingList.addAll(listBet)
                    Log.i("DELETE", bettingList.toString())
                    recycler_view.layoutManager = LinearLayoutManager(requireContext())
                    adapter = BetAdapter(this, bettingList)
                    recycler_view.adapter = adapter
                }

            } else if(bettingList[position].betStatus=="win"){
                viewModel.deleteBet(bettingList[position]){
                    CoroutineScope(Dispatchers.Main).launch {
                        bettingList.clear()
                        viewModel.getAllBet().observe(viewLifecycleOwner) { listBet ->
                            if (listBet.isNotEmpty()){
                                var capWinDel = getPrefBank()!!.toDouble()
                                capWinDel -= listBet[position].betAmount.toDouble()
                                Toast.makeText(
                                    requireContext(),
                                    capWinDel.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                setPrefBank(capWinDel)
                                textViewWinSum.text = "$capWinDel"
                                bettingList.addAll(listBet)
                                recycler_view.layoutManager = LinearLayoutManager(requireContext())
                                adapter = BetAdapter(this@MainFragment, bettingList)
                                recycler_view.adapter = adapter
                            }
                        }
                    }
                }
            } else {
                var capWinLoss = getPrefBank()!!.toDouble()
                capWinLoss += bettingList[position].betAmount.toDouble()
                setPrefBank(capWinLoss)
                textView_capital.text = "$capWinLoss"
                viewModel.deleteBet(bettingList[position]){}
                bettingList.clear()
                viewModel.getAllBet().observe(viewLifecycleOwner) { listBet ->
                    bettingList.addAll(listBet)
                    recycler_view.layoutManager = LinearLayoutManager(requireContext())
                    adapter = BetAdapter(this, bettingList)
                    recycler_view.adapter = adapter
                }

            }
        }
    }
}