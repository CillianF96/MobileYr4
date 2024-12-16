package ie.cillian.tushangout.authentication

import android.util.Log
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

@Composable
fun RegisterScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

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
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "TUSHangOut",
                fontSize = 32.sp,
                color = Color.Black
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = course,
                onValueChange = { course = it },
                label = { Text("Course") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        if (username.isEmpty() || course.isEmpty() || email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(
                                navController.context, "All fields are required!", Toast.LENGTH_LONG
                            ).show()
                        } else if (password != confirmPassword) {
                            Toast.makeText(
                                navController.context, "Passwords do not match!", Toast.LENGTH_LONG
                            ).show()
                        } else {
                            isLoading = true
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        val userId = firebaseAuth.currentUser?.uid ?: UUID.randomUUID().toString()
                                        val user = hashMapOf(
                                            "id" to userId,
                                            "name" to username,
                                            "course" to course,
                                            "email" to email
                                        )

                                        // Add user to Firestore
                                        firestore.collection("users").document(userId)
                                            .set(user)
                                            .addOnSuccessListener {
                                                Log.d("RegisterScreen", "User added to Firestore")
                                                Toast.makeText(
                                                    navController.context,
                                                    "Registration successful!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                navController.navigate("login")
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e("RegisterScreen", "Error adding user: ${e.message}")
                                                Toast.makeText(
                                                    navController.context,
                                                    "Failed to save user data!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                    } else {
                                        val errorMessage = task.exception?.message ?: "Registration failed."
                                        Log.e("RegisterScreen", errorMessage)
                                        Toast.makeText(
                                            navController.context, errorMessage, Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Register", color = Color(0xFFFFA726))
                }
            }
        }
    }
}
