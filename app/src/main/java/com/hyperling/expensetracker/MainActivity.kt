package com.hyperling.expensetracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.hyperling.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                Surface {
                    Main()
                }
            }
        }
    }
}

// This seems like a great tutorial for what I need to do!
// https://www.answertopia.com/jetpack-compose/a-jetpack-compose-room-database-and-repository-tutorial/

@Composable
fun Main() {
    val context = LocalContext.current

    var sumDaily by remember { mutableStateOf(0.0) }
    var sumWeekly by remember { mutableStateOf(0.0) }
    var sumMonthly by remember { mutableStateOf(0.0) }
    var sumYearly by remember { mutableStateOf(0.0) }

    val sums = mapOf(
        "Daily" to sumDaily,
        "Weekly" to sumWeekly,
        "Monthly" to sumMonthly,
        "Yearly" to sumYearly,
    )

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Current Expense Summary"
        )

        //for ((name, value) in sums) {
        sums.forEach { (name, value) ->
            Row (
                horizontalArrangement = Arrangement.End,
            ){
                Text(text = name + ": ")
                Text(text = value.toString())
            }
        }

        // FORTESTING
        Text (text = String.format("%.2f",sumDaily))
        Text (text = String.format("%.2f",sumWeekly))
        Text (text = String.format("%.2f",sumMonthly))
        Text (text = String.format("%.2f",sumYearly))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = {
                val intent = Intent(context, NewExpenseActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(text = "Create New Expense")
            }
        }

        Text(text = "Expenses")
        // https://medium.com/@rowaido.game/implementing-the-room-library-with-jetpack-compose-590d13101fa7
        /* TODO:
            ForEach over all DB records, show it, and add its value to the summary.
         */
        val expenseArray = listOf(
            Expense("Test", 180.0, 'Y', "My notes."),
            Expense("Test2", 20.0, 'M', "My notes, 2!"),
        )
        expenseArray.forEach { expense ->
            when (expense.freq) {
                'D' -> sumYearly += (expense.cost * 365.25)
                'W' -> sumYearly += (expense.cost * (365.25 / 7))
                'M' -> sumYearly += (expense.cost * 12)
                'Y' -> sumYearly += (expense.cost)
            }

            sumDaily = sumYearly / 365.25
            sumWeekly = sumYearly / (365.25 / 7)
            sumMonthly = sumYearly / 12
        }

        // FORTESTING
        Text (text = String.format("$ %.2f",sumDaily))
        Text (text = String.format("$ %.2f",sumWeekly))
        Text (text = String.format("$ %.2f",sumMonthly))
        Text (text = String.format("$ %.2f",sumYearly))
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ExpenseTrackerTheme {
        Main()
    }
}