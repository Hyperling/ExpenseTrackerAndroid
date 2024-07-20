package com.hyperling.expensetracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyperling.expensetracker.ui.theme.ExpenseTrackerTheme
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly


// Activity which allows data to be entered and saved to Shared Preferences.
// https://stackoverflow.com/questions/59436808/simple-kotlin-data-save

// Actually, probably best to use Room and a database rather than preferences.

class NewExpenseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                Surface {
                    CreateExpense()
                }
            }
        }
    }
}

@Composable
fun CreateExpense() {

    /*** Activity Setup ***/

    val context = LocalContext.current
    val intent = Intent(context, MainActivity::class.java)
    fun returnToMain() {
        // set the new task and clear flags
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    // https://medium.com/jetpack-composers/jetpack-compose-room-db-b7b23bd6b189
    //val sharedPref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun saveAndReturn() {
        //val editor = sharedPref.edit()
        //editor.putString("Test", "Test")
        returnToMain()
    }

    /*** UI Variables ***/

    var text by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    // https://jetpackcomposeworld.com/radio-buttons-in-jetpack-compose/
    val freqOptions = listOf("Daily", "Weekly", "Monthly", "Yearly")
    var freqSelected by remember { mutableStateOf(freqOptions[2]) }

    /*** UI ***/

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        // Header //
        Text(
            text = "Add New Expense",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
        )

        // Input //
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TextField (
                value = text,
                onValueChange = { text = it },
                label = { Text("Name") },
                modifier = Modifier.padding(4.dp)
            )
            TextField (
                value = cost,
                onValueChange = {
                    val lastChar = it.substring(it.length-1)
                    if (lastChar.isDigitsOnly())
                        cost = it
                    if (lastChar == ".")
                        cost = it.replace(".", "") + "."
                },
                label = { Text("Cost") },
                modifier = Modifier.padding(4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text (
                    text = "Frequency: "
                )
                Column (
                    horizontalAlignment = Alignment.Start
                ) {
                    freqOptions.forEach { freq ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (freq == freqSelected),
                                onClick = { freqSelected = freq }
                            )
                            Text(
                                text = freq
                            )
                        }
                    }
                }
            }
        }


        // Action Buttons //
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { saveAndReturn() },
                Modifier.weight(3f).padding(4.dp),
            ) {
                Text(text = "Save")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { returnToMain() },
                Modifier.weight(3f).padding(4.dp),
            ) {
                Text(text = "Cancel")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateExpensePreview() {
    ExpenseTrackerTheme {
        CreateExpense()
    }
}

