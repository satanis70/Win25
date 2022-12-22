package com.example.win25.repository

import androidx.lifecycle.LiveData
import com.example.win25.model.BetModel

interface BetRepository {
    val allBet: LiveData<List<BetModel>>
    suspend fun insertBet(betModel: BetModel, onSuccess:()-> Unit)
    suspend fun deleteBet(betModel: BetModel, onSuccess:()-> Unit)
    suspend fun updateBet(status: String, position: Int, onSuccess:()-> Unit)
}