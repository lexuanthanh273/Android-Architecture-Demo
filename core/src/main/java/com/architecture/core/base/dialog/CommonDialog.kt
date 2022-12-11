package com.architecture.core.base.dialog

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.architecture.core.R
import com.architecture.core.base.BaseDialog
import com.architecture.core.databinding.CommonDialogBinding
import com.architecture.core.ext.gone
import com.architecture.core.ext.visible
import com.architecture.core.pushdown.PushDownAnim
import java.lang.Integer.min

class CommonDialog(context: Context, builder: Builder) : BaseDialog(context) {

    private var commonDialogType: CommonDialogType = builder.commonDialogType
    private var isDialogCancelable = builder.cancelable
    private var negativeButton = builder.negativeButton
    private var positiveButton = builder.positiveButton

    private var tittle: String = builder.tittle
    private var message: String = builder.message
    private var messageSpan = builder.messageSpan
    private var onPositive: (() -> Unit)? = builder.onPositive
    private var onNegative: (() -> Unit)? = builder.onNegative
    private var onDismiss: (() -> Unit)? = builder.onDismiss

    private var _binding: CommonDialogBinding? = null
    private val binding get() = _binding!!

    override val isCancelable: Boolean
        get() = isDialogCancelable

    override fun setContentView() {
        _binding = CommonDialogBinding.inflate(
            LayoutInflater.from(context), null, false
        )
        setContentView(binding.root)
    }

    override fun initView() {
//        val isTablet = context.resources.getBoolean(R.bool.is_tablet)
        val isTablet = false
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
//        val dialogWidth = min(screenWidth, screenHeight) * if (isTablet) TABLET_RATIO_WIDTH_DIALOG else PHONE_RATIO_WIDTH_DIALOG
        val dialogWidth = if (isTablet) {
            min(screenWidth, screenHeight) * TABLET_RATIO_WIDTH_DIALOG
        } else {
            min(
                screenWidth,
                screenHeight
            ) - context.resources.getDimensionPixelOffset(R.dimen._24sdp)
        }
        window?.setLayout(dialogWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)

        when (commonDialogType) {
            is CommonDialogType.Information -> {
                (binding.ivIcon.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.START
                (binding.tvTittle.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.START
                (binding.tvMessage.layoutParams as LinearLayout.LayoutParams).gravity =
                    Gravity.START
                binding.ivIcon.setImageResource(R.drawable.ic_all_common_dialog_information)
                binding.tvPositive.setBackgroundResource(R.drawable.bg_all_gradient_button_24)
                binding.tvNegative.setBackgroundResource(R.drawable.bg_all_stroke_purple_24)
                binding.tvNegative.setTextColor(context.getColor(R.color.support_08))
            }
            is CommonDialogType.Warning -> {
                (binding.ivIcon.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER
                (binding.tvTittle.layoutParams as LinearLayout.LayoutParams).gravity =
                    Gravity.CENTER
                (binding.tvMessage.layoutParams as LinearLayout.LayoutParams).gravity =
                    Gravity.CENTER
                binding.ivIcon.setImageResource(R.drawable.ic_all_common_dialog_warning)
                binding.tvPositive.setBackgroundResource(R.drawable.bg_all_button_warning)
                binding.tvNegative.setBackgroundResource(R.drawable.bg_all_button_cancel_warning)
                binding.tvNegative.setTextColor(context.getColor(R.color.support_02))
            }
            is CommonDialogType.Error -> {
                (binding.ivIcon.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER
                (binding.tvTittle.layoutParams as LinearLayout.LayoutParams).gravity =
                    Gravity.CENTER
                (binding.tvMessage.layoutParams as LinearLayout.LayoutParams).gravity =
                    Gravity.CENTER
                binding.ivIcon.setImageResource(R.drawable.ic_all_common_dialog_error)
                binding.tvPositive.setBackgroundResource(R.drawable.bg_all_button_error)
                binding.tvNegative.setBackgroundResource(R.drawable.bg_all_stroke_red_24)
                binding.tvNegative.setTextColor(context.getColor(R.color.support_01))
            }
            else -> {
                binding.tvPositive.setBackgroundResource(R.drawable.bg_all_gradient_button_24)
                binding.tvNegative.setBackgroundResource(R.drawable.bg_all_stroke_purple_24)
                binding.tvNegative.setTextColor(context.getColor(R.color.support_08))
            }

        }

        binding.tvTittle.text = tittle
        if (messageSpan != null) {
            binding.tvMessage.text = messageSpan
            binding.tvMessage.movementMethod = LinkMovementMethod.getInstance()
            binding.tvMessage.highlightColor = Color.TRANSPARENT
        } else {
            binding.tvMessage.text = message
        }

        if (positiveButton.isNotEmpty()) {
            binding.tvPositive.text = positiveButton
            binding.tvPositive.visible()
        } else {
            binding.tvPositive.gone()
        }
        if (negativeButton.isNotEmpty()) {
            binding.tvNegative.text = negativeButton
            binding.tvNegative.visible()
        } else {
            binding.tvNegative.gone()
        }
        PushDownAnim.setPushDownAnimTo(binding.tvPositive).setOnClickListener {
            onPositive?.invoke()
            dismiss()
        }
        PushDownAnim.setPushDownAnimTo(binding.tvNegative).setOnClickListener {
            onNegative?.invoke()
            dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
        onDismiss?.invoke()
        _binding = null
    }

    class Builder {
        internal var commonDialogType: CommonDialogType = CommonDialogType.Information
        internal var tittle: String = ""
        internal var message: String = ""
        internal var messageSpan: SpannableString? = null
        internal var cancelable: Boolean = true
        internal var negativeButton: CharSequence = ""
        internal var positiveButton: CharSequence = ""
        internal var onPositive: (() -> Unit)? = null
        internal var onNegative: (() -> Unit)? = null
        internal var onDismiss: (() -> Unit)? = null
        internal var onUpdateAccount: (() -> Unit)? = null

        fun setCommonDialogType(commonDialogType: CommonDialogType): Builder {
            this.commonDialogType = commonDialogType
            return this
        }

        fun setTittle(tittle: String): Builder {
            this.tittle = tittle
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setMessage(message: SpannableString): Builder {
            this.messageSpan = message
            return this
        }

        fun setCancelable(isCancelable: Boolean): Builder {
            this.cancelable = isCancelable
            return this
        }

        fun setOnPositive(positiveButton: CharSequence, onPositive: (() -> Unit)? = null): Builder {
            this.onPositive = onPositive
            this.positiveButton = positiveButton
            return this
        }

        fun setOnNegative(negativeButton: CharSequence, onNegative: (() -> Unit)? = null): Builder {
            this.onNegative = onNegative
            this.negativeButton = negativeButton
            return this
        }

        fun setOnDismiss(onDismiss: () -> Unit): Builder {
            this.onDismiss = onDismiss
            return this
        }

        fun build(context: Context): CommonDialog {
            return CommonDialog(context, this)
        }
    }
}

sealed class CommonDialogType {
    object Error : CommonDialogType()
    object Warning : CommonDialogType()
    object Information : CommonDialogType()
    object None : CommonDialogType()
}
