package ie.cillian.tushangout.component

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ie.cillian.tushangout.authentication.LoginScreen
import ie.cillian.tushangout.authentication.RegisterScreen
import ie.cillian.tushangout.hangout.CreateMeetupScreen
import ie.cillian.tushangout.hangout.DisplayMessagingForm
import ie.cillian.tushangout.hangout.MessageScreen
import ie.cillian.tushangout.hangout.MessageViewModel
import ie.cillian.tushangout.home.HomeScreen
import ie.cillian.tushangout.home.HomeViewModel
import ie.cillian.tushangout.home.StartupScreen
import ie.cillian.tushangout.location.CurrentLocationScreen
import ie.cillian.tushangout.location.MapLocationScreen
import ie.cillian.tushangout.location.MeetupLocationScreen

@Composable
fun BuildNavigationGraph(
    homeViewModel: HomeViewModel = viewModel(),
    messageViewModel: MessageViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Startup.route) {

        composable(Screen.Startup.route) { StartupScreen(navController) }

        composable(Screen.Login.route) { LoginScreen(navController) }

        composable(Screen.Register.route) { RegisterScreen(navController) }

        composable(Screen.Home.route) { HomeScreen(navController, homeViewModel) }

        composable(Screen.CreateMeetup.route) { CreateMeetupScreen(navController) }

        composable(
            route = "${Screen.DisplayMessagingForm.route}/{newMessage}",
            arguments = listOf(navArgument("newMessage") { defaultValue = "No Message" })
        ) { backStackEntry ->
            val newMessage = backStackEntry.arguments?.getString("newMessage") ?: "No Message"
            DisplayMessagingForm(
                navController = navController,
                newMessage = newMessage,
                messageViewModel = messageViewModel
            )
        }

        composable(
            route = "${Screen.Message.route}/{message}",
            arguments = listOf(navArgument("message") { defaultValue = "Default Message" })
        ) { backStackEntry ->
            val message = backStackEntry.arguments?.getString("message") ?: "Default Message"
            MessageScreen(
                navController = navController,
                message = message,
                messageViewModel = messageViewModel
            )
        }

        composable(
            route = "${Screen.CurrentLocation.route}/{latitude}/{longitude}",
            arguments = listOf(
                navArgument("latitude") { type = NavType.StringType },
                navArgument("longitude") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull() ?: 0.0
            val longitude = backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull() ?: 0.0
            CurrentLocationScreen(navController = navController, latitude = latitude, longitude = longitude)
        }

        composable(
            route = "${Screen.MeetupLocation.route}/{latitude}/{longitude}",
            arguments = listOf(
                navArgument("latitude") { type = NavType.StringType },
                navArgument("longitude") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull() ?: 0.0
            val longitude = backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull() ?: 0.0
            MeetupLocationScreen(navController = navController, latitude = latitude, longitude = longitude)
        }

        composable(Screen.MapLocation.route) { MapLocationScreen(navController) }

        composable(
            route = "${Screen.Message.route}/{meetupName}/{course}/{date}/{time}/{latitude}/{longitude}",
            arguments = listOf(
                navArgument("meetupName") { type = NavType.StringType },
                navArgument("course") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("latitude") { type = NavType.StringType },
                navArgument("longitude") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val meetupName = backStackEntry.arguments?.getString("meetupName") ?: "No Name"
            val course = backStackEntry.arguments?.getString("course") ?: "No Course"
            val date = backStackEntry.arguments?.getString("date") ?: "No Date"
            val time = backStackEntry.arguments?.getString("time") ?: "No Time"
            val latitude = backStackEntry.arguments?.getString("latitude") ?: "0.0"
            val longitude = backStackEntry.arguments?.getString("longitude") ?: "0.0"

            MessageScreen(
                navController = navController,
                message = "Meetup: $meetupName\nCourse: $course\nDate: $date\nTime: $time\nLocation: $latitude, $longitude",
                messageViewModel = messageViewModel
            )
        }


    }


}
