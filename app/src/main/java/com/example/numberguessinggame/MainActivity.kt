package com.example.numberguessinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NumberGuessingGameLayout()
                }
            }
        }
    }
}

@Composable
fun NumberGuessingGameLayout() {
    var guessInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isGameOver by remember { mutableStateOf(false) }
    val randomNumber = remember { mutableStateOf(Random.nextInt(1, 1001)) }
    val guess = guessInput.toIntOrNull()
    var numberOfGuesses by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().alpha(0.8f) ,

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Text(
                text = stringResource(R.string.detail),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )

            EditNumberField(
                value = guessInput,
                onValueChanged = { guessInput = it },
                modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
            )

            Button(
                onClick = {
                    message = if (guess != null) {
                        numberOfGuesses++
                        when {
                            guess < randomNumber.value -> {
                                if (guess < randomNumber.value - 50) {
                                    "Way too low! \nTry a much higher number."
                                } else {
                                    "Too low! \nTry a higher number."
                                }
                            }
                            guess > randomNumber.value -> {
                                if (guess > randomNumber.value + 50) {
                                    "Way too high! \nTry a much lower number."
                                } else {
                                    "Too high! \nTry a lower number."
                                }
                            }
                            else -> {
                                isGameOver = true
                                "Congratulations! You guessed it!"
                            }
                        }
                    } else {
                        "Please enter a valid number."
                    }
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(width = 200.dp, height = 60.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFFFF9000)
                )
            ) {
                Text(text = stringResource(R.string.check_guess),
                    style = TextStyle(
                        fontSize = 18.sp
                    )

                )
            }

            if (isGameOver) {
                AlertDialog(
                    onDismissRequest = {
                        isGameOver = false
                        guessInput = ""
                        message = ""
                        randomNumber.value = Random.nextInt(1, 1001)
                    },
                    title = { Text(text = "Congratulations!") },
                    text = {
                        Text(
                            text = "You guessed it in $numberOfGuesses guesses. " +
                                    "\nYou have guessed it correctly.",
                            style = TextStyle(
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(bottom = 16.dp).align(alignment = Alignment.CenterHorizontally),
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                isGameOver = false
                                guessInput = ""
                                message = ""
                                randomNumber.value = Random.nextInt(1, 1001)
                                numberOfGuesses = 0
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Play Again")
                        }
                    }
                )
            }
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)


            )
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = {
            Text(
                text = stringResource(R.string.enter_guess),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun NumberGuessingGameLayoutPreview() {
    NumberGuessingGameTheme {
        NumberGuessingGameLayout()
    }
}
