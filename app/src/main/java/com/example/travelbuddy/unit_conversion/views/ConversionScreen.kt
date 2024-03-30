package com.example.travelbuddy.unit_conversion.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.travelbuddy.unit_conversion.model.ScreenColor
import com.example.travelbuddy.unit_conversion.model.ScreenData
import com.example.travelbuddy.unit_conversion.model.getColors
import com.example.travelbuddy.unit_conversion.model.getTitle
import java.text.DecimalFormat

@Composable
fun CustomDropdownSelector(
    isVisible: Boolean,
    selectedItem: String,
    bgColor: Color,
    scrollBarBgColor: Color,
    itemText: List<String>,
    itemIcon: List<String?>,
    onSelect: (item: String) -> Unit,
    hideDropdown: () -> Unit,
){
    val scrollState = rememberScrollState()

    Row(modifier = Modifier.fillMaxSize().background(bgColor)) {
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
                        .heightIn(max = 300.dp)
                        .verticalScroll(state = scrollState)
                        .padding(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(bgColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.background(scrollBarBgColor).padding(20.dp)
                    ){
                    itemText.onEachIndexed { index, textLabel ->

                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .decoderFactory(SvgDecoder.Factory())
                                .data(itemIcon[index])
                                .size(Size(height=20, width=10))
                                .build()
                        )

                        val bgColor = if(textLabel == selectedItem) Color.White else bgColor
                        if (index != 0) {
                            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                        }
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(bgColor)
                                .padding(vertical = 20.dp, horizontal = 5.dp)
                                .fillMaxWidth()
                                .clickable { onSelect(itemText[index]) },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            itemIcon[index]?.let{
                                Image(
                                    painter = painter,
                                    contentDescription = textLabel,
                                    modifier = Modifier.size(40.dp).padding(horizontal = 10.dp)
                                )
                            }
                            Text(
                                text = textLabel,
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
}

@Composable
fun ConversionRowItem(
    amount: String,
    label: String,
    icon: String?,
    buttonColor: Color,
    textColor: Color,
    updateAmount: (String) -> Unit,
    onTypeClicked: () -> Unit,
){
    val deciFormat = DecimalFormat("0.#")
    val textFieldDisp = if(amount.toDoubleOrNull() != null){
        deciFormat.format(amount.toDouble())
    } else { amount }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(icon)
            .size(Size(height=20, width=10))
            .build()
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)),
            value = textFieldDisp,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 28.sp),
            shape = OutlinedTextFieldDefaults.shape,
            singleLine = true,
            maxLines = 1,
            onValueChange = { change -> updateAmount(change) },
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                .background(buttonColor)
                .padding(vertical = 25.dp, horizontal = 10.dp)
                .clickable { onTypeClicked() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ) {
            icon?.let{
                Image(
                    painter = painter,
                    contentDescription = label,
                    modifier = Modifier.size(40.dp).padding(horizontal = 10.dp)
                )
            }
            Text(
                text = label,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
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
    val screenColors: ScreenColor = data.screenType.getColors()
    val title: String = data.screenType.getTitle()

    Surface {
        var isDropdownInput = remember { mutableStateOf(false) }
        var selectedItem = remember { mutableStateOf("") }
        var isDropdownVisible = remember { mutableStateOf(false) }

        // Pressing back should go to selection page.
        BackHandler(enabled = true) {  onBackPress() }

        val itemText: List<String> = data.listOfData.map { it.label }
        val itemIcon: List<String?> = data.listOfData.map {
            (it as? ScreenData.ConvValue.Country)?.icon
        }

        CustomDropdownSelector(
            selectedItem = selectedItem.value,
            itemText = itemText,
            itemIcon = itemIcon,
            bgColor = screenColors.fgColor,
            scrollBarBgColor = screenColors.bgbgColor,
            onSelect = { selected ->
                if(isDropdownInput.value) { updateInputType(selected) } else { updateOutputType(selected) }
                isDropdownVisible.value = false
            },
            isVisible = isDropdownVisible.value,
            hideDropdown = { isDropdownVisible.value = false }
        )
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxHeight()
                .background(screenColors.fgColor),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = screenColors.bgColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp),
            )
            Column (
                modifier = Modifier
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConversionRowItem(
                    amount = data.inputAmount,
                    label = data.inputData.label,
                    icon = (data.inputData as? ScreenData.ConvValue.Country)?.icon,
                    buttonColor = screenColors.bgColor,
                    textColor = screenColors.textColor,
                    updateAmount = updateInputAmount,
                    onTypeClicked = {
                        isDropdownInput.value = true
                        selectedItem.value = data.inputData.label
                        isDropdownVisible.value = true
                    }
                )
                HorizontalDivider(thickness = 2.dp)
                ConversionRowItem(
                    amount = data.outputAmount,
                    label = data.outputData.label,
                    icon = (data.outputData as? ScreenData.ConvValue.Country)?.icon,
                    buttonColor = screenColors.bgColor,
                    textColor = screenColors.textColor,
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