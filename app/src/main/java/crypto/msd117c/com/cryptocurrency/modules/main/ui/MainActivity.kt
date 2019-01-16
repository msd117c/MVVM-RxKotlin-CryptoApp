package crypto.msd117c.com.cryptocurrency.modules.main.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import crypto.msd117c.com.cryptocurrency.R
import crypto.msd117c.com.cryptocurrency.di.factory.ViewModelFactory
import crypto.msd117c.com.cryptocurrency.modules.main.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var lifeCycle: MainLifeCycle
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        lifeCycle = MainLifeCycle(this, this.lifecycle, viewModel)
    }

}
