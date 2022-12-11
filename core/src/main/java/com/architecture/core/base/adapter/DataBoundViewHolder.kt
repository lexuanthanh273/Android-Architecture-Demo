package com.architecture.core.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * A generic ViewHolder that works with a [ViewDataBinding].
 * @param <T> The type of the ViewDataBinding.
</T> */
open class DataBoundViewHolder<out T : ViewBinding> constructor(val binding: T) :
    RecyclerView.ViewHolder(binding.root)
