package uz.itschool.handybook.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.model.Comment
import uz.itschool.handybook.model.Login
import uz.itschool.handybook.model.Signin
import uz.itschool.handybook.model.UserToken

interface APIService {
    @GET("/book-api")
    fun getBooks():Call<List<Book>>

    @GET("/book-api/view")
    fun getBook(@Query("id") id:Int):Call<Book>

    @GET("/book-api/main-book")
    fun getMainBook():Call<Book>
    @GET("/book-api/category")
    fun getBookByCategory(@Query("name") name:String):Call<List<Book>>

    @GET("/book-api/comment")
    fun getComments(@Query("id") id: Int):Call<List<Comment>>

    @GET("/book-api/search-name")
    fun search(@Query("name") name: String):Call<List<Book>>

    @POST("/book-api/register")
    fun signup(@Body login: Login): Call<UserToken>

    @POST("/book-api/login")
    fun login(@Body signin: Signin): Call<UserToken>
}