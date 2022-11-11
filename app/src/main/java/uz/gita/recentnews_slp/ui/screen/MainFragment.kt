package uz.gita.recentnews_slp.ui.screen

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.recentnews_slp.R
import uz.gita.recentnews_slp.data.common.Categories
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity
import uz.gita.recentnews_slp.databinding.FragmentMainBinding
import uz.gita.recentnews_slp.presentation.viewModel.MainViewModel
import uz.gita.recentnews_slp.presentation.viewModel.impl.MainViewModelImpl
import uz.gita.recentnews_slp.ui.adapter.MainListAdapter
import uz.gita.recentnews_slp.ui.adapter.SnapCategoryAdapter
import uz.gita.recentnews_slp.utils.isConnected


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val adapter = MainListAdapter("Latest news")
    private val snapAdapter = SnapCategoryAdapter()
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {

        menu()
        allClicks()
        adapterSet()
        setObservers()
        snapRecyclerView()
    }
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return toggle.onOptionsItemSelected(item)
    }

    private fun menu() {
        toggle = ActionBarDrawerToggle(requireActivity(), binding.drawer, R.string.open, R.string.close)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
        binding.host.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.share -> {
                    shareApp()
                    binding.drawer.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.rate_us -> {
                    rateApp()
                    binding.drawer.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.contact -> {
                    contactUs()
                    binding.drawer.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.about_us -> {
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToAboutUsFragment2())
                    binding.drawer.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    private fun snapRecyclerView() {
        binding.recyclerViewRated.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewRated.adapter = snapAdapter
        snapAdapter.categoryList = Categories.getAllCategories()
        binding.recyclerViewRated.onFlingListener = null
        val startSnapHelper2 = LinearSnapHelper()
        startSnapHelper2.attachToRecyclerView(binding.recyclerViewRated)
        binding.recyclerViewRated.setHasFixedSize(true)
        getFirstVisiblePosition(binding.recyclerViewRated)

    }
    private fun getFirstVisiblePosition(rv: RecyclerView?): Int {
        if (rv != null) {
            val layoutManager: RecyclerView.LayoutManager = rv.layoutManager!!
            if (layoutManager is LinearLayoutManager) {
                return layoutManager.findFirstVisibleItemPosition()
            }
        }
        return 0
    }
    @SuppressLint("FragmentLiveDataObserve")
    private fun setObservers() {
        viewModel.loadNewsLivedata.observe(viewLifecycleOwner, loadNewsObserver)
        viewModel.progressLivedata.observe(viewLifecycleOwner, progressObserver)
        viewModel.errorLivedata.observe(this, errorObserver)
        viewModel.readMoreLivedata.observe(this, readMoreObserver)
    }
    private fun adapterSet() {
        binding.listNews.isVisible = false
        binding.listNews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listNews.adapter = adapter
    }
    private fun allClicks() {
        binding.buttonBack.setOnClickListener {
            // If the navigation drawer is not open then open it, if its already open then close it.
            if (!binding.drawer.isDrawerOpen(GravityCompat.START)) binding.drawer.openDrawer(
                GravityCompat.START
            )
            else binding.drawer.closeDrawer(GravityCompat.END)
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.topNews()
        }
        binding.clickFavourite.setOnClickListener {
        }
        adapter.setListener {
            if (isConnected())  viewModel.readMore(it.toNewsEntity())
            else Toast.makeText(requireContext(), "Connection  lost!", Toast.LENGTH_SHORT).show()
        }
        snapAdapter.setLyambda { query, title ->
            if (!isConnected()) Toast.makeText(requireContext(), "Connection  lost!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNewsByCategory(query, title))
        }
    }
    private fun rateApp() {
        val packageName =
            "developer?id=uz.gita.recentnews_slp"
        val uri: Uri = Uri.parse("market://$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/$packageName")
                )
            )
        }
    }
    private fun shareApp(): Boolean {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val packageName = "developer?id=uz.gita.recentnews_slp"
        val shareBody = "http://play.google.com/store/apps/$packageName"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Our Apps")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
        return true
    }
    private fun contactUs(): Boolean {
        val email = "suyunovlaziz1997@gmail.com"  //"suyunovlaziz1997@gmail.com"
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(email))
        intent.data = Uri.parse("mailto:")
        startActivity(intent)
        return true
    }

    private val errorObserver = Observer<String> {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
    }
    private val loadNewsObserver = Observer<List<TopNewsEntity>> {
        Log.d("TAG", "fragment : allNews keldi, size $it")
        if (it.isNotEmpty())  {
            binding.listNews.isVisible = true
            binding.lottie.isVisible = false
            adapter.submitList(it)
        } else {
            binding.listNews.isVisible = false
            binding.lottie.isVisible = true
        }
    }
    private val progressObserver = Observer<Boolean> {
        binding.swipeRefresh.isRefreshing = it
    }
    private val readMoreObserver = Observer<NewsEntity> {
        Log.d("url", "url : $it")
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToReadMoreFragment(it))
    }


}