package com.example.whatwherewhenquestions.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.whatwherewhenquestions.R
import com.example.whatwherewhenquestions.databinding.FragmentMainBinding

class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

        Log.d("MyLog", "HELLO MFR")

        binding.findQuestions.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_questionsFragment)
        }

        binding.createTraining.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_trainingFragment)
        }

        binding.favoriteButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_favoritesFragment)
        }

        return binding.root
    }
}