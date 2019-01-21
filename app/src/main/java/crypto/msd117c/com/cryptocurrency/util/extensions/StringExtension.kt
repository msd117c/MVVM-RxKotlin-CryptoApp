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


val String?.comafy: String
    get() {
        var inputNum = this
        val splittedNum = this!!.split(GlobalValues.decimalSeparator)
        var decimalNum = ""
        if (splittedNum.size == 2) {
            inputNum = splittedNum[0]
            decimalNum = GlobalValues.decimalSeparator + splittedNum[1]
        }

        val inputDouble = inputNum!!.toDouble()
        val myFormatter = DecimalFormat("###${GlobalValues.thousandSeparator}###")
        val output = myFormatter.format(inputDouble)

        return output + decimalNum
    }