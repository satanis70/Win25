package com.example.win25.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.win25.betRepository
import com.example.win25.model.BetModel
import com.example.win25.repository.BetRealisation
import com.example.win25.repository.BetRepository
import com.example.win25.room.BetDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBetViewModel(application: Application):AndroidViewModel(application) {
    val daoBet = BetDatabase.getDatabase(application).betDao()
    fun initDatabase(){
        betRepository = BetRealisation(daoBet)
    }
    fun insertBet(betModel: BetModel, onSuccess:()->Unit) =
        viewModelScope.launch(Dispatchers.IO){
            betRepository.insertBet(betModel){
                onSuccess()
            }
        }
}