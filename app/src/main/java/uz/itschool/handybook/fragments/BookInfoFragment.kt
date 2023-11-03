package uz.itschool.handybook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.itschool.handybook.R
import uz.itschool.handybook.databinding.FragmentBookInfoBinding
import uz.itschool.handybook.model.Book

private const val ARG_PARAM1 = "param1"

class BookInfoFragment : Fragment() {

    private var param1: Book? = null
    private var _binding: FragmentBookInfoBinding? =null
    private val binding get() =_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Book
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding= FragmentBookInfoBinding.inflate(inflater,container,false)

        binding.backBtnBookinfo.setOnClickListener {
            findNavController().popBackStack()
        }
        
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Book) =
            BookInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}