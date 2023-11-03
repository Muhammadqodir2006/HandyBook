package uz.itschool.handybook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.BookAdapter
import uz.itschool.handybook.databinding.FragmentMoreBooksBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoreBooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoreBooksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var category: String = ""
    lateinit var binding: FragmentMoreBooksBinding
    private val api = APIClient.getInstance().create(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_PARAM1)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBooksBinding.inflate(inflater, container, false)

        setAdapter()

        return binding.root
    }

    private fun setAdapter(){
        api.getBookByCategory(category).enqueue(object : Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                var books = response.body()!!

                binding.moreBooksRecycler.adapter = BookAdapter(books, requireContext(), true,
                    object : BookAdapter.BookClicked{
                        override fun onClicked(book: Book) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.main_fragment_container,
                                    BookInfoFragment.newInstance(book)).commit()
                        }

                    })
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(category: String) =
            MoreBooksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, category)
                }
            }
    }
}