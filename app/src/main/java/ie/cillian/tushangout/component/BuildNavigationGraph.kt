package ie.cillian.tushangout.component

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ie.cillian.tushangout.home.StartupScreen
import ie.cillian.tushangout.authentication.LoginScreen
import ie.cillian.tushangout.authentication.RegisterScreen
import ie.cillian.tushangout.home.HomeViewModel

@Composable
fun BuildNavigationGraph(
    homeViewModel: HomeViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Startup.route) {
        composable(Screen.Startup.route) { StartupScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        // Add more screens as required
    }
}
