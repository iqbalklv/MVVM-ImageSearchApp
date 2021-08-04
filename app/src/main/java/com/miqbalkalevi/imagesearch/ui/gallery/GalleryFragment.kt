package com.miqbalkalevi.imagesearch.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.miqbalkalevi.imagesearch.R
import com.miqbalkalevi.imagesearch.data.UnsplashPhoto
import com.miqbalkalevi.imagesearch.databinding.FragmentGalleryBinding
import com.miqbalkalevi.imagesearch.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),
    UnsplashPhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter(this)

        binding.apply {
            rvGallery.setHasFixedSize(true)
            rvGallery.itemAnimator = null
            rvGallery.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() }
            )

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                pbGallery.isVisible = loadState.source.refresh is LoadState.Loading

                rvGallery.isVisible = loadState.source.refresh is LoadState.NotLoading
                //(loadState.source.refresh !is LoadState.Loading && loadState.source.refresh !is LoadState.Error)

                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvErrorLoad.isVisible = loadState.source.refresh is LoadState.Error

                //Empty RecView
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    rvGallery.isVisible = false
                    tvErrorQueryEmpty.isVisible = true
                } else {
                    tvErrorQueryEmpty.isVisible = false
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.galleryEvent.collect { event ->
                when (event) {
                    is GalleryViewModel.GalleryEvent.NavigateToDetailsScreen -> {
                        val action =
                            GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(event.photo)
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }


        setHasOptionsMenu(true)
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        viewModel.onPhotoClicked(photo)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.rvGallery.scrollToPosition(0)
                    viewModel.onSearchMenuSubmit(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}