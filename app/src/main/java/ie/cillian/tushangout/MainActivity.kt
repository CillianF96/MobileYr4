package ie.cillian.tushangout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

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
