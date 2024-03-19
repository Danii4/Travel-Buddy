import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    return Surface {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ){
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Welcome to TravelBuddy",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Terms and Conditions of Use: by continuing to use our platform you agree to the privacy policies listed below.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
              Text(
                modifier = Modifier.padding(20.dp),
                text = "Your personal information will be stored in our system and your spending habits will be shared with those that you choose to share with.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                Text(
                modifier = Modifier.padding(20.dp),
                text = "Your browsing preferences and data will also be stored in the system",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
