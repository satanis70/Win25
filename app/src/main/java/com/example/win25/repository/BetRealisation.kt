package com.example.win25.repository

import androidx.lifecycle.LiveData
import com.example.win25.model.BetModel
import com.example.win25.room.Dao

class BetRealisation(private val betDao: Dao):BetRepository {
    override val allBet: LiveData<List<BetModel>>
        get() = betDao.getAll()

    override suspend fun insertBet(betModel: BetModel, onSuccess: () -> Unit) {
        betDao.insert(betModel)
    }

    override suspend fun deleteBet(betModel: BetModel, onSuccess: () -> Unit) {
        betDao.delete(betModel)
        onSuccess()
    }

    override suspend fun updateBet(status: String, position: Int, onSuccess: () -> Unit) {
        betDao.update(status, position)
        onSuccess()
    }
}