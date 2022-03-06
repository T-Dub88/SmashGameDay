package com.example.smashgameday.ui.game

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.smashgameday.R
import com.example.smashgameday.adapter.ItemAdapter
import com.example.smashgameday.databinding.FragmentStatEntryBinding
import com.example.smashgameday.model.GameViewModel
import com.example.smashgameday.statistics.GameStats


class StatEntryFragment : Fragment() {
    private lateinit var adapter: ItemAdapter
    private var _binding: FragmentStatEntryBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            statEntryFragment = this@StatEntryFragment
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(sharedViewModel.playerList as ArrayList<GameStats>, this)
        adapter = recyclerView.adapter as ItemAdapter
    }

    // Method for ending a round.
    fun endRound() {
        sharedViewModel.increaseRoundNumber()
        sharedViewModel.saveStats()
//        adapter.clearEntries()

        // Temporary way to clear the entries.
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        if (recyclerView != null) {
            recyclerView.adapter = ItemAdapter(sharedViewModel.playerList as ArrayList<GameStats>, this)
        }
        if (recyclerView != null) {
            adapter = recyclerView.adapter as ItemAdapter
        }

        hideKeyboard()
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}