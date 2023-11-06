package uz.itschool.handybook.fragments

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.barteksc.pdfviewer.PDFView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.databinding.FragmentReadPdfBinding
import uz.itschool.handybook.model.Book
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService
import uz.itschool.handybook.util.SharedPrefHelper
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [ReadPdfFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadPdfFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var pdfUri: String? = null
    private lateinit var binding: FragmentReadPdfBinding
    private lateinit var book: Book
    private val bookAPI by lazy { APIClient.getInstance().create(APIService::class.java)}
    private val shared by lazy {  SharedPrefHelper.getInstance(requireContext())}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pdfUri = it.getString("pdf")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadPdfBinding.inflate(inflater, container, false)


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

        var pdfView: PDFView = binding.pdfView

        ReadPDFFromURI(pdfView).execute(book.file)

        return binding.root
    }

    class ReadPDFFromURI(pdfView: PDFView) : AsyncTask<String, Void, InputStream>(){
        val mypdfView: PDFView = pdfView
        override fun doInBackground(vararg p0: String?): InputStream? {
            var inputStream: InputStream? = null

            try {
                val url = URL(p0.get(0))
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection

                if (urlConnection.responseCode == 200) {
                    // on below line we are initializing our input stream
                    // if the response is successful.
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            }catch (e: Exception){
                e.printStackTrace()
                return null
            }

            return inputStream


        }


        override fun onPostExecute(result: InputStream?) {
            mypdfView.fromStream(result).load()
        }

    }

}