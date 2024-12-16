package ie.cillian.tushangout.hangout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import ie.cillian.tushangout.component.Screen
import java.util.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    navController: NavController,
    message: String,
    messageViewModel: MessageViewModel
) {
    var userMessage by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    val firestore = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()

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
            // App Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "TUSHangOut",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8E1F4))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Selected Location:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                label = { Text("Message") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = Color.Black
                )
            )

            Button(
                onClick = {
                    if (userMessage.isNotEmpty()) {
                        isSaving = true

                        val messageData = hashMapOf(
                            "senderName" to "User",
                            "messageText" to userMessage,
                            "createdAt" to Date().time
                        )

                        // Save to Firestore
                        firestore.collection("messages")
                            .add(messageData)
                            .addOnSuccessListener {
                                // Save to Room database in a coroutine
                                coroutineScope.launch {
                                    messageViewModel.insertMessage("User", userMessage)
                                }
                                isSaving = false
                                navController.navigate("${Screen.DisplayMessagingForm.route}/$userMessage")
                            }
                            .addOnFailureListener {
                                isSaving = false
                                println("Error saving message: ${it.message}")
                            }
                    }
                },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                enabled = !isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = Color(0xFFFFA726))
                } else {
                    Text(text = "Send", color = Color(0xFFFFA726))
                }
            }
        }
    }
}
