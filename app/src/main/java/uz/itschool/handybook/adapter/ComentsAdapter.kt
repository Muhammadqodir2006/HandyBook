package uz.itschool.handybook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.itschool.handybook.databinding.CommentItemBinding
import uz.itschool.handybook.databinding.FragmentCommentsBinding

class ComentsAdapter:RecyclerView.Adapter<ComentsAdapter.MyHolder>() {
    class MyHolder(binding: FragmentCommentsBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(FragmentCommentsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        TODO("Not yet implemented")
    }
}