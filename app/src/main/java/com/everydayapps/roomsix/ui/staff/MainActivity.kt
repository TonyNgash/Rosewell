package com.everydayapps.roomsix.ui.staff

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.ui.admin.AdminActivity
import com.everydayapps.roomsix.vm.SingletonTransaction
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var publicDrawerToggle: ActionBarDrawerToggle
    lateinit var publicDrawerLayout: DrawerLayout
    lateinit var publicNavigationView: NavigationView

    lateinit var loginDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setupActionBarWithNavController(findNavController(R.id.public_fragment_container))

        publicDrawerLayout = findViewById(R.id.public_drawer)
        publicNavigationView = findViewById(R.id.public_nav_view)

        publicDrawerToggle  = ActionBarDrawerToggle(this,publicDrawerLayout,
            R.string.public_drawer_open,
            R.string.public_drawer_close
        )
        publicDrawerLayout.addDrawerListener(publicDrawerToggle)
        publicDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        replaceFragment(HomeFragment(),"Rosewell Beauty Parlour")

        publicNavigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.adminModule -> adminModule()
                R.id.closeApp -> dummy()

            }
            true
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(publicDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun dummy(){
        Toast.makeText(this,"Something", Toast.LENGTH_LONG).show()
    }
    fun adminModule(){
        loginDialog = Dialog(this)
        loginDialog.setContentView(R.layout.login_dialog)
        loginDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val password = loginDialog.findViewById<EditText>(R.id.login_password)
        val cancelBtn = loginDialog.findViewById<Button>(R.id.cancelLogin)
        val loginBtn = loginDialog.findViewById<Button>(R.id.doLogin)

        cancelBtn.setOnClickListener{
            loginDialog.dismiss()
        }
        loginBtn.setOnClickListener{
            loginDialog.dismiss()
            performLogin(password.text.toString())
        }

        loginDialog.show()
    }
    private fun performLogin(password : String) {
        SingletonTransaction.initObj(this)
        val currentPassword = SingletonTransaction.getPassword()
        if(password == currentPassword){
            val i = Intent(this, AdminActivity::class.java)
            startActivity(i)
            finish()
        }else{
            Toast.makeText(this,"Wrong Password Please Try Again",Toast.LENGTH_LONG).show()
        }
    }
    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.public_fragment_container,fragment)
        fragmentTransaction.commit()
        setTitle(title)
        publicDrawerLayout.closeDrawers()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.public_fragment_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}