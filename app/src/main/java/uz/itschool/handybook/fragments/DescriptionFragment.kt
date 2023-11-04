package uz.itschool.handybook.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.BookAdapter
import uz.itschool.handybook.databinding.FragmentBookInfoBinding
import uz.itschool.handybook.databinding.FragmentDescriptionBinding
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
 * Use the [DescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescriptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentDescriptionBinding? =null
    private lateinit var book: Book
    private val binding get() =_binding!!
    private var recomend=ArrayList<Book>()
    val recomendAdapter by lazy {  BookAdapter(recomend, requireContext(), false,
        object : BookAdapter.BookClicked{
            override fun onClicked(book: Book) {
                val bundle = Bundle()
                bundle.putSerializable("book", book)
                shared.setBookId(book.id)
                findNavController().navigate(R.id.action_mainFragment_to_bookInfoFragment, bundle)
            }

        }, shared) }
    private val shared by lazy {  SharedPrefHelper.getInstance(requireContext())}
    private val bookAPI by lazy { APIClient.getInstance().create(APIService::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding= FragmentDescriptionBinding.inflate(inflater,container,false)
        fetchBook()
        fetchRecomendedBooks("Badiiy adabiyot")
        object: CountDownTimer(2000,100){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                binding.description.text=book.description
                recomendAdapter.notifyDataSetChanged()
            }
        }.start()


        binding.recomend.adapter=recomendAdapter

        return binding.root
    }
    private fun fetchRecomendedBooks(category: String) {
        bookAPI.getBooksByCategory(category).enqueue(object : Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if (!response.isSuccessful) return
                val books = response.body()!!
                recomend.clear()
                recomend.addAll(books)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DescriptionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}