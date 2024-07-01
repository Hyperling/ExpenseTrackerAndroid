package com.hyperling.expensetracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.hyperling.expensetracker.ui.theme.ExpenseTrackerTheme


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
    val context = LocalContext.current
    val intent = Intent(context, MainActivity::class.java)
    fun returnToMain() {
        // set the new task and clear flags
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    val sharedPref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun saveAndReturn() {
        val editor = sharedPref.edit()
        editor.putString("Test", "Test")
        returnToMain()
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "New Expense Screen"
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { returnToMain() }) {
                Text(text = "Cancel")
            }
            Button(onClick = { saveAndReturn() }) {
                Text(text = "Cancel")
            }
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

