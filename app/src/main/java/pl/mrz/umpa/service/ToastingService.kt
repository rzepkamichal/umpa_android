package pl.mrz.umpa.service

import android.content.Context
import android.widget.Toast
import pl.mrz.umpa.R

object ToastingService {

    fun toastDataSaveFailed(ctx: Context) {
        Toast.makeText(ctx, R.string.toast_data_save_failed, Toast.LENGTH_LONG).show()
    }

    fun toastConnectionError(ctx: Context) {
        Toast.makeText(ctx, R.string.toast_connection_error, Toast.LENGTH_LONG).show()
    }
}