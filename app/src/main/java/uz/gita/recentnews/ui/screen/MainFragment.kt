package uz.gita.recentnews.ui.screen

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.recentnews.R
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews.databinding.FragmentMainBinding
import uz.gita.recentnews.presentation.MainViewModel
import uz.gita.recentnews.presentation.impl.MainViewModelImpl
import uz.gita.recentnews.ui.adapter.NewsListAdapter

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val adapter = NewsListAdapter()
    lateinit var toggle: ActionBarDrawerToggle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {

        buttonBack.setOnClickListener {
            // If the navigation drawer is not open then open it, if its already open then close it.
            if (!drawer.isDrawerOpen(GravityCompat.START)) drawer.openDrawer(GravityCompat.START)
            else drawer.closeDrawer(GravityCompat.END)
        }

        clickFavourite.setOnClickListener {
            viewModel.openFavouriteScreen()
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.allNews("all")
        }

        listNews.layoutManager = LinearLayoutManager(requireContext())
        listNews.adapter = adapter

        viewModel.errorLivedata.observe(viewLifecycleOwner, errorObserver)
        viewModel.progressLivedata.observe(viewLifecycleOwner, progressObserver)
        viewModel.loadNewsLivedata.observe(viewLifecycleOwner, loadNewsObserver)
        viewModel.openFavouriteScreenLiveData.observe(viewLifecycleOwner, openFavouriteScreenObserver)

        adapter.setListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToReadMoreFragment(it))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { return toggle.onOptionsItemSelected(item) }

    private val progressObserver = Observer<Boolean> { binding.swipeRefresh.isRefreshing = it }
    private val errorObserver = Observer<String> { Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show() }
    private val loadNewsObserver = Observer<List<NewsEntity>> {
        it.let { adapter.submitList(it) }
    }
    private val openFavouriteScreenObserver = Observer<Unit> {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToFavouriteFragment())
    }
}