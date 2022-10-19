package com.example.studentunits

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {


    private lateinit var signUpregistrationNo: EditText
    private lateinit var signUpemail: EditText
    private lateinit var signUppass: EditText
    private lateinit var signUpconfirmPass: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginText: TextView

    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpregistrationNo = findViewById(R.id.signUpRegNo)
        signUpemail = findViewById(R.id.signUpEmail)
        signUppass = findViewById(R.id.signUpPassword)
        signUpconfirmPass = findViewById(R.id.signUpConfirmPassword)
        signUpButton = findViewById(R.id.signUpBtn)
        loginText = findViewById(R.id.loginTv)

        mAuth = FirebaseAuth.getInstance()

        loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Setting onClickListener to registerBtn
        signUpregistrationNo.setOnClickListener{
            val userEmail = signUpemail.text.toString()
            val userPassword = signUppass.text.toString()

            //checking if user has entered the required details
            if (TextUtils.isEmpty(userEmail) and TextUtils.isEmpty(userPassword)){
                Toast.makeText(this, "Add your credentials", Toast.LENGTH_SHORT).show()
            }else{
                //Create a new user if he/she enters the required credentials
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this){
                        if (it.isSuccessful){
                            Toast.makeText(this, "Successful Registration", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, SignUpActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else {
                            Toast.makeText(this, "Unsuccessful Registration", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
}