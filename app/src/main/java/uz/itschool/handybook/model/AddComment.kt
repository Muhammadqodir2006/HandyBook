package uz.itschool.handybook.model

data class AddComment(
    val book_id: Int,
    val reyting: Int,
    val text: String,
    val user_id: Int
)