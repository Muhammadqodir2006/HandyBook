package uz.itschool.handybook.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.databinding.FragmentAudioBinding
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
 * Use the [AudioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var _binding:FragmentAudioBinding??=null
    private val bookAPI by lazy { APIClient.getInstance().create(APIService::class.java)}
    private val shared by lazy {  SharedPrefHelper.getInstance(requireContext())}
    private val binding get() = _binding!!
    private lateinit var book:Book
    private var param2: String? = null

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
    ): View? {
        _binding=FragmentAudioBinding.inflate(inflater,container,false)
        fetchBook()
        object: CountDownTimer(1500,100){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                binding.audioName.text=book.name
                binding.play.setOnClickListener {
                    if (book.audio==null){
                        Toast.makeText(requireContext(),"Sorry we don't have audio for this book",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }.start()


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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AudioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AudioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}