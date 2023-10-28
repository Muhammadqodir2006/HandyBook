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
import uz.itschool.handybook.databinding.FragmentLoginBinding
import uz.itschool.handybook.model.SignIn
import uz.itschool.handybook.model.User
import uz.itschool.handybook.networking.APIClient
import uz.itschool.handybook.networking.APIService
import uz.itschool.handybook.util.SharedPrefHelper

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val api = APIClient.getInstance().create(APIService::class.java)
    private lateinit var shared : SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        shared = SharedPrefHelper.getInstance(requireContext())
        binding.loginRegisterCv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        setRememberme()
        setLoginButton()
        setBackButton()

        return binding.root
    }

    private fun setRememberme() {
        val username = shared.getRememberMe()
        if (username != null) {
            binding.loginRememberMeAcchb.isChecked =true
            binding.loginUsernameEditAcet.setText(username)
        }
    }


    private fun setBackButton() {
        binding.loginBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setLoginButton() {
        binding.loginLoginMb.setOnClickListener {
            val signIn = SignIn(
                binding.loginUsernameEditAcet.text.toString().trim(),
                binding.loginPasswordEditAcet.text.toString().trim()
            )
            if (signIn.password == "" || signIn.username == "") return@setOnClickListener
            if (!validate(signIn)) return@setOnClickListener
            api.login(signIn).enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (!response.isSuccessful) {
                        binding.loginPasswordEditAcet.setText("")
                        Toast.makeText(requireContext(), "Noto'g'ri username yoki parol", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val user: User = response.body()!!
                    if (binding.loginRememberMeAcchb.isChecked) shared.setRememberMe(user.username) else shared.setRememberMe("")
                    shared.setUser(user)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG", "$t")
                }

            })
        }
    }

    private fun validate(signIn: SignIn): Boolean {
        var out = true
        if (signIn.username.length < 5) {
            binding.loginUsernameIncorrectTv.visibility = View.VISIBLE
            binding.loginPasswordEditAcet.setText("")
            out = false
        }else{
            binding.loginUsernameIncorrectTv.visibility = View.GONE
        }
        if (signIn.password.length < 8) {
            binding.loginPasswordIncorrectTv.visibility = View.VISIBLE
            binding.loginPasswordEditAcet.setText("")
            out = false
        }else{
            binding.loginPasswordIncorrectTv.visibility = View.GONE
        }
        return out
    }
}