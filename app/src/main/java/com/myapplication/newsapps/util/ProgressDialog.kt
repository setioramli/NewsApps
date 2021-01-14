package com.myapplication.newsapps.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.myapplication.newsapps.databinding.LayoutLoadingDialogBinding

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
class ProgressDialog {
    companion object {
        @SuppressLint("InflateParams")
        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val view = LayoutLoadingDialogBinding.inflate(LayoutInflater.from(context))
            dialog.setContentView(view.root)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }
    }
}