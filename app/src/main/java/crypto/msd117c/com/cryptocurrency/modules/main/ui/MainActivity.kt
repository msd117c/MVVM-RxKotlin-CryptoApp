package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.databinding.MainActivityBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var lifeCycle: MainLifeCycle
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    }

    fun getBinding(): MainActivityBinding {
        return mainActivityBinding
    }
}
