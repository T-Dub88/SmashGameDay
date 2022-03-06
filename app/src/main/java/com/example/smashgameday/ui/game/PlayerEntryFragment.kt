package com.example.smashgameday.ui.game

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smashgameday.R
import com.example.smashgameday.databinding.FragmentPlayerEntryBinding
import com.example.smashgameday.model.GameViewModel

class PlayerEntryFragment : Fragment() {
    private var _binding: FragmentPlayerEntryBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: GameViewModel by activityViewModels()

    // why did this have to be reworked from the default?
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            playerEntryFragment = this@PlayerEntryFragment
        }
    }

    fun createPlayer() {
        // Takes the entered player name and assigns it to a variable.
        val playerName = binding.playerNameText.text.toString().trim()

        // Clears the text field after using the entry.
        binding.playerNameText.text?.clear()

        // If blank returns nothing.
        if (playerName.isBlank()) {
            val toast = Toast.makeText(activity, getString(R.string.enter_name), Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }

        val toastText = getString(R.string.player_added, playerName)

        // Creates a player instance for the entered name.
        sharedViewModel.createPlayerInstance(playerName)
        sharedViewModel.displayPlayerNames()
        hideKeyboard()

        // Creates and displays a toast for the new player.
        val toast = Toast.makeText(activity, toastText, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
    }

    // Navigates to the stat entry fragment.
    fun startGameDay() {
        findNavController().navigate(R.id.action_playerEntryFragment_to_statEntryFragment)
    }

    // Function used for removing a player.
    fun removePlayer() {
        // Assigns entered text to the current player name and clears edittext.
        val playerName = binding.playerNameText.text.toString().trim()
        binding.playerNameText.text?.clear()

        // If blank returns nothing.
        if (playerName.isBlank()) {
            val toast = Toast.makeText(activity, getString(R.string.enter_name), Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }

        // Determines the current number of players.
        val numberOfPlayers = sharedViewModel.playerList.count()
        Log.i("number", numberOfPlayers.toString())

        sharedViewModel.deletePlayerInstance(playerName)
        sharedViewModel.displayPlayerNames()

        Log.i("number", numberOfPlayers.toString())
        Log.i("number", sharedViewModel.playerList.count().toString())

        // Checks to see if a player was removed and displays appropriate result.
       val toastText = if (numberOfPlayers > sharedViewModel.playerList.count()) {
           getString(R.string.player_removed, playerName)
        } else {
            getString(R.string.player_not_found, playerName)
        }

        // Show the toast for removal of player or failure to remove player.
        val toast = Toast.makeText(activity, toastText, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        hideKeyboard()
    }

    // Two functions used for hiding the keyboard.
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