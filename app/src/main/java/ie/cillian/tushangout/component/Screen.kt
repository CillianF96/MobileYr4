package ie.cillian.tushangout.component

sealed class Screen(val route: String) {
    object Startup : Screen("startup")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object CreateMeetup : Screen("create_meetup")
    object DisplayMessagingForm : Screen("display_messaging_form")
    object Message : Screen("message")
    object CurrentLocation : Screen("current_location")
    object MapLocation : Screen("map_location")
    object MeetupLocation : Screen("meetup_location")
}
