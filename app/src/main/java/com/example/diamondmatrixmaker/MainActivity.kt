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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.theme.FinalProjectTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      FinalProjectTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          AppNavigation()
        }
      }
    }
  }
}

@Composable
fun AppNavigation() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = "main_menu") {
    composable("main_menu") { MainMenu(navController) }
    composable("matrix_screen") { MatrixScreen() }
    composable("diamond_screen") { DiamondScreen() }
  }
}

@Composable
fun MainMenu(navController: NavHostController) {
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
      Button(onClick = { navController.navigate("matrix_screen") }) {
        Text("Matrix")
      }
      Button(onClick = { navController.navigate("diamond_screen") }) {
        Text("Diamond")
      }
    }
  }
}

@Composable
fun MatrixScreen() {
  var input by remember { mutableStateOf("") }
  var matrixOutput by remember { mutableStateOf("") }
  val verticalScrollState = rememberScrollState()
  val horizontalScrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
      .verticalScroll(verticalScrollState)
      .horizontalScroll(horizontalScrollState),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("Matrix Generator", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
      value = input,
      onValueChange = { input = it },
      label = { Text("Enter matrix size") },
      modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = {
      matrixOutput = generateMatrix(input)
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
  }
}

