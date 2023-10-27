package uz.itschool.handybook.model

data class UserToken(
    val access_token: String,
    val id: Int,
    val username: String
)