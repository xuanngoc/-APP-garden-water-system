package xuanngoc.myapplication.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import xuanngoc.myapplication.R
import xuanngoc.myapplication.screen.listgarden.ListGardenActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userName: TextView  = findViewById(R.id.usernameView)
        val passWord: TextView = findViewById(R.id.passwordView)

        val signIn: Button = findViewById(R.id.signInButton)
        signIn.setOnClickListener {
            val goToListGardenActivity = Intent(this@LoginActivity, ListGardenActivity::class.java)
            startActivity(goToListGardenActivity)
        }

    }
}
