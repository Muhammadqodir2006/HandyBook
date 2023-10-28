package uz.itschool.handybook.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import retrofit2.Call
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.BookAdapter
import uz.itschool.handybook.databinding.FragmentHomeBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService
import uz.itschool.handybook.util.SharedPrefHelper

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val api = APIClient.getInstance().create(APIService::class.java)
    private lateinit var shared: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeAllRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        shared = SharedPrefHelper.getInstance(requireContext())

        setUserImage()
        setMainBook()
        setAllRecycler()

        return binding.root
    }

    private fun setUserImage() {
        binding.homeUserImage.setOnClickListener {

            parentFragmentManager.beginTransaction().replace(R.id.main_fragment_container, ProfileFragment()).commit()
        }
    }

    private fun setAllRecycler() {
        api.getBooks().enqueue(object : retrofit2.Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val books = response.body()!!
                binding.homeAllRecycler.adapter = BookAdapter(books, requireContext(), true)
                Log.d("TAG", "$books")
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "$t")
            }

        })
    }

    private fun setMainBook() {
        api.getMainBook().enqueue(object : retrofit2.Callback<Book>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                val mainBook = response.body()!!
                binding.homeMainBookImage.load(mainBook.image)
                binding.homeMainBookText.text = """${mainBook.author}ning "${mainBook.name}" asari"""
                binding.homeMainBookReadNowMb.setOnClickListener {
                    // TODO Set listener
                }
            }
            override fun onFailure(call: Call<Book>, t: Throwable) {
                Log.d("TAG", "$t")
            }

        })
    }
}