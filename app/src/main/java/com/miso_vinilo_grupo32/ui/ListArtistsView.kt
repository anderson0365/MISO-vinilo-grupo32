package com.miso_vinilo_grupo32.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miso_vinilo_grupo32.R
import com.miso_vinilo_grupo32.databinding.FragmentListArtistViewBinding
import com.miso_vinilo_grupo32.ui.adapters.ListArtistsAdapter
import com.miso_vinilo_grupo32.viewmodels.ListArtistsVM

class ListArtistsView : Fragment() {

    private var _binding : FragmentListArtistViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var artistsVM : ListArtistsVM
    private lateinit var viewModelAdapter: ListArtistsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListArtistViewBinding.inflate(inflater, container, false)
        viewModelAdapter = ListArtistsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.artistRv

        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = viewModelAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        //activity.actionBar?.title = getString(R.string.title_collectors)

        artistsVM = ViewModelProvider(this, ListArtistsVM.Factory(activity.application)).get(ListArtistsVM::class.java)
        artistsVM.artists.observe(viewLifecycleOwner,  {
            it.apply {
                viewModelAdapter!!.artists = this
            }
        })

        artistsVM.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!artistsVM.isNetworkErrorShown.value!!) {
            Toast.makeText(activity,getString(R.string.network_error), Toast.LENGTH_LONG).show()
            artistsVM.onNetworkErrorShown()
        }
    }
}