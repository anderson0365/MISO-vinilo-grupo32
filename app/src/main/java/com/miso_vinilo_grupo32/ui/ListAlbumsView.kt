package com.miso_vinilo_grupo32.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.miso_vinilo_grupo32.R
import com.miso_vinilo_grupo32.databinding.FragmentListAlbumsViewBinding


class ListAlbumsView : Fragment() {

    private var _binding : FragmentListAlbumsViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListAlbumsViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.albumDetailButton.setOnClickListener {
            val intent = Intent(activity, DetailAlbumView::class.java)

            try {
                val id = binding.albumIdInput.text.toString().toInt()
                intent.putExtra("albumId", id)
                activity?.startActivity(intent)
            }catch (e: Exception){
                inputError()
            }

        }
    }

    private fun inputError() {
        Toast.makeText(activity, getString(R.string.invalid_id), Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}