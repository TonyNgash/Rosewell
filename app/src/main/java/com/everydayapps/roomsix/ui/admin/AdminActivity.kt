package com.everydayapps.roomsix.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.everydayapps.roomsix.ui.staff.MainActivity
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.ui.admin.fragments.*
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {

    lateinit var adminDrawerToggle: ActionBarDrawerToggle
    lateinit var adminDrawerLayout: DrawerLayout
    lateinit var adminNavigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        adminDrawerLayout = findViewById(R.id.admin_drawer)
        adminNavigationView = findViewById(R.id.admin_nav_view)

        adminDrawerToggle = ActionBarDrawerToggle(this,adminDrawerLayout,
            R.string.admin_drawer_open,
            R.string.admin_drawer_close
        )
        adminDrawerLayout.addDrawerListener(adminDrawerToggle)
        adminDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        replaceFragment(DashboardFragment(),"Dashboard")
        adminNavigationView.setNavigationItemSelectedListener{
            it.isChecked =true
            when(it.itemId){
                R.id.dashboard -> replaceFragment(DashboardFragment(),it.title.toString())
                R.id.viewTransactions -> replaceFragment(TransHolderFragment(),it.title.toString())
                R.id.addMembers -> replaceFragment(MemberFragment(),it.title.toString())
                R.id.addServices -> replaceFragment(ServiceFragment(),it.title.toString())
                R.id.settings -> replaceFragment(SettingsFragment(),it.title.toString())
                R.id.logout -> logout()
            }
            true
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.member_transactions_fragment_container)

    }
    private fun logout() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(adminDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dummy() {
        Toast.makeText(this,"This is a dummy", Toast.LENGTH_LONG).show()
    }
    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.admin_fragment_container,fragment)
        fragmentTransaction.commit()
        setTitle(title)
        adminDrawerLayout.closeDrawers()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.member_transactions_fragment_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}