package com.miso_vinilo_grupo32.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.miso_vinilo_grupo32.R
import com.miso_vinilo_grupo32.databinding.FragmentListAlbumsViewBinding


class ListAlbumsView : Fragment() {

    private var _binding : FragmentListAlbumsViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var detail_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListAlbumsViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        detail_button = view.findViewById(R.id.album_detail_button)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detail_button.setOnClickListener {
                val intent = Intent(activity, DetailAlbumView::class.java)
                intent.putExtra("albumId", 101)
                activity?.startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}