package uz.itschool.handybook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.itschool.handybook.R
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.util.SharedPrefHelper

class BookAdapter(var books : List<Book>, val context: Context, val isVertical : Boolean,
    val bookClicked: BookClicked, val shared: SharedPrefHelper): RecyclerView.Adapter<BookAdapter.BookHolder>() {
    val saved = MutableList(books.size){false}
    inner class BookHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView.findViewById(R.id.book_item_title)
        val audio:ImageView = itemView.findViewById(R.id.book_item_audio)
        val author:TextView = itemView.findViewById(R.id.book_item_author)
        val rating:TextView = itemView.findViewById(R.id.book_item_rating)
        val imageIV:ImageView = itemView.findViewById(R.id.book_item_image_iv)
        val bookmarkIV:ImageView = itemView.findViewById(R.id.book_item_bookmark_iv)
        val bookMarkCV:CardView = itemView.findViewById(R.id.book_item_bookmark_cardview)

        val imageCV:CardView = itemView.findViewById(R.id.book_item_image_card_view)
        val parentView:ConstraintLayout = itemView.findViewById(R.id.book_item_parent_constraint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false))
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.name
        if (book.audio == null) holder.audio.visibility = View.GONE
        holder.author.text = book.author
        holder.imageIV.load(book.image)
        holder.rating.text = book.reyting.toString()
        if (shared.isSaved(book.id)) {
            saved[position] = true
            holder.bookmarkIV.setImageResource(R.drawable.bookmark_icon_enabled)
        }
        holder.bookmarkIV.setOnClickListener {
            if (saved[position]){
                shared.removeSaved(book.id)
                holder.bookmarkIV.setImageResource(R.drawable.bookmark_icon)
            }else{
                shared.addSaved(book)
                holder.bookmarkIV.setImageResource(R.drawable.bookmark_icon_enabled)
            }
        }
        if (isVertical){
            holder.imageCV.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            holder.parentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }

        holder.parentView.setOnClickListener {
            bookClicked.onClicked(book)
        }
    }

    interface BookClicked{
        fun onClicked(book:Book)
    }
}