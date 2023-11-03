package uz.itschool.handybook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.handybook.databinding.CommentItemBinding
import uz.itschool.handybook.databinding.FragmentCommentsBinding
import uz.itschool.handybook.model.CommentResponse

class ComentsAdapter(var list:ArrayList<CommentResponse>):RecyclerView.Adapter<ComentsAdapter.MyHolder>() {
    class MyHolder(binding: CommentItemBinding):RecyclerView.ViewHolder(binding.root) {
        var commentBody= binding.comentBody
        var username=binding.username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(CommentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var comment=list[position]
        holder.username.text=comment.username
        holder.commentBody.text=comment.text
    }
}