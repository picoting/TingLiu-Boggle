package com.example.boggle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment

class GameBoard: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.gameboard, container, false)

        populateGrid()
    }

    private fun populateGrid() {
        val letterGrid: GridLayout = view?.findViewById(R.id.letterGrid) ?: return
        letterGrid.removeAllViews() // clear the grid

        val letters = generateRandomLetters()

        for (i in letters.indices) {
            val button = Button(context).apply {
                text = letters[i].toString()
                layoutParams = GridLayout.LayoutParams().apply {
                    //gahhhhhhh
                }
                setOnClickListener {
                    //handle gameplay
                }
            }
            letterGrid.addView(button)
        }
    }

    private fun generateRandomLetters(): List<Char> {
        val vowels = listOf('A', 'E', 'I', 'O', 'U')
        val consonants = ('A'..'Z').filterNot { it in vowels }
        return vowels
    }
}