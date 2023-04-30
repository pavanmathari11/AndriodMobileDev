package edu.fullerton.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private lateinit var screen: TextView
    private var display = ""
    private lateinit var inputtext: EditText
    private lateinit var displaytext: TextView
    private var currentOperator = ""
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deletevar = findViewById<ImageButton>(R.id.butdelet)
        deletevar.setOnClickListener {
            deletenumber()
        }

        screen = findViewById(R.id.input_box)
        screen.text = display
        inputtext = findViewById(R.id.input_box)
        displaytext = findViewById(R.id.result_box)
    }

    private fun appendToLast(str: String) {
        this.inputtext.text.append(str)
    }

    fun onClickNumber(v: View) {
        val b = v as Button
        display += b.text
        appendToLast(display)
        display=""
    }

    fun onClickOperator(v: View) {
        val b = v as Button
        display += b.text
        if (endsWithOperatore()) {
            replace(display)
            currentOperator = b.text.toString()
            display = ""
        } else {
            appendToLast(display)
            currentOperator = b.text.toString()
            display = ""
        }
    }

    fun onClearButton(v: View) {
        inputtext.text.clear()
        displaytext.text = ""
    }

    private fun deletenumber() {
        this.inputtext.text.delete(getinput().length - 1, getinput().length)
    }

    private fun getinput(): String {
        return this.inputtext.text.toString()
    }

    private fun endsWithOperatore(): Boolean {
        return getinput().endsWith("+") || getinput().endsWith("-") || getinput().endsWith("/") || getinput().endsWith("x")
    }

    private fun replace(str: String) {
        inputtext.text.replace(getinput().length - 1, getinput().length, str)
    }

    private fun operate(a: String, b: String, cp: String): Double {
        return when (cp) {
            "+" -> a.toDouble() + b.toDouble()
            "-" -> a.toDouble() - b.toDouble()
            "x" -> a.toDouble() * b.toDouble()
            "\u00F7" -> a.toDouble() / b.toDouble()
            else -> -1.0
        }
    }

    fun equalresult(v: View) {
        var input = getinput()

        if (!endsWithOperatore()) {
            if (input.contains("x")) {
                input = input.replace("x", "*")
            }
            if (input.contains("\u00F7")) {
                input = input.replace("\u00F7", "/")
            }

            val expression = ExpressionBuilder(input).build()
            val result = expression.evaluate()

            displaytext.text = result.toString()
        } else {
            displaytext.text = ""
        }
    }
}
