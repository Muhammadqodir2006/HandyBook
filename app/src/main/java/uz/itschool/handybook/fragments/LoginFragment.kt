package uz.itschool.handybook.fragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
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
import uz.itschool.handybook.databinding.FragmentLoginBinding
import uz.itschool.handybook.model.Signin
import uz.itschool.handybook.model.UserToken
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val api = APIClient.getInstance().create(APIService::class.java)

        binding.homeMainBookReadNowMb.setOnClickListener {
            val signin = Signin(
                binding.loginEmailEditAcet.text.toString(),
                binding.loginPasswordEditAcet.text.toString()
            )
            api.login(signin).enqueue(object: Callback<UserToken> {
                override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment2)
                }

                override fun onFailure(call: Call<UserToken>, t: Throwable) {
                    Log.d("TAG", "$t")
                }

            })
        }

        return binding.root
    }
}