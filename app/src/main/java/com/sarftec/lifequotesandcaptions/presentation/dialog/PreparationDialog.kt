package com.sarftec.lifequotesandcaptions.presentation.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.sarftec.lifequotesandcaptions.databinding.LayoutAppPreparationDialogBinding

class PreparationDialog(context: Context) : AlertDialog(context) {

    init {
        setView(
            LayoutAppPreparationDialogBinding.inflate(
                LayoutInflater.from(context)
            ).root
        )
    }
}