package crypto.msd117c.com.cryptocurrency.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

val String?.normalize: String
    get() {
        return if (this != null && this.isNotBlank()) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            val number = this.toDouble()
            df.format(number)
        } else {
            "N/A"
        }
    }


val String?.comfy: String
    get() {
        if (this == "N/A" || this == null) return "N/A"
        val format = NumberFormat.getCurrencyInstance()
        val numberFormat = (format as DecimalFormat).decimalFormatSymbols
        numberFormat.currencySymbol = ""
        format.decimalFormatSymbols = numberFormat
        format.isDecimalSeparatorAlwaysShown = false
        val number = if (contains(",") && !contains(".")) {
            replace(",", ".").toDouble()
        } else {
            toDouble()
        }
        val result = format.format(number)
        return if (number % 1 == 0.0
        ) {
            if (result.length > 4) {
                result.substring(0, result.length - 4)
            } else {
                result
            }
        } else {
            result
        }
    }

val String?.toDate: String
    get() {
        this?.let {
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sdf.calendar = cal
            cal.time = sdf.parse(it)
            val date = cal.time
            val timeStampFormat = SimpleDateFormat("dd-yyyy-MM HH:mm")
            return timeStampFormat.format(date)
        }
        return "N/A"
    }