package crypto.msd117c.com.cryptocurrency.util

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