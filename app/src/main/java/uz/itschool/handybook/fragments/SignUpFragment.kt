package uz.itschool.handybook.fragments

import android.os.Bundle
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
import uz.itschool.handybook.databinding.FragmentSignUpBinding
import uz.itschool.handybook.model.Login
import uz.itschool.handybook.model.UserToken
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        val api = APIClient.getInstance().create(APIService::class.java)
        binding.signupSignupMb.setOnClickListener {
            if (validate()){
                val login = Login(
                    binding.signupIsm.text.toString(),
                    binding.signupSurnameEditAcet.text.toString(),
                    binding.signupEmailEditAcet.text.toString(),
                    binding.signupPasswordEditAcet.text.toString()
                )
                api.signup(login).enqueue(object: Callback<UserToken>{
                    override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
                        findNavController().navigate(R.id.action_signUpFragment_to_mainFragment2)
                    }

                    override fun onFailure(call: Call<UserToken>, t: Throwable) {
                        Log.d("TAG", "$t")
                    }

                })
            }
        }

        return binding.root
    }

    fun validate(): Boolean {
        return true
    }
}