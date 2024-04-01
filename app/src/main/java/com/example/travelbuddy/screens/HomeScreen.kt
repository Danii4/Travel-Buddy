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
            //the title and welcome message for our app name
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Welcome to TravelBuddy",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            //provides the user with the terms and conditions of our app, to improve trust and transparency
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
                Text(
                modifier = Modifier.padding(20.dp),
                text = "By using our app, you agree to all the terms and conditions listed.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                 Text(
                modifier = Modifier.padding(20.dp),
                text = "Frequently Asked Questions",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
                 //FAQ section
                  Text(
                modifier = Modifier.padding(20.dp),
                text = "Do I have to be connected to the internet to view my data?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                     Text(
                modifier = Modifier.padding(20.dp),
                text = "Limited content will be visible without full internet access on the app. Please connect to the internet",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                      Text(
                modifier = Modifier.padding(20.dp),
                text = "Is it possible to contact someone directly about pricing and questions related to using the app?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                     Text(
                modifier = Modifier.padding(20.dp),
                text = "Yes, for travel agent support or questions relating to a specific itinerary please feel free to contact us at inquiries@travelbuddy.com",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                      Text(
                modifier = Modifier.padding(20.dp),
                text = "How often does the pricing of various flights and hotels change?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                     Text(
                modifier = Modifier.padding(20.dp),
                text = "In our system we use real time data connected directly to industry suppliers to receive real time pricing.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                     Text(
                modifier = Modifier.padding(20.dp),
                text = "Do you accept partnership or business for companies in the travel industry.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
                     Text(
                modifier = Modifier.padding(20.dp),
                text = "Yes! If you would like to discuss partner opportunities please contact us at partners@travelbuddy.com",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
