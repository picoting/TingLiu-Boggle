package com.example.boggle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import kotlin.random.Random

class GameBoard: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.gameboard, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateGrid()
    }

    fun regenerateBoard() { //for hitting new game
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

        val numVowel = Random.nextInt(3,6)
        val randomVowels = List(numVowel) { vowels.random() }

        val numCons = 16-numVowel
        val randomCons= List(numCons) { consonants.random() }

        return (randomVowels + randomCons).shuffled()
    }
}