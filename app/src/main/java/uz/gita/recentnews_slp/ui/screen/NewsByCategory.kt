package uz.gita.recentnews_slp.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.recentnews_slp.utils.isConnected
import uz.gita.recentnews_slp.R
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.databinding.FragmentNewsCatgoryBinding
import uz.gita.recentnews_slp.presentation.viewModel.CategoryViewModel
import uz.gita.recentnews_slp.presentation.viewModel.impl.CategoryViewModelImpl
import uz.gita.recentnews_slp.ui.adapter.CategoryListAdapter
import uz.gita.recentnews_slp.ui.adapter.MainListAdapter


@AndroidEntryPoint
class NewsByCategory : Fragment(R.layout.fragment_news_catgory) {
    private val binding by viewBinding(FragmentNewsCatgoryBinding::bind)
    private val navArgs by navArgs<NewsByCategoryArgs>()
    private val viewModel: CategoryViewModel by viewModels<CategoryViewModelImpl>()
    private lateinit var adapter: CategoryListAdapter


    @SuppressLint("FragmentLiveDataObserve", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {

        adapterSet()
        viewModel.allNewsByCategory(navArgs.category)

        toolbar.text = navArgs.title + " News"
        swipeRefresh.setOnRefreshListener {
            viewModel.allNewsByCategory(navArgs.category)
        }
        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.errorLivedata.observe(this@NewsByCategory, errorObserver)
        viewModel.newsByCategoryLivedata.observe(viewLifecycleOwner, newsDataObserver)
        viewModel.progressLivedata.observe(viewLifecycleOwner, progressObserver)
        viewModel.readMoreLivedata.observe(this@NewsByCategory, readMoreObserver)

    }

    private fun adapterSet() {
        adapter = CategoryListAdapter(navArgs.title)
        binding.listNews.isVisible = false
        binding.listNews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listNews.adapter = adapter

        adapter.setListener {
            if (isConnected())  viewModel.readingMore(it)
            else Toast.makeText(requireContext(), "Connection  lost!", Toast.LENGTH_SHORT).show()
        }

    }

    private val readMoreObserver = Observer<NewsEntity> {
        findNavController().navigate(NewsByCategoryDirections.actionNewsByCategoryToReadMoreFragment(it))
    }
    private val progressObserver = Observer<Boolean> {
        binding.swipeRefresh.isRefreshing = it
    }
    private val newsDataObserver = Observer<List<NewsEntity>> {
        if (it.isNotEmpty())  {
            binding.listNews.isVisible = true
            binding.lottie.isVisible = false
            adapter.submitList(it)
        } else {
            binding.listNews.isVisible = false
            binding.lottie.isVisible = true
        }
    }
    private val errorObserver = Observer<String> {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
    }

}
