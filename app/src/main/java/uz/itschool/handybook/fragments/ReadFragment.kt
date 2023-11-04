package uz.itschool.handybook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.BookAdapter
import uz.itschool.handybook.databinding.FragmentReadBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.util.SharedPrefHelper

class ReadFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReadBinding.inflate(inflater, container, false)

        val shared = SharedPrefHelper.getInstance(requireContext())
        val list = shared.getSaved()

        binding.readBooks.adapter = BookAdapter(list, requireContext(), true, object: BookAdapter.BookClicked{
            override fun onClicked(book: Book) {
                val bundle = Bundle()
                bundle.putSerializable("book", book)
                findNavController().navigate(R.id.action_mainFragment_to_bookInfoFragment, bundle)
            }

        }, shared)

        binding.readingBooks.adapter = BookAdapter(list, requireContext(), true, object: BookAdapter.BookClicked{
            override fun onClicked(book: Book) {
                val bundle = Bundle()
                bundle.putSerializable("book", book)
                findNavController().navigate(R.id.action_mainFragment_to_bookInfoFragment, bundle)
            }

        }, shared)

        return binding.root
    }
}