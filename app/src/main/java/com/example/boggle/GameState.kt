package com.example.boggle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameState: Fragment() {

    private var scoreView: TextView? = null
    interface GameStateListener {
        fun newGame()
    }
    private var listener: GameState.GameStateListener? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.gamestate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        scoreView = view.findViewById(R.id.scoreValue)
    }

    fun updateScore(requireView: View, score: Int) {
        scoreView?.text = score.toString()
    }
}