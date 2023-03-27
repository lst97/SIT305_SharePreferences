package com.example.week4_sharepreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

interface ILogin{
    fun validate(username: String, password: String, preference:SharedPreferences) : Boolean
    fun setSharePreference(username: String, password: String, preference:SharedPreferences)
    fun isFound(username: String, password: String,  preference:SharedPreferences): Boolean
}
class Login(name: EditText, password: EditText, login: Button, rememberMe: CheckBox) : ILogin{
    val userNameInput : EditText = name
    val passwordInput : EditText = password
    val loginBtn : Button = login
    val rememberCheckbox : CheckBox = rememberMe

    override fun validate(username: String, password: String, preference:SharedPreferences): Boolean {
        val prefUsername = preference.getString("username", "")
        val prefPassword = preference.getString("password", "")

        if (prefUsername == username && prefPassword == password){
            return true
        }
        return false
    }

    override fun setSharePreference(username: String, password: String, preference:SharedPreferences) {
        val editor = preference.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }

    override fun isFound(username: String, password: String, preference:SharedPreferences): Boolean {
        val username = preference.getString("username", "")
        return username!!.isNotEmpty()
    }

}



class MainActivity : AppCompatActivity() {
    companion object {
        const val USERNAME = "com.example.week4_share_preferences.USERNAME"
        const val PASSWORD = "com.example.week4_share_preferences.PASSWORD"
    }

    private fun startIntent(username: String, password: String){
        val intent = Intent(this, userpage::class.java).apply {
            putExtra(USERNAME, username)
            putExtra(PASSWORD, password)
        }
        startActivity(intent)
    }

    private fun userPage(username: String, password: String) {
        startIntent(username, password)
    }


    private fun userPage(preference: SharedPreferences) {
        val username = preference.getString("username", "")
        val password = preference.getString("password", "")

        if (username != null && password != null) {
            startIntent(username,password)
        }
    }


    private fun initCallback(loginPage: Login){
        loginPage.loginBtn.setOnClickListener{
            val username = loginPage.userNameInput.text.toString()
            val password = loginPage.passwordInput.text.toString()
            val isRemember = loginPage.rememberCheckbox.isChecked

            val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val isUserFound = loginPage.isFound(username, password, sharedPreferences)

            var isValidate = false

            if(!isUserFound){
                if (isRemember){
                    loginPage.setSharePreference(username, password, sharedPreferences)
                }
                isValidate = true
            }else{
                isValidate = loginPage.validate(username, password, sharedPreferences)
            }

            if (isValidate){
                userPage(username, password)
            }
        }
    }

    private fun isPreferenceSet(preference: SharedPreferences) : Boolean{
        val username = preference.getString("username", "")

        if (username != ""){
            return true
        }
        return false
    }

    private fun initProgram(){
        val userNameInput = findViewById<EditText>(R.id.textbox_username)
        val passwordInput = findViewById<EditText>(R.id.textbox_password)
        val loginBtn = findViewById<Button>(R.id.btn_login)
        val rememberMe = findViewById<CheckBox>(R.id.cb_remember)
        val loginPage = Login(userNameInput, passwordInput, loginBtn, rememberMe)
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        initCallback(loginPage)
        if(isPreferenceSet(sharedPreferences)){
            userPage(sharedPreferences)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initProgram()
    }


}