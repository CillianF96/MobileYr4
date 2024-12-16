package ie.cillian.tushangout

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import ie.cillian.tushangout.component.BuildNavigationGraph
import ie.cillian.tushangout.ui.theme.TUSHangoutTheme

import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseFirestore.getInstance()
        setContent {
            TUSHangoutTheme {
                BuildNavigationGraph()
            }
        }
    }
}
