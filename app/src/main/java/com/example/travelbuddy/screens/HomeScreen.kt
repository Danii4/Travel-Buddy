import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelbuddy.screens.BudgetTrackingScreen
import com.example.travelbuddy.expenses.views.ExpenseView
import com.example.travelbuddy.screens.HomeScreen
import com.example.travelbuddy.screens.TranslationScreen
import com.example.travelbuddy.screens.TripPlanningScreen

@Composable
fun HomeScreen() {
    return Surface {
        Text(modifier = modifier.padding(20.dp),
             text = "Home Screen",
             fontSize = 18.sp,
             fontWeight = FontWeight.Medium
            )
        Column{
            Text(
                Modifier = Modifier.padding(20.dp),
                text = "Welcome to TravelBuddy",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                Modifier = Modifier.padding(20.dp),
                text = "SignUp",
                fontSize = 35.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                Modifier = Modifier.padding(20.dp),
                text = "Username",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                Modifier = Modifier.padding(20.dp),
                text = "Email Address",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                Modifier = Modifier.padding(20.dp),
                text = "Main Purpose of Using the App",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                Modifier = Modifier.padding(20.dp),
                text = "Password",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                Modifier = Modifier.padding(20.dp),
                text = "Signup",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            
        }
    }
}

class Verifylogin{
    val response = OkHttpClient().newCall(request).execute()
    val json = response.body.string()
}
