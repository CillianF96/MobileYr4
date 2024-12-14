package ie.cillian.tushangout.hangout

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ie.cillian.tushangout.component.Screen
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun CreateMeetupScreen(navController: NavController) {
    var meetupName by remember { mutableStateOf("") }
    var courseName by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFA726), Color(0xFF212121))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TUSHangOut",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Meetup Name Field
            OutlinedTextField(
                value = meetupName,
                onValueChange = { meetupName = it },
                label = { Text("Meetup Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            OutlinedTextField(
                value = courseName,
                onValueChange = { courseName = it },
                label = { Text("Course") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Time Picker
            Button(
                onClick = { showTimePicker = true },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Starting Time: ${selectedTime.hour}:${selectedTime.minute}",
                    color = Color(0xFFFFA726)
                )
            }

            if (showTimePicker) {
                TimePickerDialog(
                    initialTime = selectedTime,
                    onTimeSelected = { time ->
                        selectedTime = time
                        showTimePicker = false
                    },
                    onDismissRequest = { showTimePicker = false }
                )
            }

            Button(
                onClick = { showDatePicker = true },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Enter Date: ${selectedDate.toString()}",
                    color = Color(0xFFFFA726)
                )
            }

            if (showDatePicker) {
                DatePickerDialog(
                    initialDate = selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                        showDatePicker = false
                    },
                    onDismissRequest = { showDatePicker = false }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate(Screen.MapLocation.route) },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
            ) {
                Text(text = "Create Meetup", color = Color(0xFFFFA726))
            }
        }
    }
}

@Composable
fun TimePickerDialog(
    initialTime: LocalTime? = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val selectedTime = LocalTime.of(hourOfDay, minute)
                onTimeSelected(selectedTime)
            },
            initialTime?.hour ?: 0,
            initialTime?.minute ?: 0,
            true
        )
    }

    DisposableEffect(Unit) {
        timePickerDialog.setOnCancelListener { onDismissRequest() }
        timePickerDialog.show()
        onDispose {
            timePickerDialog.dismiss()
        }
    }
}

@Composable
fun DatePickerDialog(
    initialDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                onDateSelected(selectedDate)
            },
            initialDate.year,
            initialDate.monthValue - 1,
            initialDate.dayOfMonth
        )
    }

    DisposableEffect(Unit) {
        datePickerDialog.setOnCancelListener { onDismissRequest() }
        datePickerDialog.show()
        onDispose {
            datePickerDialog.dismiss()
        }
    }
}
