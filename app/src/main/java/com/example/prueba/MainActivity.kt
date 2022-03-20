package com.example.prueba

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Llamada a registrar usuario
        /*fun onClick(view: View) {
            CreateAccount();
        }*/
    }

    private fun CreateAccount(){
        val emailPart: TextView = findViewById(R.id.editTextTextEmailAddress);
        val pwdPart: TextView = findViewById(R.id.editTextTextPassword);

        val email = emailPart.text.toString();
        val password= pwdPart.text.toString();

        //Validar si los campos estan vacios

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setTitle("Sign up");
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    saveUserInfo(email);
                }else{
                    val message = task.exception!!.toString();
                    Toast.makeText(this, "", Toast.LENGTH_LONG)
                    progressDialog.dismiss();
                }
            };
    }

    private fun saveUserInfo(email: String) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid;
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users");

        val userMap = HashMap<String, Any>();
        userMap["uid"]=currentUserID;
        userMap["email"]=email; //Dudo que aqui vaya el currentUserID, pero segun el tuto es: userMap["email"]=currentUserID
        userMap["bio"]= "Hey esto es el bio";
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/witstop-1049d.appspot.com/o/Default%20Images%2Fuser.png?alt=media&token=5d69e514-cda2-4806-a6b2-f74e6ec49373"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if( task.isSuccessful){/*
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent);
                    finish();*/
                }
            }
    }

    fun onClick(view: View) {
        CreateAccount();
    }


}

/*
 PARA Sign in -> Mirar final del tutorial capitulo 08
*/

