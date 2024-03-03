package com.example.travelbuddy.unit_conversion.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.travelbuddy.ui.theme.LightGrey
import com.example.travelbuddy.unit_conversion.ScreenData
import java.text.DecimalFormat

@Composable
fun CustomDropdownSelector(
    isVisible: Boolean,
    selectedItem: String,
    items: List<String>,
    onSelect: (item: String) -> Unit,
    hideDropdown: () -> Unit,
){
    val scrollState = rememberScrollState()

    Row(modifier = Modifier.fillMaxSize()) {
        if (isVisible) {
            Popup(
                alignment = Alignment.Center,
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                onDismissRequest = { hideDropdown() }
            ) {
                Column(
                    modifier = Modifier
                        .heightIn(max = 180.dp)
                        .verticalScroll(state = scrollState)
                        .padding(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                        .border(2.dp, Color.Black),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ){

                    }
                    items.onEachIndexed { index, item ->
                        val bgColor = if(item == selectedItem) Color.LightGray else Color.White
                        if (index != 0) {
                            Divider(thickness = 1.dp, color = Color.LightGray)
                        }
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(bgColor)
                                .padding(vertical = 20.dp, horizontal = 5.dp)
                                .fillMaxWidth()
                                .clickable { onSelect(items[index]) },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = item,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionRowItem(
    amount: String,
    label: String,
    updateAmount: (String) -> Unit,
    onTypeClicked: () -> Unit,
){
    val deciFormat = DecimalFormat("0.#")
    val textFieldDisp = if(amount.toDoubleOrNull() != null){
        deciFormat.format(amount.toDouble())
    } else { amount }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(10.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)),
            value = textFieldDisp,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 28.sp),
            shape = TextFieldDefaults.outlinedShape,
            singleLine = true,
            maxLines = 1,
            onValueChange = { change -> updateAmount(change) },
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                .background(LightGrey)
                .padding(vertical = 25.dp, horizontal = 10.dp)
                .clickable { onTypeClicked() }
            ) {
            Text(
                text = label,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Composable
fun ConversionScreen(
    data: ScreenData.ConversionData,
    updateInputAmount: (String) -> Unit,
    updateOutputAmount: (String) -> Unit,
    updateInputType: (String) -> Unit,
    updateOutputType: (String) -> Unit,
    onBackPress: () -> Unit
){

    Surface {

        var isDropdownInput = remember { mutableStateOf(false) }
        var selectedItem = remember { mutableStateOf("") }
        var tempStrings: List<String> = data.listOfData.map { it.label }
        var isDropdownVisible = remember { mutableStateOf(false) }

        // Pressing back should go to selection page.
        BackHandler(enabled = true) {  onBackPress() }

        CustomDropdownSelector(
            selectedItem = selectedItem.value,
            items = tempStrings,
            onSelect = { selected ->
                if(isDropdownInput.value) { updateInputType(selected) } else { updateOutputType(selected) }
                isDropdownVisible.value = false
            },
            isVisible = isDropdownVisible.value,
            hideDropdown = { isDropdownVisible.value = false }
        )

        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Temperature Converter:",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp),
            )

            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 20.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConversionRowItem(
                    amount = data.inputAmount,
                    label = data.inputData.label,
                    updateAmount = updateInputAmount,
                    onTypeClicked = {
                        isDropdownInput.value = true
                        selectedItem.value = data.inputData.label
                        isDropdownVisible.value = true
                    }
                )
                Divider(thickness = 2.dp)
                ConversionRowItem(
                    amount = data.outputAmount,
                    label = data.outputData.label,
                    updateAmount = updateOutputAmount,
                    onTypeClicked = {
                        isDropdownInput.value = false
                        selectedItem.value = data.outputData.label
                        isDropdownVisible.value = true
                    }
                )

            }

        }
    }

}