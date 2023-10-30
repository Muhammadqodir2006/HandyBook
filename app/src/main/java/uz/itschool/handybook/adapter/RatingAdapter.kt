package uz.itschool.handybook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.handybook.R

class RatingAdapter(private val ratingClick: RatingClickInterface) :
    RecyclerView.Adapter<RatingAdapter.RatingHolder>() {
    private var rating = 3

    inner class RatingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.rating_item_image_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingHolder {
        return RatingHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rating_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RatingHolder, position: Int) {
        holder.img.setImageResource(
            if (position < rating) R.drawable.star_enabled
            else R.drawable.star_disabled
        )
        holder.img.setOnClickListener {
            val previous = rating
            rating = position + 1
            if (rating == previous) return@setOnClickListener
            notifyDataSetChanged()
            ratingClick.onClick(previous, rating)
        }
    }

    interface RatingClickInterface {
        fun onClick(previous: Int, current:Int)
    }
}