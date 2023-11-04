package uz.itschool.handybook.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.model.User

class SharedPrefHelper private constructor(context: Context) {

     private val shared : SharedPreferences = context.getSharedPreferences("data", 0)
     private val edit: SharedPreferences.Editor = shared.edit()
     private val gson  = Gson()


    companion   object {
        private var instance: SharedPrefHelper? = null
        fun getInstance(context: Context): SharedPrefHelper {
            if (instance == null) instance = SharedPrefHelper(context)
            return instance!!
        }
    }

    private val userKEY = "user"
    private val rememberMeKEY = "rememberMe"
    private val savedKEY = "saved"
    private val readKEY = "read"
    private val readingKEY = "reading"
    private val TEMP_BOOK_ID = "tempbook"

    fun setBookId(id: Int){
        edit.putInt(TEMP_BOOK_ID,id).apply()
    }
    fun getBookId():Int{
        return shared.getInt(TEMP_BOOK_ID,0)
    }
    fun setUser(user : User) {
        val temp = gson.toJson(user)
        edit.putString(userKEY, temp).apply()
    }
    fun getUser() : User? {
        val data = shared.getString(userKEY, "")
        if (data == "") return null
        val typeToken = object : TypeToken<User>() {}.type
        return gson.fromJson(data, typeToken)
    }
    fun logout(){
        edit.putString(userKEY, "").apply()
    }
    fun setRememberMe(username:String){
        if (username == "") {
            edit.remove(rememberMeKEY).apply()
        }else{
            edit.putString(rememberMeKEY, username).apply()
        }
    }
    fun getRememberMe(): String? {
        return shared.getString(rememberMeKEY, null)
    }

    private fun getSaved(): List<Book> {
        val data = shared.getString(savedKEY, "")
        if (data == "") return listOf()
        val typeToken = object : TypeToken<List<Book>>() {}.type
        return gson.fromJson(data, typeToken)
    }

    fun addSaved(book: Book):List<Book>{
        val books = getSaved().toMutableList()
        books.add(book)
        edit.putString(savedKEY, gson.toJson(books)).apply()
        return books
    }

    fun removeSaved(id:Int):List<Book>{
        val books = getSaved().toMutableList()
        for (i in books){
            if (i.id == id) {
                books.remove(i)
                break
            }
        }
        edit.putString(savedKEY, gson.toJson(books)).apply()
        return books
    }
    fun isSaved(id:Int): Boolean {
        val books = getSaved()
        for (i in books){
            if (i.id == id) return true
        }
        return false
    }
    private fun getRead(): List<Book> {
        val data = shared.getString(readKEY, "")
        if (data == "") return listOf()
        val typeToken = object : TypeToken<List<Book>>() {}.type
        return gson.fromJson(data, typeToken)
    }

    fun addRead(book: Book):List<Book>{
        val books = getSaved().toMutableList()
        books.add(book)
        edit.putString(readKEY, gson.toJson(books)).apply()
        return books
    }
    fun isRead(id:Int): Boolean {
        val books = getRead()
        for (i in books){
            if (i.id == id) return true
        }
        return false
    }

    private fun getReading(): List<Book> {
        val data = shared.getString(readingKEY, "")
        if (data == "") return listOf()
        val typeToken = object : TypeToken<List<Book>>() {}.type
        return gson.fromJson(data, typeToken)
    }

    fun addReading(book: Book): MutableList<Book>? {
        val books = getSaved().toMutableList()
        for (i in books){
            if (i == book) return null
        }
        books.add(book)
        edit.putString(readingKEY, gson.toJson(books)).apply()
        return books
    }

    fun isReading(id:Int): Boolean {
        val books = getReading()
        for (i in books){
            if (i.id == id) return true
        }
        return false
    }





}