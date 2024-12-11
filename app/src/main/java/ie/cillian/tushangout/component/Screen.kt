package ie.cillian.tushangout.component

sealed class Screen(val route: String) {
    object Startup : Screen("startup")
    object Login : Screen("login")
    object Register : Screen("register")

}