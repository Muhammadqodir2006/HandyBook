package uz.itschool.handybook.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.ViewPagerAdapter
import uz.itschool.handybook.databinding.FragmentMoreInfoBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService
import uz.itschool.handybook.util.SharedPrefHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoreInfoFragmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoreInfoFragmentFragment : Fragment() {

    private var _binding: FragmentMoreInfoBinding? = null
    private val binding get() = _binding!!
    private val infoFragmentAdapter by lazy { ViewPagerAdapter(parentFragmentManager) }
    private val shared by lazy {  SharedPrefHelper.getInstance(requireContext())}
    private val bookAPI by lazy { APIClient.getInstance().create(APIService::class.java)}
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fetchBook()
        _binding=FragmentMoreInfoBinding.inflate(inflater,container,false)
        infoFragmentAdapter.addFragment(DescriptionFragment(),"Description")
        infoFragmentAdapter.addFragment(CommentsFragment(),"Coments")
        binding.pager.adapter=infoFragmentAdapter
        binding.tabLayout.setupWithViewPager(binding.pager)
        object: CountDownTimer(2000,100){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                binding.button.setOnClickListener {
                    var bundle=Bundle()
                    bundle.putSerializable("book",book)
                    findNavController().navigate(R.id.action_moreInfoFragmentFragment_to_addCommentFragment,bundle)
                }
            }
        }.start()

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
    fun fetchBook(){
        bookAPI.getBook(shared.getBookId()).enqueue(object : Callback<Book> {
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
}
