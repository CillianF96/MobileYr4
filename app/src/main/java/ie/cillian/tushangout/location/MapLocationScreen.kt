package ie.cillian.tushangout.location

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapLocationScreen(navController: NavController) {
    var selectedLocation by remember { mutableStateOf(LatLng(52.6638, -8.6267)) }

    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(selectedLocation, 15f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Outlined.ArrowBack, "Back")
                }
                Text("Select Location", fontSize = 20.sp, color = Color.Black)
            }

            Box(modifier = Modifier.weight(1f)) {
                GoogleMap(
                    cameraPositionState = cameraPositionState,
                    onMapClick = { latLng ->
                        selectedLocation = latLng
                    }
                ) {
                    Marker(
                        state = MarkerState(position = selectedLocation),
                        title = "Selected Location"
                    )
                }
            }

            Button(
                onClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle?.set("selectedLocation", selectedLocation)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp)
            ) {
                Text("Save Location")
            }

        }
    }
}
