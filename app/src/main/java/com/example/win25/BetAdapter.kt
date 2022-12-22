package com.example.win25

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.win25.model.BetModel
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class BetAdapter:RecyclerView.Adapter<BetAdapter.BetViewHolder>() {

    var listBet = emptyList<BetModel>()

    class BetViewHolder(view: View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return BetViewHolder(view)

    }

    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
        holder.itemView.textView_nameBet.text = listBet[position].betName
    }

    override fun getItemCount(): Int {
        return listBet.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<BetModel>){
        listBet = list
        notifyDataSetChanged()
    }

}