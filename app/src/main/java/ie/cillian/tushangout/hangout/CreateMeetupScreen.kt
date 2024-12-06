package ie.cillian.tushangout.hangout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime

@Preview
@Composable
fun CreateMeetupScreen() {
    var meetupName by remember { mutableStateOf("") }
    var courseName by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFA726), Color(0xFF212121)) // Orange to Black gradient
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
            // App Header
            Text(
                text = "TUSHangOut",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Meetup Name Field
            OutlinedTextField(
                value = meetupName,
                onValueChange = { meetupName = it },
                label = { Text("Meetup Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            // Course Field
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
                Text(text = "Starting Time: ${selectedTime.hour}:${selectedTime.minute}", color = Color(0xFFFFA726))
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

            // Date Picker
            Button(
                onClick = { showDatePicker = true },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Enter Date: ${selectedDate.toString()}", color = Color(0xFFFFA726))
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

            // Create Meetup Button
            Button(
                onClick = { /* TODO: Handle create meetup logic */ },
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
    initialTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDismissRequest: () -> Unit
) {
    // Placeholder for Time Picker Dialog implementation
    // Replace with your TimePicker library or custom implementation
    // For now, we'll just close the dialog on cancel or selection
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select Time") },
        text = { Text("Time picker goes here") },
        confirmButton = {
            TextButton(onClick = { onTimeSelected(LocalTime.of(20, 0)) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DatePickerDialog(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    // Placeholder for Date Picker Dialog implementation
    // Replace with your DatePicker library or custom implementation
    // For now, we'll just close the dialog on cancel or selection
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select Date") },
        text = { Text("Date picker goes here") },
        confirmButton = {
            TextButton(onClick = { onDateSelected(LocalDate.now()) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}
