package ie.cillian.tushangout.hangout

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import ie.cillian.tushangout.component.Screen
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Composable
fun CreateMeetupScreen(navController: NavController) {
    var meetupName by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) } // Holds selected LatLng
    var isSaving by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val firestore = FirebaseFirestore.getInstance()

    // Retrieve the selected location returned from MapLocationScreen
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.get<LatLng>("selectedLocation")?.let { latLng ->
            selectedLocation = latLng
            savedStateHandle.remove<LatLng>("selectedLocation") // Clear the state after retrieving
        }
    }

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
            Text("TUSHangOut", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            OutlinedTextField(
                value = meetupName,
                onValueChange = { meetupName = it },
                label = { Text("Meetup Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = course,
                onValueChange = { course = it },
                label = { Text("Course") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { showTimePicker = true },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Starting Time: ${selectedTime.hour}:${selectedTime.minute}")
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enter Date: $selectedDate")
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

            Button(
                onClick = { navController.navigate(Screen.MapLocation.route) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Location on Map")
            }

            selectedLocation?.let {
                Text(
                    text = "Selected Location: ${it.latitude}, ${it.longitude}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (meetupName.isNotEmpty() && course.isNotEmpty() && selectedLocation != null) {
                        isSaving = true
                        val meetup = hashMapOf(
                            "meetupName" to meetupName,
                            "course" to course,
                            "time" to "${selectedTime.hour}:${selectedTime.minute}",
                            "date" to selectedDate.toString(),
                            "latitude" to selectedLocation!!.latitude,
                            "longitude" to selectedLocation!!.longitude,
                            "createdAt" to Date()
                        )

                        firestore.collection("meetups")
                            .add(meetup)
                            .addOnSuccessListener {
                                isSaving = false
                                Toast.makeText(
                                    context,
                                    "Meetup created successfully!",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.popBackStack()
                            }
                            .addOnFailureListener { exception ->
                                isSaving = false
                                Toast.makeText(
                                    context,
                                    "Error: ${exception.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    } else {
                        Toast.makeText(
                            context,
                            "All fields, including location, are required!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Create Meetup")
                }
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
    val context = LocalContext.current
    val dialog = android.app.TimePickerDialog(
        context,
        { _, hourOfDay, minute -> onTimeSelected(LocalTime.of(hourOfDay, minute)) },
        initialTime.hour,
        initialTime.minute,
        true
    )
    DisposableEffect(Unit) {
        dialog.setOnDismissListener { onDismissRequest() }
        dialog.show()
        onDispose { dialog.dismiss() }
    }
}

@Composable
fun DatePickerDialog(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val dialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth -> onDateSelected(LocalDate.of(year, month + 1, dayOfMonth)) },
        initialDate.year,
        initialDate.monthValue - 1,
        initialDate.dayOfMonth
    )
    DisposableEffect(Unit) {
        dialog.setOnDismissListener { onDismissRequest() }
        dialog.show()
        onDispose { dialog.dismiss() }
    }
}
