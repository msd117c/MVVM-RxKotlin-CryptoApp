package crypto.msd117c.com.cryptocurrency.util.extensions

import java.math.RoundingMode
import java.text.DecimalFormat
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
        var inputNum = this
        val spittedNub = this.split(".")
        var decimalNum = ""
        if (spittedNub.size == 2) {
            inputNum = spittedNub[0]
            decimalNum = "." + spittedNub[1]
        }

        val inputDouble = inputNum.toDouble()
        val myFormatter = DecimalFormat("###,###")
        val output = myFormatter.format(inputDouble)

        return output + decimalNum
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