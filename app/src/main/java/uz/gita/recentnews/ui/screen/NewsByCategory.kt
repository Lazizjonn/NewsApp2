package uz.gita.recentnews.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.recentnews.R
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews.databinding.FragmentNewsCatgoryBinding
import uz.gita.recentnews.presentation.CategoryViewModel
import uz.gita.recentnews.presentation.impl.CategoryViewModelImpl
import uz.gita.recentnews.ui.adapter.MainListAdapter


@AndroidEntryPoint
class NewsByCategory : Fragment(R.layout.fragment_news_catgory) {
    private val binding by viewBinding(FragmentNewsCatgoryBinding::bind)
    private val navArgs by navArgs<NewsByCategoryArgs>()
    private val viewModel: CategoryViewModel by viewModels<CategoryViewModelImpl>()
    private lateinit var adapter: MainListAdapter


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

        viewModel.errorLivedata.observe(viewLifecycleOwner, errorObserver)
        viewModel.newsByCategoryLivedata.observe(viewLifecycleOwner, newsDataObserver)
        viewModel.progressLivedata.observe(viewLifecycleOwner, progressObserver)
        viewModel.readMoreLivedata.observe(viewLifecycleOwner, readMoreObserver)

    }

    private fun adapterSet() {
        adapter = MainListAdapter(navArgs.title)

        binding.listNews.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listNews.adapter = adapter

        adapter.setListener {
            viewModel.readingMore(it)
        }

    }

    private val readMoreObserver = Observer<NewsEntity> {
        findNavController().navigate(NewsByCategoryDirections.actionNewsByCategoryToReadMoreFragment(it))
    }
    private val progressObserver = Observer<Boolean> {
        binding.swipeRefresh.isRefreshing = it
    }
    private val newsDataObserver = Observer<List<NewsEntity>> {
        adapter.submitList(it)
    }
    private val errorObserver = Observer<String> {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
    }

}
