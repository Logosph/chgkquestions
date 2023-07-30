package com.example.whatwherewhenquestions.ui.adapters

import android.animation.LayoutTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.whatwherewhenquestions.R
import com.example.whatwherewhenquestions.data.models.QuestionModel
import com.example.whatwherewhenquestions.databinding.DownloadedQuestionItemBinding
import com.google.android.material.snackbar.Snackbar

class QuestionListRecyclerViewAdapter(val data: ArrayList<QuestionModel>?) :
    RecyclerView.Adapter<QuestionListRecyclerViewAdapter.QuestionViewHolder>() {


    class QuestionViewHolder(
        val binding: DownloadedQuestionItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        var liked = false;
        var expanded = false;

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = DownloadedQuestionItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        if (data != null) {

            holder.binding.constraintLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

            holder.binding.number.text = "Вопрос ${position + 1}"
            holder.binding.question.text = data[position]?.question
            holder.binding.answer.text = data[position].answer
            holder.binding.comment.text = data[position].comment
            holder.binding.source.text = data[position].source
            holder.binding.answer.visibility = View.GONE
            holder.binding.comment.visibility = View.GONE
            holder.binding.source.visibility = View.GONE

            holder.binding.like.setOnClickListener {
                // TODO: Recording to database
                holder.binding.like.icon = if (holder.liked) {
                    holder.liked = false
                    ContextCompat.getDrawable(it.context, R.drawable.baseline_favorite_border_24)
                } else {
                    holder.liked = true
                    ContextCompat.getDrawable(it.context, R.drawable.baseline_favorite_24)
                }
            }

            holder.binding.showAnswer.setOnClickListener {
//                TransitionManager.beginDelayedTransition(holder.binding.constraintLayout)

                if (holder.expanded) {
                    holder.binding.answer.visibility = View.GONE
                    holder.binding.comment.visibility = View.GONE
                    holder.binding.source.visibility = View.GONE
                    holder.expanded = false
                    holder.binding.showAnswer.text = "Показать ответ"
                } else {
                    holder.binding.answer.visibility = View.VISIBLE
                    holder.binding.comment.visibility = View.VISIBLE
                    holder.binding.source.visibility = View.VISIBLE
                    holder.expanded = true
                    holder.binding.showAnswer.text = "Скрыть ответ"
                }
            }
        }
    }
}