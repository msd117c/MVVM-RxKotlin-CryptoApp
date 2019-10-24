package crypto.msd117c.com.cryptocurrency.utils.json

import android.content.Context
import androidx.annotation.VisibleForTesting
import java.io.File

@VisibleForTesting
class JsonGetter {

    fun getJSON(path: String, context: Context): String {
        // Load the JSON response.json
        val assetManager = context.assets
        val file = assetManager.open(path)
        return String(file.readBytes())
    }

    fun getJSON(path: String): String {
        // Load the JSON response.json
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}