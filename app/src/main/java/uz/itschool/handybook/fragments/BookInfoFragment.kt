package uz.itschool.handybook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.ViewPagerAdapter
import uz.itschool.handybook.databinding.FragmentBookInfoBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService

private const val ARG_PARAM1 = "param1"

class BookInfoFragment : Fragment() {

    private var param1: Book? = null
    private var _binding: FragmentBookInfoBinding?? =null
    private val binding get() =_binding!!
    private val bookAPI by lazy { APIClient.getInstance().create(APIService::class.java)}
    private val fragmentAdapter by lazy { ViewPagerAdapter(parentFragmentManager) }
    private val infoFragmentAdapter by lazy { ViewPagerAdapter(parentFragmentManager) }
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Book
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fetchBook()
        _binding= FragmentBookInfoBinding.inflate(inflater,container,false)
        fragmentAdapter.addFragment(EBookFragment(),"E-Book")
        fragmentAdapter.addFragment(AudioFragment(),"Audio Book")
        binding.tabLayoutBook.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter=fragmentAdapter

        infoFragmentAdapter.addFragment(DescriptionFragment(),"Description")
        infoFragmentAdapter.addFragment(CommentsFragment(),"Coments")
        binding.l.adapter=infoFragmentAdapter
        binding.tabLayoutInfo.setupWithViewPager(binding.l)

        return binding.root
    }
    fun fetchBook(){
        bookAPI.getBook(1).enqueue(object :Callback<Book>{
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                var body=response.body()
                if (response.isSuccessful&& body!=null){
                    book=body
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
            }
        })
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