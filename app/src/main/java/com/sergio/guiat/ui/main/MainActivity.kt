package com.sergio.guiat.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergio.guiat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


      /*  with(mainBinding){
            viewMailTextView.text = emailReceived
        }*/


    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }*/

/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
          // R.id.menu_sign_out -> goToLoginActivity()
        }
        return super.onOptionsItemSelected(item)
    }*/


}