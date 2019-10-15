package crypto.msd117c.com.cryptocurrency.util.extensions

import java.text.SimpleDateFormat
import java.util.*

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