package com.example.whatwherewhenquestions.ui.fragments

import android.animation.LayoutTransition
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatwherewhenquestions.data.repository.QuestionsRepository
import com.example.whatwherewhenquestions.databinding.FragmentQuestionsBinding
import com.example.whatwherewhenquestions.ui.adapters.QuestionListRecyclerViewAdapter
import com.example.whatwherewhenquestions.ui.viewmodels.QuestionsFragmentViewModel
import com.google.android.material.snackbar.Snackbar

class QuestionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[QuestionsFragmentViewModel::class.java]

        binding.cardConstraintLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        binding.radioButton0.isChecked = true

        binding.button.setOnClickListener{
            viewModel.getQuestions(viewLifecycleOwner)
            viewModel.types[0] = binding.checkbox1.isChecked
            viewModel.types[1] = binding.checkbox2.isChecked
            viewModel.types[2] = binding.checkbox3.isChecked
            viewModel.types[3] = binding.checkbox4.isChecked
            viewModel.types[4] = binding.checkbox5.isChecked
            viewModel.types[5] = binding.checkbox6.isChecked
            viewModel.complexity = binding.radioGroup.checkedRadioButtonId - binding.radioButton0.id
            viewModel.limit =
                try {
                    binding.numberEditText.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
            binding.recyclerView.visibility = View.GONE
            binding.loadingIndicator.visibility = View.VISIBLE
        }

        viewModel.questions.observe(viewLifecycleOwner) {
            binding.loadingIndicator.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = QuestionListRecyclerViewAdapter(viewModel.questions.value)
        }

        binding.filter.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.cardConstraintLayout)

            if (viewModel.expandedFilter) {
                viewModel.expandedFilter = false
                binding.radioGroup.visibility = View.GONE
                binding.chechboxLinearLayout.visibility = View.GONE
                binding.startDate.visibility = View.GONE
                binding.endDate.visibility = View.GONE
                binding.choosePeriod.visibility = View.GONE
                binding.filter.text = "Больше настроек"
            } else {
                viewModel.expandedFilter = true
                binding.radioGroup.visibility = View.VISIBLE
                binding.chechboxLinearLayout.visibility = View.VISIBLE
                binding.startDate.visibility = View.VISIBLE
                binding.endDate.visibility = View.VISIBLE
                binding.choosePeriod.visibility = View.VISIBLE
                binding.filter.text = "Меньше настроек"
            }
        }

        return binding.root
    }

}