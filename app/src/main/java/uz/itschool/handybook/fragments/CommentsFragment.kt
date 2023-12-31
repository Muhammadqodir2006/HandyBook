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
import uz.itschool.handybook.adapter.ComentsAdapter
import uz.itschool.handybook.databinding.FragmentBookInfoBinding
import uz.itschool.handybook.databinding.FragmentCommentsBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.model.CommentResponse
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService
import uz.itschool.handybook.util.SharedPrefHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private val bookAPI by lazy { APIClient.getInstance().create(APIService::class.java)}
    private var _binding: FragmentCommentsBinding?? =null
    private lateinit var book:Book
    private val binding get() =_binding!!
    val bundle = Bundle()
    private val shared by lazy {  SharedPrefHelper.getInstance(requireContext())}
    private var comments=ArrayList<CommentResponse>()
    private val commentAdapter by lazy { ComentsAdapter(comments) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fetchComments()
        fetchBook()
        _binding=FragmentCommentsBinding.inflate(layoutInflater,container,false)
        object: CountDownTimer(1500,100){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                bundle.putSerializable("book", book)
            }
        }.start()
        binding.comments.adapter=commentAdapter

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
    fun fetchComments(){
        bookAPI.getComments(shared.getBookId()).enqueue(object : Callback<List<CommentResponse>>{
            override fun onResponse(
                call: Call<List<CommentResponse>>,
                response: Response<List<CommentResponse>>,
            ) {
                var body = response.body()
                if (response.isSuccessful && body!=null){
                    comments.clear()
                    comments.addAll(body)
                    commentAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<CommentResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}