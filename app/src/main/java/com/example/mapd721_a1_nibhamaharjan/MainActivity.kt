// Nibha Maharjan
// StudentID: 301282952
// Datastore Assignment
package com.example.mapd721_a1_nibhamaharjan

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapd721_a1_nibhamaharjan.datastore.StoreUserEmail
import com.example.mapd721_a1_nibhamaharjan.ui.theme.MAPD721A1NibhaMaharjanTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A1NibhaMaharjanTheme {
                // A surface container
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(this) // Pass the activity context
                }
            }
        }
    }
}

@Composable
fun MainScreen(activity: ComponentActivity) {
    // context
    val context = activity.applicationContext
    // scope
    val scope = rememberCoroutineScope()
    // datastore Email
    val dataStore = StoreUserEmail(context)

    // get saved name email and id
    var nameState by remember { mutableStateOf("Nibha Maharjan") }
    var emailState by remember { mutableStateOf("nibhamaharjan@yahoo.com") }
    var idState by remember { mutableStateOf("301282952") }

    // Load saved data on composition start
    DisposableEffect(Unit) {
        scope.launch {
            nameState = dataStore.getName.first() ?: nameState
            emailState = dataStore.getEmail.first() ?: emailState
            idState = dataStore.getId.first() ?: idState
        }
        onDispose { }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Input fields for saving and loading data
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp, top = 30.dp),
                text = "Email",
                color = Color.Gray,
                fontSize = 12.sp
            )
            // email field
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = emailState,
                onValueChange = { emailState = it },
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = nameState,
                onValueChange = { nameState = it },
                label = { Text(text = "Name", color = Color.Gray, fontSize = 12.sp) },
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = idState,
                onValueChange = { idState = it },
                label = { Text(text = "ID", color = Color.Gray, fontSize = 12.sp) },
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Save, Load, Clear buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Save button
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .padding(end = 8.dp),
                    onClick = {
                        // launch the class in a coroutine scope
                        scope.launch {
                            dataStore.saveEmail(nameState, emailState, idState)
                            showToast(context, "Data saved")
                            // Clear input fields after saving
                            emailState = ""
                            nameState = ""
                            idState = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Blue
                    )
                ) {
                    // button text
                    Text(
                        text = "Save",
                        fontSize = 18.sp
                    )
                }

                // Load button
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .padding(horizontal = 8.dp),
                    onClick = {
                        // launch the class in a coroutine scope
                        scope.launch {
                            // Load data and update the fields
                            nameState = dataStore.getName.first() ?: ""
                            emailState = dataStore.getEmail.first() ?: ""
                            idState = dataStore.getId.first() ?: ""
                            showToast(context, "Data loaded: Name=$nameState, Email=$emailState, ID=$idState")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Cyan
                    )
                ) {
                    // button text
                    Text(
                        text = "Load",
                        fontSize = 18.sp
                    )
                }

                // Clear button 
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .padding(start = 8.dp),
                    onClick = {
                        // launch the class in a coroutine scope
                        scope.launch {
                            // Clear data
                            dataStore.saveEmail("", "", "")
                            // Clear input fields
                            emailState = ""
                            nameState = ""
                            idState = ""
                            showToast(context, "Data cleared")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.LightGray
                    )
                ) {
                    // button text
                    Text(
                        text = "Clear",
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(300.dp))
        }

        // Display saved data
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = "Name: Nibha Maharjan\nEmail: nibhamaharjan@yahoo.com\nID: 301282952",
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MAPD721A1NibhaMaharjanTheme {
        MainScreen(ComponentActivity())
    }
}
