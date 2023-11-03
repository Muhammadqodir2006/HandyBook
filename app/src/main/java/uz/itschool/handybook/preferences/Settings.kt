package uz.itschool.housesales.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.itschool.handybook.model.User

class Settings private constructor(context: Context) {

     val preferences : SharedPreferences = context.getSharedPreferences("data", 0)
     private val set: SharedPreferences.Editor = preferences.edit()
     private val gson  = Gson()


    companion   object {

        private var settings: Settings? = null
        fun getData(context: Context): Settings {
            val currentSettings =  settings?: Settings(context)
            settings = currentSettings

            return currentSettings
        }
    }
    fun setUser(user : User) {
        var temp = gson.toJson(user)
        set.putString("user", temp).apply()
    }
    fun getUser() : User? {
        val data = preferences.getString("user", "")
        if (data == "") return null
        val typeToken = object : TypeToken<User>() {}.type
        return gson.fromJson(data, typeToken)
    }
    fun logout(){
        set.putString("user", "").apply()
    }

}