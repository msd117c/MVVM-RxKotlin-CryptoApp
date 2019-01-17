package crypto.msd117c.com.cryptocurrency.util

import java.math.RoundingMode
import java.text.DecimalFormat

val String?.normalize: String
    get() {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        val number = this!!.toDouble()
        return df.format(number) }