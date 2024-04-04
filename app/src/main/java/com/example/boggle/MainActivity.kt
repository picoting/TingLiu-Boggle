package com.example.boggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), GameBoard.GameBoardActions {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun addPoints(points: Int) {
        //adding points
    }

    override fun removePoints(points: Int) {
        //removing points (down to 0)
    }

    override fun startNewGame() {
        //start a new game (refresh board)
    }
}