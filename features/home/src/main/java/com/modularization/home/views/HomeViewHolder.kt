package com.modularization.home.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.modularization.featurehome.databinding.ItemHomeBinding
import com.modularization.home.HomeViewModel
import com.modularization.model.User

class HomeViewHolder(parent: View): RecyclerView.ViewHolder(parent) {

    private val binding = ItemHomeBinding.bind(parent)

    fun bindTo(user: User, viewModel: HomeViewModel) {
        binding.user = user
        binding.viewmodel = viewModel
    }
}