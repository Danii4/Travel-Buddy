import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    return Surface {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Home Screen",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Column {
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Welcome to TravelBuddy",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.padding(20.dp),
                text = "SignUp",
                fontSize = 35.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Username",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Email Address",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Main Purpose of Using the App",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Password",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Signup",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

class Verifylogin {
//    val response = OkHttpClient().newCall(request).execute()
//    val json = response.body.string()
}
