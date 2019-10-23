package crypto.msd117c.com.cryptocurrency.utils

import java.io.File

class JsonGetter {

    fun getJSON(path: String): String {
        // Load the JSON response.json
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

}