package com.example.win25.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import com.example.win25.repository.BetRealisation
import com.example.win25.repository.BetRepository
import com.example.win25.room.BetDatabase

class MainViewModel(application: Application):AndroidViewModel(application) {
    lateinit var betRepository: BetRepository
    val con = betRepository.allBet
    val context = application
    fun initDatabase(){
        val daoBet = BetDatabase.getDatabase(context).betDao()
        betRepository = BetRealisation(daoBet)
    }
}