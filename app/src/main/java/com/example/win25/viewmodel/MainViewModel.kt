package com.example.win25.viewmodel

import android.app.Application
import android.icu.text.Transliterator.Position
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.example.win25.betRepository
import com.example.win25.model.BetModel
import com.example.win25.repository.BetRealisation
import com.example.win25.repository.BetRepository
import com.example.win25.room.BetDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val context = application
    val daoBet = BetDatabase.getDatabase(context).betDao()
    fun initDatabase() {
        betRepository = BetRealisation(daoBet)
    }

    fun getAllBet(): LiveData<List<BetModel>> {
        return betRepository.allBet
    }

    fun updateBet(status: String, position: Int, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            betRepository.updateBet(status, position) {
                onSuccess()
            }
        }

    fun deleteBet(betModel: BetModel, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            betRepository.deleteBet(betModel) {
                onSuccess()
            }
        }
    }
}