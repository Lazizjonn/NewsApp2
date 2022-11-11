package uz.gita.recentnews_slp.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.recentnews_slp.R
import uz.gita.recentnews_slp.databinding.FragmentAboutUsBinding

@AndroidEntryPoint
class AboutUsFragment : Fragment(R.layout.fragment_about_us) {

    private val binding by viewBinding(FragmentAboutUsBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) : Unit = with(binding){
        super.onViewCreated(view, savedInstanceState)
    }


}