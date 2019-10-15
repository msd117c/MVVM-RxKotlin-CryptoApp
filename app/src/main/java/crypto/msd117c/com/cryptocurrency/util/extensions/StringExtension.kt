package crypto.msd117c.com.cryptocurrency.util.extensions

import crypto.msd117c.com.cryptocurrency.util.GlobalValues
import java.math.RoundingMode
import java.text.DecimalFormat

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
        if (this == "N/A") return this
        var inputNum = this
        val spittedNub = this!!.split(GlobalValues.decimalSeparator)
        var decimalNum = ""
        if (spittedNub.size == 2) {
            inputNum = spittedNub[0]
            decimalNum = GlobalValues.decimalSeparator + spittedNub[1]
        }

        val inputDouble = inputNum!!.toDouble()
        val myFormatter = DecimalFormat("###,###")
        val output = myFormatter.format(inputDouble)

        return output + decimalNum
    }