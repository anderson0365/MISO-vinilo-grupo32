package com.miso_vinilo_grupo32.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miso_vinilo_grupo32.databinding.FragmentListArtistViewBinding
import com.miso_vinilo_grupo32.models.SimpleArtist
import com.miso_vinilo_grupo32.ui.adapters.ListArtistsAdapter
import com.miso_vinilo_grupo32.viewmodels.ListArtistVM

class ListArtistView : Fragment() {

    private var _binding : FragmentListArtistViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var ArtistsVM : ListArtistVM
    private lateinit var viewModelAdapter: ListArtistsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        ArtistsVM = ViewModelProvider(this, ListArtistVM.Factory(activity.application)).get(ListArtistVM::class.java)
        ArtistsVM.artists.observe(viewLifecycleOwner, Observer<MutableList<SimpleArtist>> {
            it.apply {
                viewModelAdapter!!.artists = this
            }
        })

        ArtistsVM.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!ArtistsVM.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            ArtistsVM.onNetworkErrorShown()
        }
    }
}