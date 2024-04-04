package com.example.boggle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameState: Fragment() {

    private var scoreView: TextView? = null
    private var currScore: Int = 0
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

        val newGameButton: Button = view.findViewById(R.id.newGameButton)
        newGameButton.setOnClickListener { newGame() }
    }

    fun updateScore(requireView: View, score: Int) {
        currScore += score
        if (currScore < 0) {
            currScore = 0
        }
        scoreView?.text = currScore.toString()
    }

    private fun newGame() {
        currScore = 0
        scoreView?.text = currScore.toString()
        listener?.newGame()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GameState.GameStateListener) {
            listener = context
        }
    }
}