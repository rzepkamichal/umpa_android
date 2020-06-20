package pl.mrz.umpa.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import pl.mrz.umpa.R

class ProgressDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val contentView = layoutInflater.inflate(R.layout.progress_circle_fullscreen, null)

        return AlertDialog.Builder(activity)
            .setView(contentView)
            .create().also { it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }
            .also{
                isCancelable = false
            }


    }
}