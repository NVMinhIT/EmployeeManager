package com.daovu65.employeemanager.ui.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.daovu65.employeemanager.InjectionUtil
import com.daovu65.employeemanager.R
import com.daovu65.employeemanager.ui.MainAdapter
import com.daovu65.employeemanager.ui.edit.EditProfileActivity
import com.daovu65.employeemanager.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val BUNDLE_PROFILE_ID = "BUNDLE_PROFILE_ID"
        const val BUNDLE_EDIT_PROFILE = "BUNDLE_EDIT_PROFILE"
        const val BUNDLE_ADD_NEW = "BUNDLE_ADD_NEW"
    }

    private lateinit var viewModel: MainViewModel
    lateinit var viewModelFactory: MainVMFactory

    private lateinit var mAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        InjectionUtil.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = viewModelFactory.create(MainViewModel::class.java)

        recycler_container.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mAdapter = MainAdapter(this@MainActivity) {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                intent.putExtra(BUNDLE_PROFILE_ID, mAdapter.getEmployeeAt(it).id)
                startActivity(intent)
            }
            adapter = mAdapter
            viewModel.getAllEmployee()
            viewModel.listEmployee.observe(this@MainActivity, Observer {
                mAdapter.submitValue(it)
            })
        }

        btn_add_new.setOnClickListener {
            val intent = Intent(this@MainActivity, EditProfileActivity::class.java)
            intent.putExtra(BUNDLE_EDIT_PROFILE, BUNDLE_ADD_NEW)
            startActivity(intent)
        }
    }
}