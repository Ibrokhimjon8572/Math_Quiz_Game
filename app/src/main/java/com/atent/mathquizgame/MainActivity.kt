package com.atent.mathquizgame

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var A = 0
    private var B = 0

    private var score = 0
    private var questionNumber = 1

    var timer = 30000//30secund

    private var locationOfCorrectAnswer = 0
    private var options: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateQuestions()

        startTimer()

        btn_play_again.setOnClickListener {
            opt_1.isEnabled = true
            opt_2.isEnabled = true
            opt_3.isEnabled = true
            opt_4.isEnabled = true

            count_view.text = "$score / $questionNumber"
            btn_play_again.visibility = View.INVISIBLE
            timer_view.text = (timer / 1000).toString()
            startTimer()
            generateQuestions()
        }
    }

    fun generateQuestions() {
        options.clear()

        A = (1 until 21).random()
        B = (1 until 21).random()

        question_view.text = "$A + $B"
        locationOfCorrectAnswer = (0 until 4).random()

        var incorrectAnswer: Int

        for (i in 0 until 4) {
            if (i == locationOfCorrectAnswer) {
                options.add(A + B)
            } else {
                incorrectAnswer = (5 until 41).random()

                while (incorrectAnswer == A + B || incorrectAnswer in options) {
                    incorrectAnswer = (5 until 41).random()
                }
                options.add(incorrectAnswer)
            }

        }
        opt_1.text = options[0].toString()
        opt_2.text = options[1].toString()
        opt_3.text = options[2].toString()
        opt_4.text = options[3].toString()
    }

    fun choosAnsver(view: View) {
        result_text.visibility = View.VISIBLE
        if (view.tag.toString() == locationOfCorrectAnswer.toString()) {
            score++
            result_text.text = "Javob to'g'ri"
        } else {
            result_text.text = "Javob noto'g'ri!.."
            result_text.setTextColor(Color.parseColor("#CD5C5C"))
        }
        object : CountDownTimer(1000, 1000) {
            override fun onFinish() {
                result_text.setTextColor(Color.parseColor("#DDCECE"))
                result_text.visibility = View.INVISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()

        count_view.text = "$score / $questionNumber"
        questionNumber++
        generateQuestions()
    }

    fun startTimer() {
        object : CountDownTimer((timer + 100).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer_view.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                timer_view.text = "0"
                val builder = AlertDialog.Builder(this@MainActivity)
                    .setTitle("O'yin tugadi..")
                    .setCancelable(false)
                    .setMessage("Siz $questionNumber ta savoldan $score ta toptingiz!..")
                    .setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                val dialog = builder.create()
                dialog.show()

                clearGame()

                opt_1.isEnabled = false
                opt_2.isEnabled = false
                opt_3.isEnabled = false
                opt_4.isEnabled = false

                btn_play_again.visibility = View.VISIBLE

            }

        }.start()

    }

    fun clearGame() {
        opt_1.text = ""
        opt_2.text = ""
        opt_3.text = ""
        opt_4.text = ""

        question_view.text = ""
        score = 0
        questionNumber = 1

    }

}