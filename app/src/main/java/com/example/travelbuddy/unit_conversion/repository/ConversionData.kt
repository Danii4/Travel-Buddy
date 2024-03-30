package com.example.travelbuddy.unit_conversion.repository

import com.example.travelbuddy.unit_conversion.model.ScreenData
import com.example.travelbuddy.unit_conversion.model.ScreenData.ConvMethod.ConvFunction
import com.example.travelbuddy.unit_conversion.model.ScreenData.ConvMethod.Equiv
import com.example.travelbuddy.unit_conversion.model.ScreenData.ConvValue.Default
import com.example.travelbuddy.unit_conversion.model.ScreenType


fun getDefaultConversionData(): ScreenData.ConversionData {
    return ScreenData.ConversionData(
        screenType = ScreenType.LENGTHS,
        inputAmount = "0",
        outputAmount = "0",
        inputData = Default(conv = Equiv(0.0), label="default"),
        outputData = Default(conv = Equiv(0.0), label="default"),
        listOfData = listOf(Default(conv = Equiv(0.0), label="default")),
    )
}

fun getLengthConversionData(): ScreenData.ConversionData {
    // Store everything in terms of amountEquivalent = mm
    val dataList: List<ScreenData.ConvValue> = listOf(
        Default(conv = Equiv(  1.0), label="Millimeter (mm)"), // Millimeter
        Default(conv = Equiv (10.0), label="Centimeter (cm)"), // Centimeter
        Default(conv = Equiv (1000.0), label="Meter (m)"), // Meter
        Default(conv = Equiv (1000000.0), label="Kilometer (km)"), // Kilometer
        Default(conv = Equiv (25.4), label="Inch (in)"), // Inch
        Default(conv = Equiv (304.8), label="Foot (ft)"), // Foot
        Default(conv = Equiv (914.4), label="Yard (yd)"), // Yard
        Default(conv = Equiv (1609344.0), label="Mile (mi)"), // Mile
        Default(conv = Equiv (5029200.0), label="League"), // League
        Default(conv = Equiv (201168.0), label="Furlong"), // Furlong
        Default(conv = Equiv (20116.8), label="Chain"), // Chain
        Default(conv = Equiv (1852000.344), label="Nautical Mile (nmi)"), // Nautical Mile
        Default(conv = Equiv (5029.2), label="Rod"), // Rod
        Default(conv = Equiv (201.168), label="Link"), // Link
    )

    return ScreenData.ConversionData(
        screenType = ScreenType.LENGTHS,
        inputAmount = "0",
        outputAmount = "0",
        inputData = dataList[0],
        outputData = dataList[1],
        listOfData = dataList,
    )
}

fun getTemperatureConversionData(): ScreenData.ConversionData {
    // Conversion functions assuming Kelvin as the base unit
    fun celsiusToKelvin(c: Double): Double = c + 273.15
    fun fahrenheitToKelvin(f: Double): Double = (f - 32) * 5/9 + 273.15
    fun rankineToKelvin(r: Double): Double = r * 5/9
    fun kelvinToCelsius(k: Double): Double = k - 273.15
    fun kelvinToFahrenheit(k: Double): Double = (k - 273.15) * 9/5 + 32
    fun kelvinToRankine(k: Double): Double = k * 9/5
    fun kelvinToKelvin(k: Double): Double = k


    val dataList: List<ScreenData.ConvValue> = listOf(
        Default(conv = ConvFunction(forwardConv = ::celsiusToKelvin, backwardConv = ::kelvinToCelsius), label="Celcius"),
        Default(conv = ConvFunction(forwardConv = ::fahrenheitToKelvin, backwardConv = ::kelvinToFahrenheit), label="Farenheit"),
        Default(conv = ConvFunction(forwardConv = ::rankineToKelvin, backwardConv = ::kelvinToRankine), label="Rankine"),
        Default(conv = ConvFunction(forwardConv = ::kelvinToKelvin, backwardConv = ::kelvinToKelvin), label="Kelvin"),
    )

    return ScreenData.ConversionData(
        screenType = ScreenType.TEMPERATURE,
        inputAmount = "0",
        outputAmount = "32",
        inputData = dataList[0],
        outputData = dataList[1],
        listOfData = dataList,
    )
}

fun getWeightConversionData(): ScreenData.ConversionData {
    // Store everything in terms of amountEquivalent = grams
    val dataList: List<ScreenData.ConvValue> = listOf(
        Default(conv = Equiv(1.0), label="Grams (g)"), // Gram
        Default(conv = Equiv(1000.0), label="Kilogram (kg)"), // Kilogram
        Default(conv = Equiv(1000000.0), label="Metric Ton (t)"), // Metric Ton
        Default(conv = Equiv(0.001), label="Milligram (mg)"), // Milligram
        Default(conv = Equiv(28.3495), label="Ounce (oz)"), // Ounce
        Default(conv = Equiv(453.592), label="Pound (lb)"), // Pound
        Default(conv = Equiv(6350.29), label="Stone (st)"), // Stone
        Default(conv = Equiv(907184.74), label="US Ton (us ton)"), // US Ton
        Default(conv = Equiv(907184.74), label="Imperial Ton (imp ton)"), // Imperial Ton
        Default(conv = Equiv(31.1035), label="Troy Ounce (oz t)"), // Troy Ounce
        Default(conv = Equiv(1.55517), label="Pennyweight (dwt)"), // Pennyweight
        Default(conv = Equiv(0.0647989), label="Grain (gr)"), // Grain
        Default(conv = Equiv(100000.0), label="Metric Quintal (q)"), // Metric Quintal
        Default(conv = Equiv(0.2), label="Carat (ct)"), // Carat
    )

    return ScreenData.ConversionData(
        screenType = ScreenType.WEIGHT,
        inputAmount = "0",
        outputAmount = "0",
        inputData = dataList[0],
        outputData = dataList[1],
        listOfData = dataList,
    )
}

fun getVolumeConversionData(): ScreenData.ConversionData {
    // Store everything in terms of amountEquivalent = mm
    val dataList: List<ScreenData.ConvValue> = listOf(
        Default(conv = Equiv(1.0), label="Cubic Millimeter (mm)"),// Cubic Millimeter
        Default(conv = Equiv(1000.0), label="Cubic Centimeter (cm³)"), // Cubic Centimeter
        Default(conv = Equiv(1000.0), label="Milliliter (mL)"), // Milliliter
        Default(conv = Equiv(1000000.0), label="Liter (L)"), // Liter
        Default(conv = Equiv(1000000000.0), label="Cubic Meter (m³)"), // Cubic Meter
        Default(conv = Equiv(16387.064), label="Cubic Inch (in³)"), // Cubic Inch
        Default(conv = Equiv(28316846.592), label="Cubic Foot (ft³)"), // Cubic Foot
        Default(conv = Equiv(764554857.984), label="Cubic Yard (yd³)"), // Cubic Yard
        Default(conv = Equiv(4928.92), label="US tsp"), // US Teaspoon
        Default(conv = Equiv(14786.764), label="US tbsp"), // US Tablespoon
        Default(conv = Equiv(236588.236), label="US cup"), // US Cup
        Default(conv = Equiv(29573.53), label="US fl oz"), // US Fluid Ounce
        Default(conv = Equiv(3785411.784), label="US gal"), // US Gallon
        Default(conv = Equiv(5919.39), label="UK tsp"), // Imperial Teaspoon
        Default(conv = Equiv(17758.2), label="UK tbsp"), // Imperial Tablespoon
        Default(conv = Equiv(28413.1), label="UK fl oz"), // Imperial Fluid Ounce
        Default(conv = Equiv(250000.0), label="UK cup"), // Imperial Cup (approximate for recipes)
        Default(conv = Equiv(568261.0), label="UK pt"), // Imperial Pint
        Default(conv = Equiv(1136522.0), label="UK qt"), // Imperial Quart
        Default(conv = Equiv(4546090.0), label="UK gal"), // Imperial Gallon
    )

    return ScreenData.ConversionData(
        screenType = ScreenType.VOLUME,
        inputAmount = "0",
        outputAmount = "0",
        inputData = dataList[0],
        outputData = dataList[1],
        listOfData = dataList,
    )
}
