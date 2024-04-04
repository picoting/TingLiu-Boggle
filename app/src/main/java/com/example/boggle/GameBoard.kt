package com.example.boggle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.BufferedReader
import java.io.IOException
import java.util.Locale
import kotlin.math.abs
import kotlin.random.Random

class GameBoard: Fragment() {

    private lateinit var buttons: Array<Array<Button?>>

    private val currentWord = StringBuilder()
    private val selectedButtons = mutableListOf<Button>()

    private var currentWordTextView: TextView? = null

    private var lastRow: Int = -10
    private var lastCol: Int = -10

    private lateinit var validWords: Set<String>

    private val generatedWords = mutableSetOf<String>()

    private var score: Int = 0

    interface GameBoardListener {
        fun submitPressed(score: Int)
    }
    private var listener: GameBoard.GameBoardListener? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.gameboard, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentWordTextView = view.findViewById(R.id.currWord)
        val clearButton: Button = view.findViewById(R.id.clearButton)
        val submitButton: Button = view.findViewById(R.id.submitButton)

        validWords = loadWords(requireContext())

        clearButton.setOnClickListener { clearWord() }
        submitButton.setOnClickListener { submitWord() }
        populateGrid()
    }

    private fun submitWord() {
        //val score: Int

        if (isValid(currentWord.toString(), validWords)) {
            score = calculateScore(currentWord.toString())
            Toast.makeText(context, "Valid word! + $score", Toast.LENGTH_SHORT).show()
        } else {
            score = -10
            Toast.makeText(context, "Invalid word. -10", Toast.LENGTH_SHORT).show()
        }

        listener?.submitPressed(score)

        val currentWordTextView: TextView = view?.findViewById(R.id.currWord) ?: return

        currentWord.clear()
        selectedButtons.forEach { it.isEnabled = true }
        selectedButtons.clear()

        lastRow = -10
        lastCol = -10

        currentWordTextView.text = currentWord
    }

    private fun clearWord() {
        val currentWordTextView: TextView = view?.findViewById(R.id.currWord) ?: return

        currentWord.clear()
        selectedButtons.forEach { it.isEnabled = true }
        selectedButtons.clear()

        lastRow = -10
        lastCol = -10

        currentWordTextView.text = currentWord
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
                        onButtonSelected(this, row, col)
                    }
                }
                letterGrid.addView(button)
                buttons[row][col] = button
            }
        }
    }

    private fun onButtonSelected(button: Button, row: Int, col: Int) {
        if (!isAdjacent(row, col)) return //not adjacent

        val currentWordTextView: TextView = view?.findViewById(R.id.currWord) ?: return
        currentWord.append(button.text.toString())
        currentWordTextView.text = currentWord
        selectedButtons.add(button)
        button.isEnabled = false // Optional: disable button to prevent re-selection

        lastRow = row
        lastCol = col

        //Toast.makeText(context, "Selected Row: $row, Col: $col", Toast.LENGTH_SHORT).show()
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

    private fun isAdjacent(row: Int, col: Int): Boolean {
        if (lastRow == -10 || lastCol == -10) { //first letter
            return true
        }
        return abs(row - lastRow) <= 1 && abs(col - lastCol) <= 1
    }

    private fun loadWords(context: Context): Set<String> {
        val words = mutableSetOf<String>()
        try {
            context.assets.open("words.txt").bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    words.add(line.trim().uppercase(Locale.getDefault()))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace() // Handle exceptions
        }
        return words
    }

    private fun isValid(word: String, validWords: Set<String>): Boolean {
        return validWords.contains(word.trim()) &&
                validVowels(word.trim()) &&
                word.length >= 4 &&
                generatedWords.add(word)
    }

    private fun validVowels(word: String): Boolean {
        val vowels = "AEIOU"
        var vowelCount = 0

        for (char in word) {
            if (char in vowels) vowelCount++
            if (vowelCount >= 2) return true
        }

        return false
    }

    private fun calculateScore(word: String): Int {
        var score = 0
        val specialConsonants = setOf('S', 'Z', 'P', 'X', 'Q')
        var containsSpecialConsonant = false

        for (char in word) {
            when (char) {
                in "AEIOU" -> score += 5
                in specialConsonants -> {
                    score += 1
                    containsSpecialConsonant = true
                }
                else -> score += 1
            }
        }

        if (containsSpecialConsonant) {
            score *= 2
        }

        return score
    }

    fun newGame(requireView: View) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GameBoard.GameBoardListener) {
            listener = context
        }
    }

}