package crypto.msd117c.com.cryptocurrency.utils

import java.io.File

object JsonGetter {

    fun getJSON(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

}