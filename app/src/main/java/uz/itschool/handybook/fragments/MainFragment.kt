package uz.itschool.handybook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.itschool.handybook.R
import uz.itschool.handybook.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setFragmentView()
        setBottomNavigation()

        return binding.root
    }

    private fun setBottomNavigation() {
        // TODO: set bottom navigation
    }

    private fun setFragmentView() {
        parentFragmentManager.beginTransaction().add(R.id.main_fragment_container, HomeFragment()).commit()
    }
}