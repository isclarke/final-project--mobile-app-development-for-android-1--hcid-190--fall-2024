package com.example.diamondmatrixmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diamondmatrixmaker.ui.theme.DiamondmatrixmakerTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      DiamondmatrixmakerTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          MainMenu()
        }
      }
    }
  }
}

@Composable
fun MainMenu() {
  var currentScreen by remember { mutableStateOf("main_menu") }

  when (currentScreen) {
    "main_menu" -> MainMenuContent(onNavigate = { currentScreen = it })
    "matrix_screen" -> MatrixScreen(onBack = { currentScreen = "main_menu" })
    "diamond_screen" -> DiamondScreen(onBack = { currentScreen = "main_menu" })
  }
}

@Composable
fun MainMenuContent(onNavigate: (String) -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Which item would you like to render in the app?", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(32.dp))

    Row(
      horizontalArrangement = Arrangement.SpaceEvenly,
      modifier = Modifier.fillMaxWidth()
    ) {
      Button(onClick = { onNavigate("matrix_screen") }) {
        Text("Matrix Gen")
      }
      Button(onClick = { onNavigate("diamond_screen") }) {
        Text("Diamond Gen")
      }
    }
  }
}

@Composable
fun MatrixScreen(onBack: () -> Unit) {
  var input by remember { mutableStateOf("") }
  var matrixOutput by remember { mutableStateOf("") }
  var matrixSize by remember { mutableStateOf(0) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
      .verticalScroll(rememberScrollState())
      .horizontalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Matrix Generator", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
      value = input,
      onValueChange = {
        input = it
        matrixSize = it.toIntOrNull() ?: 0
      },
      label = { Text("Enter matrix size") },
      modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = {
      matrixOutput = generateMatrix(matrixSize)
    }) {
      Text("Generate Matrix")
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = matrixOutput,
      textAlign = TextAlign.Center,
      fontSize = 14.sp,
      modifier = Modifier.padding(16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = onBack) {
      Text("Back")
    }
  }
}

@Composable
fun DiamondScreen(onBack: () -> Unit) {
  var size by remember { mutableStateOf("") }
  var diamondOutput by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Diamond Generator", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
      value = size,
      onValueChange = { size = it },
      label = { Text("Enter diamond size") },
      modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = {
      diamondOutput = generateDiamond(size)
    }) {
      Text("Generate Diamond")
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = diamondOutput,
      textAlign = TextAlign.Center,
      fontSize = 14.sp,
      modifier = Modifier.padding(16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = onBack) {
      Text("Back")
    }
  }
}

private fun generateMatrix(size: Int): String {
  if (size <= 0) {
    return "Invalid matrix size"
  } else {

    val matrix = Array(size) { IntArray(size) { 0 } }
    val output = StringBuilder()

    output.append("Printing matrix with default value: \n")
    output.append(defaultMatrix(matrix))

    output.append("Printing matrix: \n")
    output.append(numberMatrix(matrix))

    output.append("Printing flipped matrix: \n")
    output.append(swapMatrix(matrix))

    return output.toString()
  }
}

private fun defaultMatrix(matrix: Array<IntArray>): String {
  val size = matrix.size
  val maxNumber = size * size
  val width = maxNumber.toString().length - 2
  val output = StringBuilder()

  for ((rowIndex, row) in matrix.withIndex()) {
    for ((columnIndex, num) in row.withIndex()) {
      val isDiagonal = columnIndex == size - 1 - rowIndex
      output.append(
        if (isDiagonal) {
          "$num".padStart(width)
        } else {
          num.toString().padStart(width)
        }
      )
      output.append(" ")
    }
    output.append("\n")
  }
  return output.toString()
}

private fun numberMatrix(matrix: Array<IntArray>): String {
  val size = matrix.size
  var count = 1
  val maxNumber = size * size
  val width = maxNumber.toString().length - 2
  val output = StringBuilder()

  for (rowIndex in 0 until size) {
    for (columnIndex in 0 until size) {
      val isDiagonal = columnIndex == size - 1 - rowIndex
      output.append(
        if (isDiagonal) {
          "$count".padStart(width)
        } else {
          count.toString().padStart(width)
        }
      )
      count++
      output.append(" ")
    }
    output.append("\n")
  }
  return output.toString()
}

private fun swapMatrix(matrix: Array<IntArray>): String {
  val size = matrix.size
  val output = StringBuilder()
  val maxNumber = size * size
  val width = maxNumber.toString().length - 2

  for (rowIndex in 0 until size) {
    for (columnIndex in 0 until size) {

      val isDiagonal = columnIndex == size - 1 - rowIndex
      val value = if (isDiagonal) {
        "${(rowIndex * size + columnIndex + 1)}"
      } else {
        (maxNumber - (rowIndex * size + columnIndex)).toString()
      }

      output.append(value.padStart(width) + " ")
    }
    output.append("\n")
  }
  return output.toString()
}

fun generateDiamond(input: String): String {
  val number = input.toIntOrNull() ?: return "Invalid input"
  if (number <= 0) return "Please enter a positive number"

  val stringBuilder = StringBuilder()
  val isEven = number % 2 == 0

  if (isEven) {
    // Top single star
    val topSpaces = number / 2 - 1
    stringBuilder.append("  ".repeat(topSpaces) + "  *")
    stringBuilder.appendLine()

    for (i in 1..number / 2) {
      val spaceCount = number / 2 - i
      val starCount = 2 * (i + 1) - 2

      // Print spaces
      stringBuilder.append("  ".repeat(spaceCount))
      // Print stars with spaces between them
      stringBuilder.append(" *".repeat(starCount))
      stringBuilder.appendLine()
    }

    // Bottom half of the diamond (excluding the single bottom star)
    for (i in 0 until number / 2 - 1) {
      val spaceCount = i + 1
      val starCount = number - 2 * (i + 1)

      // Print spaces
      stringBuilder.append("  ".repeat(spaceCount))
      // Print stars with spaces between them
      stringBuilder.append(" *".repeat(starCount))
      stringBuilder.appendLine()
    }

    // Bottom  star
    stringBuilder.append("  ".repeat(topSpaces) + "  *")
    stringBuilder.appendLine()

    //odd variation
  } else {
    for (i in 0 until number / 2 + 1) {
      val spaceCount = number / 2 - i
      val starCount = 2 * i + 1

      stringBuilder.append(" ".repeat(spaceCount))
      stringBuilder.append("* ".repeat(starCount).trimEnd())
      stringBuilder.appendLine()
    }

    for (i in number / 2 - 1 downTo 0) {
      val spaceCount = number / 2 - i
      val starCount = 2 * i + 1

      // Print spaces
      stringBuilder.append(" ".repeat(spaceCount))
      stringBuilder.append("* ".repeat(starCount).trimEnd())
      stringBuilder.appendLine()
    }
  }

  return stringBuilder.toString().trimEnd()
}
