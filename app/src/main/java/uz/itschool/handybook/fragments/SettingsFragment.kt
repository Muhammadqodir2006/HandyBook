package uz.itschool.handybook.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.itschool.handybook.R
import uz.itschool.handybook.databinding.FragmentSettingsBinding
import uz.itschool.handybook.util.SharedPrefHelper

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var shared: SharedPrefHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        shared = SharedPrefHelper.getInstance(requireContext())

        setbackButton()
        setLogoutButton()

        val user = shared.getUser()

        binding.logoutFirstnameAcet.text = user?.fullname
        binding.logoutUsernameEditAcet.text = user?.username


        return binding.root
    }

    private fun setbackButton() {
        binding.signupBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setLogoutButton() {
        binding.logoutLogoutMb.setOnClickListener {
            shared.logout()
            findNavController().navigate(R.id.action_mainFragment_to_welcomeFragment)
        }
    }
}