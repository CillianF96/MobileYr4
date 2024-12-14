package ie.cillian.tushangout.component

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ie.cillian.tushangout.home.StartupScreen
import ie.cillian.tushangout.authentication.LoginScreen
import ie.cillian.tushangout.authentication.RegisterScreen
import ie.cillian.tushangout.home.HomeScreen
import ie.cillian.tushangout.hangout.CreateMeetupScreen
import ie.cillian.tushangout.hangout.DisplayMessagingForm
import ie.cillian.tushangout.hangout.MessageScreen
import ie.cillian.tushangout.location.CurrentLocationScreen
import ie.cillian.tushangout.location.MapLocationScreen
import ie.cillian.tushangout.location.MeetupLocationScreen
import ie.cillian.tushangout.home.HomeViewModel
import com.google.android.gms.maps.model.LatLng

@Composable
fun BuildNavigationGraph(
    homeViewModel: HomeViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Startup.route) {
        composable(Screen.Startup.route) { StartupScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController, homeViewModel) }
        composable(Screen.CreateMeetup.route) { CreateMeetupScreen(navController) }
        composable(Screen.DisplayMessagingForm.route) { DisplayMessagingForm(navController) }


        composable(
            route = "${Screen.Message.route}/{message}",
            arguments = listOf(navArgument("message") { defaultValue = "Default Message" })
        ) { backStackEntry ->
            val message = backStackEntry.arguments?.getString("message") ?: "Default Message"
            MessageScreen(navController, message)
        }

        composable(Screen.CurrentLocation.route) { CurrentLocationScreen(navController) }
        composable(Screen.MapLocation.route) {
            MapLocationScreen(
                navController = navController,
                saveLocation = { selectedLocation ->
                    homeViewModel.updateFirstItemWithLocation(selectedLocation)
                }
            )
        }
        composable(Screen.MeetupLocation.route) { MeetupLocationScreen(navController) }
    }
}


