package uz.itschool.handybook.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.ViewPagerAdapter
import uz.itschool.handybook.databinding.FragmentBookInfoBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService
import uz.itschool.handybook.util.SharedPrefHelper

private const val ARG_PARAM1 = "book"

class BookInfoFragment : Fragment() {


    private var _binding: FragmentBookInfoBinding? =null
    private val binding get() =_binding!!

    private val fragmentAdapter by lazy { ViewPagerAdapter(parentFragmentManager) }
    private val infoFragmentAdapter by lazy { ViewPagerAdapter(parentFragmentManager) }
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            book = it.getSerializable(ARG_PARAM1) as Book
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding= FragmentBookInfoBinding.inflate(inflater,container,false)

        setAdapters()

        binding.imageView4.setOnClickListener {
            findNavController().navigate(R.id.action_bookInfoFragment_to_moreInfoFragmentFragment)
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tabLayoutInfo.setupWithViewPager(binding.l)
        return binding.root
    }
    fun setAdapters(){
        fragmentAdapter.addFragment(EBookFragment(),"E-Book")
        fragmentAdapter.addFragment(AudioFragment(),"Audio Book")
        binding.tabLayoutBook.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter=fragmentAdapter

        binding.bookTitle.text = book.name
        binding.imageView4.load(book.image)
        infoFragmentAdapter.addFragment(DescriptionFragment(),"Description")
        infoFragmentAdapter.addFragment(CommentsFragment(),"Coments")
        binding.l.adapter=infoFragmentAdapter
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