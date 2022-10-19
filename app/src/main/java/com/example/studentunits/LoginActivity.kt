package com.example.studentunits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.textclassifier.TextClassificationContext
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginregistrationNo: EditText
    private lateinit var loginpass: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPass: TextView
    private lateinit var signUpText: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginregistrationNo = findViewById(R.id.loginRegNo)
        loginpass = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginBtn)
        forgotPass = findViewById(R.id.forgotPassword)
        signUpText = findViewById(R.id.signUpTv)

        auth = FirebaseAuth.getInstance()

        //Adding onClickListener to Sign Up TextView
        signUpText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        //Sign in Button onClickLIstener
        loginButton.setOnClickListener {
            val userEmail = loginregistrationNo.text.toString()
            val userPassword = loginpass.text.toString()

            if (TextUtils.isEmpty(userEmail) and TextUtils.isEmpty(userPassword)) {
                Toast.makeText(this, "Please enter your credentials", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "UnSuccessful Login", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }

    //onStart Method to check if user is already authenticated or not => if authenticated, go to the main page
    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

}