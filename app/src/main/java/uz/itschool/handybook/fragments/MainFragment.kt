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
        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.home_sc -> loadFragment(HomeFragment())
                R.id.reading_sc -> loadFragment(HomeFragment())
                R.id.bookmark_sc -> loadFragment(HomeFragment())
                R.id.profile_sc -> loadFragment(HomeFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit()
    }

    private fun setFragmentView() {
        parentFragmentManager.beginTransaction().add(R.id.main_fragment_container, HomeFragment()).commit()
    }
}