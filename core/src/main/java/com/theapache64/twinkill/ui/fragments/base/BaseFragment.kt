package com.theapache64.twinkill.ui.fragments.base

import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.theapache64.twinkill.utils.extensions.snackBar

abstract class BaseFragment : Fragment() {

    abstract val viewDataBinding: ViewDataBinding

    fun snack(@StringRes message: Int) {
        viewDataBinding.root.snackBar(message)
    }
}