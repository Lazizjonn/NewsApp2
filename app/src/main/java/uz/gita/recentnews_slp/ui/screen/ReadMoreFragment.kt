package uz.gita.recentnews_slp.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.recentnews_slp.R
import uz.gita.recentnews_slp.databinding.FragmentReadMoreBinding

@AndroidEntryPoint
class ReadMoreFragment : Fragment(R.layout.fragment_read_more) {
    private val binding by viewBinding(FragmentReadMoreBinding::bind)
    private val navArgs by navArgs<ReadMoreFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.webView.loadUrl(navArgs.data.readMore!!)
        binding.toolbarText.text = (navArgs.data.title).trim()
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}