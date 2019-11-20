package juhchamp.com.br.simplebiometricsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preCheckButton.setOnClickListener {
            goPreCheckActivity()
        }

        noPreCheckButton.setOnClickListener {
            goNoPreCheckActivity()
        }
    }

    private fun goPreCheckActivity() {
        startActivity(Intent(this, ExampleWithPreCheckActivity::class.java))
    }

    private fun goNoPreCheckActivity() {
        startActivity(Intent(this, ExampleWithNoPreCheckActivity::class.java))
    }
}
