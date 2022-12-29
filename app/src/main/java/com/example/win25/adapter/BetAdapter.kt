package com.example.win25.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.win25.R
import com.example.win25.model.BetModel
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class BetAdapter(val onItemClickListener: OnItemClickListener, val listBet: List<BetModel>):RecyclerView.Adapter<BetAdapter.BetViewHolder>() {

    class BetViewHolder(view: View):RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return BetViewHolder(view)
    }

    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
        holder.itemView.textView_nameBet.text = listBet[position].betName
        val nameBet = holder.itemView.textView_nameBet
        val statusBet = holder.itemView.textView_status
        val amountBet = holder.itemView.textView_betAmount
        val winAmountBet = holder.itemView.textView_winAmount
        val btnWin = holder.itemView.btn_win
        val btnLoss = holder.itemView.btn_loss
        val btnDelete = holder.itemView.btn_delete
        val layout = holder.itemView.layout_item
        nameBet.text = listBet[holder.adapterPosition].betName
        statusBet.text = listBet[holder.adapterPosition].betStatus
        amountBet.text = listBet[holder.adapterPosition].betAmount
        if (listBet[holder.adapterPosition].betStatus=="win"){
            winAmountBet.text = (listBet[holder.adapterPosition].betAmount.toDouble()*listBet[holder.adapterPosition].betOdd.toDouble()).toString()
            layout.background = holder.itemView.resources.getDrawable(R.drawable.round_corner_layout_win)
            btnLoss.visibility = View.INVISIBLE
            btnWin.visibility = View.INVISIBLE
        } else if (listBet[holder.adapterPosition].betStatus=="loss") {
            winAmountBet.text = (listBet[holder.adapterPosition].betAmount.toDouble()*-1).toString()
            layout.background = holder.itemView.resources.getDrawable(R.drawable.round_corner_layout_loss)
            btnLoss.visibility = View.INVISIBLE
            btnWin.visibility = View.INVISIBLE
        }
        btnWin.setOnClickListener{
            layout.background = holder.itemView.resources.getDrawable(R.drawable.round_corner_layout_win)
            onItemClickListener.onClick(holder.adapterPosition, BetModel(
                holder.adapterPosition,
                holder.adapterPosition,
                listBet[holder.adapterPosition].betName,
                listBet[holder.adapterPosition].betOdd,
                listBet[holder.adapterPosition].betAmount,
                listBet[holder.adapterPosition].betStatus,
                listBet[holder.adapterPosition].bankCapital),
                "win",
                holder.itemView.textView_status,
                winAmountBet)
            btnLoss.visibility = View.INVISIBLE
            btnWin.visibility = View.INVISIBLE
        }
        btnLoss.setOnClickListener{
            layout.background = holder.itemView.resources.getDrawable(R.drawable.round_corner_layout_loss)
            onItemClickListener.onClick(holder.adapterPosition, BetModel(
                holder.adapterPosition,
                holder.adapterPosition,
                listBet[holder.adapterPosition].betName,
                listBet[holder.adapterPosition].betOdd,
                listBet[holder.adapterPosition].betAmount,
                listBet[holder.adapterPosition].betStatus,
                listBet[holder.adapterPosition].bankCapital),
                "loss",
                holder.itemView.textView_status,
                winAmountBet)
            btnLoss.visibility = View.INVISIBLE
            btnWin.visibility = View.INVISIBLE
        }
        btnDelete.setOnClickListener {
            onItemClickListener.onClick(holder.adapterPosition, BetModel(
                holder.adapterPosition,
                holder.adapterPosition,
                listBet[holder.adapterPosition].betName,
                listBet[holder.adapterPosition].betOdd,
                listBet[holder.adapterPosition].betAmount,
                listBet[holder.adapterPosition].betStatus,
                listBet[holder.adapterPosition].bankCapital),
                "delete",
                holder.itemView.textView_status,
                winAmountBet)
        }
    }

    override fun getItemCount(): Int {
        return listBet.size
    }
    interface OnItemClickListener {
        fun onClick(
            position: Int,
            betModel: BetModel,
            s: String,
            textViewStatus: TextView,
            textViewWinSum: TextView
        ) {}
    }
}