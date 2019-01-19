package crypto.msd117c.com.cryptocurrency.util.extensions

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


val String?.comafy: String
    get() {
        var inputNum = this
        val splittedNum = this!!.split(",")
        var decimalNum = ""
        if (splittedNum.size == 2) {
            inputNum = splittedNum[0]
            decimalNum = "." + splittedNum[1]
        }

        val inputDouble = inputNum!!.toDouble()
        val myFormatter = DecimalFormat("###,###")
        val output = myFormatter.format(inputDouble)

        return output + decimalNum
    }