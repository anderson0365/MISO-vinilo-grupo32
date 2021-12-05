package com.miso_vinilo_grupo32.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.miso_vinilo_grupo32.R
import com.miso_vinilo_grupo32.databinding.FragmentMainViewBinding

class MainViewFragment : Fragment() {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private var _binding : FragmentMainViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var options: List<String>

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMainViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        options = listOf(getString(R.string.albums_tap), getString(R.string.artist_tap))
        pagerAdapter = PagerAdapter(options, childFragmentManager)
        viewPager = binding.pager
        viewPager.adapter = pagerAdapter
        val tabLayout = binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class PagerAdapter(private val options: List<String>,fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int  = options.size

    override fun getItem(i: Int): Fragment {
        val fragment = if (i == 0){
            ListAlbumsView()
        }else{
            ListArtistsView()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return options[position]
    }
}