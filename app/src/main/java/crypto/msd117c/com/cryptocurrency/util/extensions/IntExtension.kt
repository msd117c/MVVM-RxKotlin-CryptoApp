package crypto.msd117c.com.cryptocurrency.util.extensions

import java.util.*

val Int?.toDate: String
    get() {
        val cl = Calendar.getInstance()
        cl.timeInMillis = this!!.toLong()
        return "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND)
    }