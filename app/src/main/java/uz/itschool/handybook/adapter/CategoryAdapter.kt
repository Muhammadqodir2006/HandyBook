package uz.itschool.handybook.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import uz.itschool.handybook.R
import uz.itschool.handybook.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val context: Context,
    private val categoryRecyclerView: RecyclerView,
    private val categoryPressed: CategoryPressed
) : RecyclerView.Adapter<CategoryAdapter.MyHolder>() {
    private var current = 0

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.category_mcv)
        val text: TextView = itemView.findViewById(R.id.category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyHolder, @SuppressLint("RecyclerView") position: Int) {
        if (position == 0) {
            holder.text.text = "Hammasi"
        } else {
            holder.text.text = categories[position - 1].type_name.capitalize()
        }
        if (current == position) {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.primary))
            holder.text.setTextColor(context.resources.getColor(R.color.white))
        } else {
            holder.cardView.setCardBackgroundColor(context.resources.getColor(R.color.white))
            holder.text.setTextColor(context.resources.getColor(R.color.primary))
        }
        holder.cardView.setOnClickListener {
            if (position != current) {
                notifyItemChanged(current)
                current = position
                notifyItemChanged(current)
                if (position == 0) categoryPressed.onPressed(null)
                else categoryPressed.onPressed(categories[position - 1].type_name)
            }
            categoryRecyclerView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return categories.size + 1
    }



    interface CategoryPressed {
        fun onPressed(category: String?)
    }

}