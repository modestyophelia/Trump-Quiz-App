package com.example.projektarbete1
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader

@Serializable // created new data class and serialize it to convert the data
data class Question(val id: Int, val question: String, val correctAnswer: Boolean)

private lateinit var scoreViewModel: ScoreViewModel

class QuestionScreen : AppCompatActivity() {
    private var context: Context? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        context = this@QuestionScreen
        val questions = (context as QuestionScreen).assets.open("Questions.json").bufferedReader().use(
            BufferedReader::readText) //use the context and open the json file, use buffered reader to read the input
        val questionList = Json.decodeFromString<List<Question>>(questions) //parse json file into a list containing the Question objects
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_screen)

        val trueBtn = findViewById<Button>(R.id.trueBtn)
        val falseBtn = findViewById<Button>(R.id.falseBtn)
        val questionTextView = findViewById<TextView>(R.id.questionTextView)
        val darkModeBtn = findViewById<SwitchCompat>(R.id.darkModeBtn)
        val score = Score()
        var index = 0

        darkModeBtn.setOnCheckedChangeListener { _, _ ->
            if (darkModeBtn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) // True
                darkModeBtn.text = "Disable Dark Mode"
                setTheme(R.style.Theme_Projektarbete1)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // False
                darkModeBtn.text = "Enable Light Mode"
                setTheme(R.style.Theme_Projektarbete1)
            }
        }

        trueBtn.setOnClickListener {

            if (index > 7 - 1) {
                val intent = Intent(this@QuestionScreen, FinishScreen::class.java)
                intent.putExtra("score", score.score) //share score value between screens
                startActivity(intent)
                if (questionList[index].correctAnswer) {
                    scoreViewModel.score += 1
                    score.score += 1
                    index += 1
                    Toast.makeText(this@QuestionScreen, "Correct!", Toast.LENGTH_SHORT).show()
                }
            }
            if (questionList[index].correctAnswer) {
                scoreViewModel.score += 1
                score.score += 1
                index += 1
                Toast.makeText(this@QuestionScreen, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                index += 1
                Toast.makeText(this@QuestionScreen, "Incorrect", Toast.LENGTH_SHORT).show()
            }


            println(score.score)
            println("index:$index")
            questionTextView.text = questionList[index].question
        }

        falseBtn.setOnClickListener {

        if (index > 7-1) {
            val intent = Intent(this@QuestionScreen, FinishScreen::class.java)
            intent.putExtra("score", score.score)
            startActivity(intent)
        }
        if (!questionList[index].correctAnswer) {
            scoreViewModel.score +=1
            score.score += 1
            index += 1
            Toast.makeText(this@QuestionScreen, "Correct!", Toast.LENGTH_SHORT).show()
        }
        else {
            index += 1
            Toast.makeText(this@QuestionScreen, "Incorrect", Toast.LENGTH_SHORT).show()
        }
            println(score.score)
            println("index:$index")
            questionTextView.text = questionList[index].question
            }

        scoreViewModel = ViewModelProvider(this)[ScoreViewModel::class.java]

    }
}
