package uz.itschool.handybook.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.adapter.BookAdapter
import uz.itschool.handybook.adapter.CategoryAdapter
import uz.itschool.handybook.databinding.FragmentHomeBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.model.Category
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService
import uz.itschool.handybook.util.SharedPrefHelper

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val api = APIClient.getInstance().create(APIService::class.java)
    private lateinit var shared: SharedPrefHelper
    private lateinit var adapter: BookAdapter
    private var allBooks = listOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeAllRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        shared = SharedPrefHelper.getInstance(requireContext())

        adapter = BookAdapter(listOf(), requireContext(), true,
            object : BookAdapter.BookClicked{
                override fun onClicked(book: Book) {
                    val bundle = Bundle()
                    bundle.putSerializable("book", book)
                    shared.setBookId(book.id)
                    findNavController().navigate(R.id.action_mainFragment_to_bookInfoFragment, bundle)
                }

            }, shared)
        binding.homeAllRecycler.adapter = adapter

        setMainBook()
        setAllRecycler()
        setSearch()
        setFilterListener()
        setCategoryAdapter()

        return binding.root
    }
    private fun setFilterListener(){
        binding.homeFilterFab.setOnClickListener {
            if (binding.homeCategoryRecycler.isVisible) binding.homeCategoryRecycler.visibility = View.GONE
            else binding.homeCategoryRecycler.visibility = View.VISIBLE
        }
    }

    private fun setCategoryAdapter() {
        binding.homeCategoryRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        api.getCategories().enqueue(object : Callback<List<Category>>{
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (!response.isSuccessful) return
                binding.homeCategoryRecycler.adapter = CategoryAdapter(response.body()!!, requireContext(), binding.homeCategoryRecycler, object : CategoryAdapter.CategoryPressed{
                    override fun onPressed(category: String?) {
                        if (category == null){
                            setAllBooks(allBooks)
                            binding.homeMainBookContainer.visibility = View.VISIBLE
                            return
                        }
                        binding.homeMainBookContainer.visibility = View.GONE
                        setCategoryChanger(category)
                    }
                })
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })
    }

    private fun setCategoryChanger(category: String) {
        api.getBooksByCategory(category).enqueue(object : Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if (!response.isSuccessful) return
                val books = response.body()!!
                setAllBooks(books)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })
    }

    private fun setSearch() {
        binding.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.homeSearchView.clearFocus()
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    binding.homeMainBookContainer.visibility = if (newText  == "") View.VISIBLE else View.GONE
                    api.search(newText).enqueue(object : Callback<List<Book>>{
                        override fun onResponse(
                            call: Call<List<Book>>,
                            response: Response<List<Book>>
                        ) {
                            if (!response.isSuccessful) return
                            setAllBooks(response.body()!!)
                        }

                        override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                            Log.d("TAG", "$t")
                        }
                    })
                    return true
                }
                setAllBooks(allBooks)
                return false
            }

        })
    }

    private fun setAllRecycler() {
        api.getBooks().enqueue(object : Callback<List<Book>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val books = response.body()!!
                cacheAllBooks(books)
                setAllBooks(books)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "$t")
            }
        })
    }

    private fun cacheAllBooks(books: List<Book>) {
        allBooks = books
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAllBooks(books: List<Book>) {
        adapter.books = books
        adapter.notifyDataSetChanged()
        binding.homeNothingFound.visibility = if (books.isEmpty()) View.VISIBLE else View.GONE
    }


    private fun setMainBook() {
        api.getMainBook().enqueue(object : Callback<Book>{
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