package com.example.dicegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dicegame.ui.theme.DiceGameTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.dicegame.R.drawable
import kotlin.random.Random
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.text.input.KeyboardType
// EditText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyScreenContent()
        }
    }
}

@Composable
fun MyScreenContent() {
    // Assuming DiceGameTheme is your app's theme and Surface is the background
    DiceGameTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(16.dp)) {
                val diceRoll = remember { mutableStateOf(1) }
                val mathProblem = remember { mutableStateOf("")}
                var userAnswer by remember { mutableStateOf("")}
                val message = remember { mutableStateOf("")}
                var player1 by remember { mutableStateOf(0)}
                var num1 by remember { mutableStateOf(0)}
                var num2 by remember { mutableStateOf(0)}
                var sum by remember { mutableStateOf(0)}
                var difference by remember { mutableStateOf(0)}
                var product by remember { mutableStateOf(0)}
                // Place your button inside this Column
                Button(
                    onClick = {
                        // Handle the button click here
                        val result = Random.nextInt(1, 7)
                        diceRoll.value = result
                        userAnswer = ""
                        message.value = ""
                        when (result) {
                            1 -> {
                                num1 = Random.nextInt(0, 100)
                                num2 = Random.nextInt(0, 100)
                                sum = num1 + num2
                                mathProblem.value = "$num1 + $num2 = ?"
                            }
                            2 -> {
                                num1 = Random.nextInt(0, 100)
                                num2 = Random.nextInt(0, 100)
                                difference = num1 - num2
                                mathProblem.value = "$num1 - $num2 = ?"
                            }
                            3 -> {
                                num1 = Random.nextInt(0, 21)
                                num2 = Random.nextInt(0, 21)
                                product = num1 * num2
                                mathProblem.value = "$num1 * $num2 = ?"
                            }
                            4 -> {
                                mathProblem.value = "Roll again for double points!"
                            }
                            5 -> {
                                mathProblem.value = "Lose a turn!"
                            }
                            else -> {
                                mathProblem.value = "Jackpot attempt!"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Roll the Die")
                }
                Text("Player 1: $player1")
                // ... other composables
                Image (
                    painter = painterResource(id = when (diceRoll.value) {
                        1 -> drawable.dice_1
                        2 -> drawable.dice_2
                        3 -> drawable.dice_3
                        4 -> drawable.dice_4
                        5 -> drawable.dice_5
                        else -> drawable.dice_6
                    }),
                    contentDescription = "Dice Image"
                )

                Text("Dice Roll: ${diceRoll.value}")
                Text("Math Problem: ${mathProblem.value}")

                if (mathProblem.value.isNotEmpty() && diceRoll.value in 1..3) {
                    TextField(
                        value = userAnswer,
                        onValueChange = { userAnswer = it },
                        label = { Text("Answer") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedButton(
                        onClick = {
                            if (userAnswer.isNotEmpty()) {
                                val answer = userAnswer.toInt()
                                if (diceRoll.value == 1 && answer == sum) {
                                    message.value = "Correct!"
                                    player1 += 1
                                } else if (diceRoll.value == 2 && answer == difference) {
                                    message.value = "Correct!"
                                    player1 += 2
                                } else if (diceRoll.value == 3 && answer == product) {
                                    message.value = "Correct!"
                                    player1 += 3
                                } else {
                                    message.value = "Incorrect!"
                                }
                            }
                        }
                    ) {
                        Text("Submit Answer")
                    }

                    if (message.value.isNotEmpty()) {
                        Text(message.value)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyScreenContent()
}