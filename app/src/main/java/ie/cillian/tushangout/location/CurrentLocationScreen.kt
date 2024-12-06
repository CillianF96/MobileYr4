package ie.cillian.tushangout.location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.android.gms.maps.model.LatLng

@Preview
@Composable
fun CurrentLocationScreen() {
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
            LatLng(14.5995, 120.9842), // Manila Coordinates
            12f
        )
    }

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
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "TUSHangOut",
                    fontSize = 24.sp,
                    color = Color.Black
                )
                IconButton(onClick = { /* TODO: Handle location refresh */ }) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "Refresh Location",
                        tint = Color.Black
                    )
                }
            }

            // Map Section
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true)
            ) {
                // Add markers for your location and the destination
                Marker(
                    state = MarkerState(position = LatLng(14.5995, 120.9842)), // Example Location
                    title = "Your Location"
                )
                Marker(
                    state = MarkerState(position = LatLng(14.5506, 121.0347)), // Example Destination
                    title = "Brew Mix Coffee Shop"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Any Issues Section
            Text(
                text = "Any Issues",
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* TODO: Handle message */ },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color(0xFFFFA726)
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Message, contentDescription = "Message")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Message")
                }
                IconButton(onClick = { /* TODO: Handle issues button */ }) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "Report Issue",
                        tint = Color(0xFFFFA726)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estimated Time and Start Button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2E2E2E))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "42 min (8.1 km)",
                        fontSize = 18.sp,
                        color = Color(0xFFFFA726)
                    )
                    Text(
                        text = "Fastest route, despite the usual traffic",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /* TODO: Handle Start Navigation */ },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6200EE),
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Start", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
