package converter

fun main(args: Array<String>) {
    val baseConverter = BaseConverter()
    baseConverter.getUserInput()
}


class BaseConverter {


    private val meterMeasure = arrayOf("m","meter","meters")
    private val kiloMeterMeasure = arrayOf("km","kilometer","kilometers")
    private val cmMeasure = arrayOf("cm","centimeter","centimeters")
    private val mmMeasure = arrayOf("mm","millimeter","millimeters")
    private val mileMeasure = arrayOf("mi","mile","miles")
    private val yardMeasure = arrayOf("yd","yard","yards")
    private val ftMeasure = arrayOf("ft","foot","feet")
    private val inchMeasure = arrayOf("in","inch","inches")

    private val grams = arrayOf("g","gram","grams")
    private val kilograms = arrayOf("kg","kilogram","kilograms")
    private val milligrams = arrayOf("mg","milligram","milligrams")
    private val pounds = arrayOf("lb","pound","pounds","Pound")
    private val ounces = arrayOf("oz","ounce","ounces")

    private val celsius = arrayOf("degree Celsius","degrees Celsius","celsius","dc","c","C")
    private val fahrenheit = arrayOf("degree Fahrenheit","degrees Fahrenheit","fahrenheit","df","f","F")
    private val kelvins = arrayOf("kelvin","kelvins","k","K")

    private val temperatures = celsius + fahrenheit + kelvins
    private val weights = grams + kilograms + milligrams + pounds + ounces
    private val lengths = meterMeasure + kiloMeterMeasure + cmMeasure + mmMeasure + mileMeasure + yardMeasure + ftMeasure + inchMeasure


    private fun getMeasure1(unit: Double,measure: String): Pair<Double,String>? {
        return when {
            exists(meterMeasure,measure) -> 1.0 to if (unit ==  1.0 ) meterMeasure[1]else meterMeasure[2]
            exists(kiloMeterMeasure,measure)-> 1000.0 to if (unit ==  1.0 ) kiloMeterMeasure[1]else kiloMeterMeasure[2]
            exists(cmMeasure,measure) -> 0.01 to if (unit ==  1.0 ) cmMeasure[1]else cmMeasure[2]
            exists(mmMeasure,measure) -> 0.001 to if (unit ==  1.0 ) mmMeasure[1]else mmMeasure[2]
            exists(mileMeasure,measure) -> 1609.35 to if (unit ==  1.0 ) mileMeasure[1]else mileMeasure[2]
            exists(yardMeasure,measure) -> 0.9144 to if (unit ==  1.0 ) yardMeasure[1]else yardMeasure[2]
            exists(ftMeasure,measure) -> 0.3048 to if (unit ==  1.0 ) ftMeasure[1]else ftMeasure[2]
            exists(inchMeasure,measure) -> 0.0254 to if (unit ==  1.0 ) inchMeasure[1]else inchMeasure[2]
            exists(grams,measure) -> 1.0 to if (unit ==  1.0 ) grams[1]else grams[2]
            exists(kilograms,measure)-> 1000.0 to if (unit ==  1.0 ) kilograms[1]else kilograms[2]
            exists(milligrams,measure) -> 0.001 to if (unit ==  1.0 ) milligrams[1]else milligrams[2]
            exists(pounds,measure) -> 453.592 to if (unit ==  1.0 ) pounds[1]else pounds[2]
            exists(ounces,measure) -> 28.3495 to if (unit ==  1.0 ) ounces[1]else ounces[2]
            exists(celsius,measure) -> 1.1 to if (unit == 1.0) celsius[0] else celsius[1]
            exists(fahrenheit,measure) -> 1.0 to if (unit == 1.0) fahrenheit[0] else fahrenheit[1]
            exists(kelvins,measure) -> 1.0 to if (unit == 1.0) kelvins[0] else kelvins[1]
            else -> null
        }
    }


    private fun exists(args: Array<String>, string: String) : Boolean {
        return args.any { it.equals(string,ignoreCase = true) }
    }

     fun getUserInput() {

         val regex = Regex("[0-9.-]+ ?([a-zA-Z]+)? [a-zA-Z]+ [a-zA-Z]+ ?([a-zA-Z]+)? [a-zA-Z]+")
         val regex0 = Regex("[0-9.-]+ ([a-zA-Z]+)? [a-zA-Z]+ [a-zA-Z]+ ([a-zA-Z]+)? [a-zA-Z]+")
         val regex1 = Regex("[0-9.-]+ [a-zA-Z]+ [a-zA-Z]+ ([a-zA-Z]+)? [a-zA-Z]+")
         val regex2 = Regex("[0-9.-]+ ([a-zA-Z]+)? [a-zA-Z]+ [a-zA-Z]+ [a-zA-Z]+")
         val regex3 = Regex("[0-9.-]+ [a-zA-Z]+ [a-zA-Z]+ [a-zA-Z]+")
         val regexTo = Regex("( to | in )")



         while (true) {
             println("Enter what you want to convert (or exit):")
             val userInput = readln()

             if (userInput == "exit") break
             if (!userInput.matches(regex)) {
                 println("Parse error")
                 continue
             }
             var currentMeasure = ""; var targetMeasure = ""
             val unit : Double = userInput.split(" ").first().toDouble()

             if (userInput.matches(regex0)) {
                 val newValue = userInput.split(regexTo).first().split(" ")
                 currentMeasure = "${newValue[1]} ${newValue[2]}"
                 targetMeasure = userInput.split(regexTo).last()
             }

             if (userInput.matches(regex1)) {
                 currentMeasure = userInput.split(regexTo).first().split(" ").last()
                 targetMeasure = userInput.split(regexTo).last()
             }
             else if (userInput.matches(regex2)) {
                 val newValue = userInput.split(regexTo).first().split(" ")
                 currentMeasure = "${newValue[1]} ${newValue[2]}"
                 targetMeasure = userInput.split(regexTo).last()
             }

             else if (userInput.matches(regex3)) {
                 currentMeasure = userInput.split(" ")[1]
                 targetMeasure = userInput.split(" ").last()
             }


             val curMeasureImpossible = !exists(weights,currentMeasure) && !exists(lengths,currentMeasure) && !exists(temperatures,currentMeasure)
             val tarMeasureImpossible = !exists(weights,targetMeasure) && !exists(lengths,targetMeasure) && !exists(temperatures,targetMeasure)

             if (curMeasureImpossible || tarMeasureImpossible) {
                 val currentProperty = getMeasure1(1.2,currentMeasure)?.second
                 val targetProperty = getMeasure1(1.2,targetMeasure)?.second

                 println("Conversion from ${if (curMeasureImpossible) "???" 
                 else currentProperty } to ${if (tarMeasureImpossible) "???" 
                 else targetProperty } is impossible")
             }
             else if (exists(weights,currentMeasure) && exists(lengths,targetMeasure)) {
                 val currentProperty = getMeasure1(1.2,currentMeasure)?.second
                 val targetProperty = getMeasure1(1.2,targetMeasure)?.second
                 println("Conversion from $currentProperty to $targetProperty is impossible")
             }
             else if (exists(weights,currentMeasure) && exists(temperatures,targetMeasure)) {
                 val currentProperty = getMeasure1(1.2,currentMeasure)?.second
                 val targetProperty = getMeasure1(1.2,targetMeasure)?.second
                 println("Conversion from $currentProperty to $targetProperty is impossible")
             }
             else if (exists(lengths,currentMeasure) && exists(weights,targetMeasure)) {

                 val currentProperty = getMeasure1(1.2,currentMeasure)?.second
                 val targetProperty = getMeasure1(1.2,targetMeasure)?.second
                 println("Conversion from $currentProperty to $targetProperty is impossible")
             }
             else if (exists(lengths,currentMeasure) && exists(temperatures,targetMeasure)) {
                 val currentProperty = getMeasure1(1.2,currentMeasure)?.second
                 val targetProperty = getMeasure1(1.2,targetMeasure)?.second
                 println("Conversion from $currentProperty to $targetProperty is impossible")
             }
             else if (exists(temperatures,currentMeasure) && exists(weights,targetMeasure)) {
                 val currentProperty = getMeasure1(1.2,currentMeasure)?.second
                 val targetProperty = getMeasure1(1.2,targetMeasure)?.second
                 println("Conversion from $currentProperty to $targetProperty is impossible")
             }
             else if (exists(temperatures,currentMeasure) && exists(lengths,targetMeasure)) {
                 val currentProperty = getMeasure1(1.2,currentMeasure)?.second
                 val targetProperty = getMeasure1(1.2,targetMeasure)?.second
                 println("Conversion from $currentProperty to $targetProperty is impossible")
             }
             else if (exists(lengths,currentMeasure) && exists(lengths,targetMeasure)) {
                 // first change to the meter then to the particular property
                 if (unit < 0) {
                     println("Length shouldn't be negative.")
                     continue
                 }
                 val result = convert(unit,getMeasure1(unit,currentMeasure)?.first!!,getMeasure1(unit,targetMeasure)?.first!!)
                 val currentProperty = getMeasure1(unit,currentMeasure)?.second
                 val targetProperty = getMeasure1(result,targetMeasure)?.second

                 println("$unit $currentProperty is $result $targetProperty")
             }
             else if (exists(weights,currentMeasure) && exists(weights,targetMeasure)) {
                 // first change to the gram then to the particular property
                 if (unit < 0) {
                     println("Weight shouldn't be negative.")
                     continue
                 }
                 val result = convert(unit,getMeasure1(unit,currentMeasure)?.first!!,getMeasure1(unit,targetMeasure)?.first!!)
                 val currentProperty = getMeasure1(unit,currentMeasure)?.second
                 val targetProperty = getMeasure1(result,targetMeasure)?.second
                 println("$unit $currentProperty is $result $targetProperty")
             }
             else if (exists(temperatures,currentMeasure) && exists(temperatures,targetMeasure)) {

                 val temperature = Temperature()
                 var result = 1.0
                 // first convert to
                 if (exists(celsius,currentMeasure) && exists(fahrenheit,targetMeasure)) {
                     result = temperature.celsiusToFahrenheit(unit)
                 }
                 else if (exists(fahrenheit,currentMeasure) && exists(celsius,targetMeasure)){
                     result = temperature.fahrenheitToCelsius(unit)
                 }
                 else if (exists(kelvins,currentMeasure) && exists(celsius,targetMeasure)){
                     result = temperature.kelvinsToCelsius(unit)
                 }
                 else if (exists(celsius,currentMeasure) && exists(kelvins,targetMeasure)) {
                     result = temperature.celsiusToKelvin(unit)
                 }
                 else if (exists(fahrenheit,currentMeasure) && exists(kelvins,targetMeasure)) {
                     result = temperature.fahrenheitToKelvins(unit)
                 }
                 else if (exists(kelvins,currentMeasure) && exists(fahrenheit,targetMeasure)) {
                     result = temperature.kelvinsToFahrenheit(unit)
                 }
                 else if (exists(celsius,currentMeasure) && exists(celsius,targetMeasure)) {
                     result = temperature.fromCelsius(temperature.toCelsius(unit,TemperatureType.CELSIUS),TemperatureType.CELSIUS)
                 }
                 else if (exists(fahrenheit,currentMeasure) && exists(fahrenheit,targetMeasure)) {
                     result = temperature.fromCelsius(temperature.toCelsius(unit,TemperatureType.FAHRENHEIT),TemperatureType.FAHRENHEIT)
                 }
                 else if (exists(kelvins,currentMeasure) && exists(kelvins,targetMeasure)) {
                     result = temperature.fromCelsius(temperature.toCelsius(unit,TemperatureType.KELVIN),TemperatureType.KELVIN)
                 }

                 val currentProperty = getMeasure1(unit,currentMeasure)?.second
                 val targetProperty = getMeasure1(result,targetMeasure)?.second
                 println("$unit $currentProperty is $result $targetProperty")
             }

             println()
         }
    }

    private fun convert(unit: Double,curMeasure: Double,targetMeasure: Double): Double {
        return unit * curMeasure / targetMeasure
    }


}

class Length {

    private val meterMeasure = arrayListOf("m","meter","meters")
    private val kiloMeterMeasure = arrayOf("km","kilometer","kilometers")
    private val cmMeasure = arrayOf("cm","centimeter","centimeters")
    private val mmMeasure = arrayOf("mm","millimeter","millimeters")
    private val mileMeasure = arrayOf("mi","mile","miles")
    private val yardMeasure = arrayOf("yd","yard","yards")
    private val ftMeasure = arrayOf("ft","foot","feet")
    private val inchMeasure = arrayOf("in","inch","inches")

    private val lengths = meterMeasure + kiloMeterMeasure + cmMeasure + mmMeasure + mileMeasure + yardMeasure + ftMeasure + inchMeasure


    private fun convert(unit: Double,curMeasure: Double,targetMeasure: Double): Double {
        return unit * curMeasure / targetMeasure
    }
}


class Weight {

    private val grams = arrayOf("g","gram","grams")
    private val kilograms = arrayOf("kg","kilogram","kilograms")
    private val milligrams = arrayOf("mg","milligram","milligrams")
    private val pounds = arrayOf("lb","pound","pounds")
    private val ounces = arrayOf("oz","ounce","ounces")

    private val weights = grams + kilograms + milligrams + pounds + ounces

    private fun convert(unit: Double,curMeasure: Double,targetMeasure: Double): Double {
        return unit * curMeasure / targetMeasure
    }
}

enum class TemperatureType {CELSIUS,FAHRENHEIT,KELVIN}

class Temperature {

    private val celsius = arrayOf("degree celsius","degrees celsius","celsius","dc","c")
    private val fahrenheit = arrayOf("degree fahrenheit","degrees fahrenheit","df","f")
    private val kelvins = arrayOf("kelvin","kelvins","k")

    private val temperatures = celsius + fahrenheit + kelvins

    fun toCelsius(unit: Double,type: TemperatureType) : Double {

        return when(type) {
            TemperatureType.CELSIUS -> unit
            TemperatureType.FAHRENHEIT -> fahrenheitToCelsius(unit)
            else -> kelvinsToCelsius(unit)
        }
    }

    fun fromCelsius(unit: Double,type: TemperatureType) : Double {
        return when(type) {
            TemperatureType.CELSIUS -> unit
            TemperatureType.FAHRENHEIT -> celsiusToFahrenheit(unit)
            else -> celsiusToKelvin(unit)
        }
    }

    fun celsiusToFahrenheit(celsius: Double) : Double {
        return celsius * 9.0/5.0 + 32
    }

    fun fahrenheitToCelsius(f: Double) : Double {
        return (f-32).toDouble() * 5.0/9.0
    }

    fun kelvinsToCelsius(k: Double) : Double {
        return k - 273.15
    }

    fun celsiusToKelvin(c: Double) : Double {
        return c + 273.15
    }

    fun fahrenheitToKelvins(f: Double) : Double {
        return (f + 459.67) * 5.0/9.0
    }

    fun kelvinsToFahrenheit(k: Double) : Double {
        return k * 9.0/5.0 - 459.67
    }

}