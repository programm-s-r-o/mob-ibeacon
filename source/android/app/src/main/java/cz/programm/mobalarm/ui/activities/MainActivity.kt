package cz.programm.mobalarm.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import cz.programm.mobalarm.R
import cz.programm.mobalarm.databinding.ActivityMainBinding
import cz.programm.mobalarm.services.PermissionService
import cz.programm.mobalarm.ui.presenters.MainActivityPresenter
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private val presenter: MainActivityPresenter by inject()
    private val permissionService: PermissionService by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, window.decorView.findViewById(android.R.id.content), false)
        binding.presenter = presenter
        binding.activity = this
        setContentView(binding.root)

        permissionService.checkPermissions(this)
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = presenter.adapter
        presenter.attach(this)
    }

    fun toggleBeaconSearch() {
        permissionService.checkPermissions(this)
        presenter.toggleBeaconSearch()
    }

}