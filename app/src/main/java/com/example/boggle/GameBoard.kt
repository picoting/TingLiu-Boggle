package com.example.boggle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.random.Random

private lateinit var buttons: Array<Array<Button?>>
private val currentWord = StringBuilder()
private val selectedButtons = mutableListOf<Button>()
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

        buttons = Array(4) { Array<Button?>(4) { null } }

        for (row in 0 until 4) {
            for (col in 0 until 4) {
                val button = Button(context).apply {
                    text = letters[row * 4 + col].toString()
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 200
                        height = 200
                        setMargins(5, 5, 5, 5)
                    }
                    setOnClickListener {
                        onButtonSelected(this)
                    }
                }
                letterGrid.addView(button)
                buttons[row][col] = button
            }
        }
    }

    private fun onButtonSelected(button: Button) {
        val currentWordTextView: TextView = view?.findViewById(R.id.currWord) ?: return
        currentWord.append(button.text.toString())
        currentWordTextView.text = currentWord
        selectedButtons.add(button)
        button.isEnabled = false // Optional: disable button to prevent re-selection
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