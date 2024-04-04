package com.example.boggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivity : AppCompatActivity(), GameState.GameStateListener, GameBoard.GameBoardListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun newGame() {
        Log.d("MainActivity", "onNewGameClicked() called")
        val board = supportFragmentManager.findFragmentById(R.id.gameboard) as GameBoard?
        board?.newGame(board.requireView())
    }

    override fun submitPressed(score: Int) {
        val state = supportFragmentManager.findFragmentById(R.id.gamestate) as GameState?
        state?.updateScore(state.requireView(), score)
    }
}