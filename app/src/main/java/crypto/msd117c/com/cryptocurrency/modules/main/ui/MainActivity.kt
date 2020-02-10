package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import crypto.msd117c.com.cryptocurrency.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

}
