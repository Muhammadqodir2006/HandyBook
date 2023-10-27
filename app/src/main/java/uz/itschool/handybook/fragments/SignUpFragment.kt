package uz.itschool.handybook.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itschool.handybook.R
import uz.itschool.handybook.databinding.FragmentSignUpBinding
import uz.itschool.handybook.model.SignUp
import uz.itschool.handybook.model.User
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService
import uz.itschool.housesales.preferences.SharedPrefHelper

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val api = APIClient.getInstance().create(APIService::class.java)
    private lateinit var shared: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        shared = SharedPrefHelper.getInstance(requireContext())

        setbackButton()
        setSignUpButton()



        return binding.root
    }

    private fun setbackButton() {
        binding.signupBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setSignUpButton() {
        binding.signupSignupMb.setOnClickListener {
            if (binding.signupUsernameEditAcet.text.toString() == "") {
                showToast("Username bo'sh bo'lishi mumkin emas")
                return@setOnClickListener
            }
            if (binding.signupPasswordEditAcet.text.toString() == "") {
                showToast("Parol bo'sh bo'lishi mumkin emas")
                return@setOnClickListener
            }
            val signUp = SignUp(
                username = binding.signupUsernameEditAcet.text.toString().trim(),
                fullname = binding.signupFirstnameAcet.text.toString()
                    .trim() + " " + binding.signupSurnameEditAcet.text.toString().trim(),
                password = binding.signupPasswordEditAcet.text.toString().trim()
            )
            if (!validate(signUp)) return@setOnClickListener

            api.signup(signUp).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.d("TAG", "$response")
                    if (!response.isSuccessful) {
                        showToast("sign up error")
                        return
                    }
                    if (response.code() == 422){
                        showToast("Username allaqachon olingan")
                        binding.signupUsernameEditAcet.setText("")
                        return
                    }

                    val user = response.body()!!
                    shared.setUser(user)
                    findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG", "$t")
                }
            })
        }
    }

    private fun validate(signUp: SignUp): Boolean {
        var out = true
        if (binding.signupFirstnameAcet.text.toString() == "") {
            binding.signupFirstnameIncorrectTv.visibility = View.VISIBLE
            out = false
        } else binding.signupFirstnameIncorrectTv.visibility = View.GONE

        if (binding.signupSurnameEditAcet.text.toString() == "") {
            binding.signupSurnameIncorrectTv.visibility = View.VISIBLE
            out = false
        } else binding.signupSurnameIncorrectTv.visibility = View.GONE

        if (signUp.username.length < 5) {
            binding.signupUsernameIncorrectTv.visibility = View.VISIBLE
            out = false
        } else binding.signupUsernameIncorrectTv.visibility = View.GONE

        if (signUp.password.length < 8) {
            binding.signupPasswordIncorrectTv.visibility = View.VISIBLE
            binding.signupPasswordEditAcet.setText("")
            binding.signupReenterPasswordEditAcet.setText("")
            out = false
        } else binding.signupPasswordIncorrectTv.visibility = View.GONE

        if (signUp.password.length >= 8 && binding.signupReenterPasswordEditAcet.text.toString()
                .trim() != signUp.password
        ) {
            binding.signupReenterPasswordIncorrectTv.visibility = View.VISIBLE
            binding.signupReenterPasswordEditAcet.setText("")
            out = false
        } else binding.signupReenterPasswordIncorrectTv.visibility = View.GONE
        return out
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}