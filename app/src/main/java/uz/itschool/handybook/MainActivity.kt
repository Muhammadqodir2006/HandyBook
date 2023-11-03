package uz.itschool.handybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.itschool.handybook.fragments.BookInfoFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}