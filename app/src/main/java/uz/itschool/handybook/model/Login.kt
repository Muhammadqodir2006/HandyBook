package uz.itschool.handybook.model

data class Login (
    var username: String,
    val fullname: String,
    val email: String,
    val password: String,
)