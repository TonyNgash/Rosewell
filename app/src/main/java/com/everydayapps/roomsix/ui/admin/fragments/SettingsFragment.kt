package com.everydayapps.roomsix.ui.admin.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.everydayapps.roomsix.R
import com.everydayapps.roomsix.vm.SingletonTransaction


class SettingsFragment : Fragment() {

    lateinit var change_password_btn : Button
    lateinit var currentPasswordDialog: Dialog
    lateinit var newPasswordDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SingletonTransaction.initObj(requireContext())

        change_password_btn = view.findViewById<Button>(R.id.change_password_btn)

        change_password_btn.setOnClickListener {
            currentPassword()
        }
    }
    fun currentPassword(){
        currentPasswordDialog = Dialog(requireContext())
        currentPasswordDialog.setContentView(R.layout.current_password_dialog)
        currentPasswordDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val current_password = currentPasswordDialog.findViewById<EditText>(R.id.current_password)
        val cancelCurrentPassword = currentPasswordDialog.findViewById<Button>(R.id.cancelCurrentPassword)
        val confirmCurrentPassword = currentPasswordDialog.findViewById<Button>(R.id.confirmCurrentPassword)

        cancelCurrentPassword.setOnClickListener {
            currentPasswordDialog.dismiss()
        }
        confirmCurrentPassword.setOnClickListener {
            val currentPassword = SingletonTransaction.getPassword()
            val givenPassword = current_password.text.toString()
            if(currentPassword == givenPassword){
                newPassword()
            }else{
                Toast.makeText(requireContext(),"WRONG Password! Please Input The Current Password",Toast.LENGTH_LONG).show()
            }
            currentPasswordDialog.dismiss()
        }

        currentPasswordDialog.show()
    }

    fun newPassword(){
        newPasswordDialog = Dialog(requireContext())
        newPasswordDialog.setContentView(R.layout.new_password_dialog)
        newPasswordDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val new_password = newPasswordDialog.findViewById<EditText>(R.id.new_password)
        val cancelNewPassword = newPasswordDialog.findViewById<Button>(R.id.cancelNewPassword)
        val saveNewPassword = newPasswordDialog.findViewById<Button>(R.id.saveNewPassword)

        cancelNewPassword.setOnClickListener {
            newPasswordDialog.dismiss()
        }
        saveNewPassword.setOnClickListener {
            val setPassword = new_password.text.toString()
            SingletonTransaction.setPassword(setPassword)
            newPasswordDialog.dismiss()
        }

        newPasswordDialog.show()
    }


}