package com.example.innogeeks_team_project

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
//
//class customDialog {
//}

class customDialog(val myActivity: Activity) {
    private lateinit var isDialog: AlertDialog

    @SuppressLint("InflateParams")
    fun dialogRunning() {
        val layInf = myActivity.layoutInflater
        val myDialogView = layInf.inflate(R.layout.loading_dialog, null)
        val builder = AlertDialog.Builder(myActivity)
        builder.setView(myDialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog = builder.show()
    }

    fun dialogClose() {
        isDialog.dismiss()
    }
}