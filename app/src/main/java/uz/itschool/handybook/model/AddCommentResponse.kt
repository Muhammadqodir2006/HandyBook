package uz.itschool.handybook.model

data class AddCommentResponse(
    val book_id: String,
    val id: Int,
    val reyting: String,
    val text: String,
    val user_id: String
)