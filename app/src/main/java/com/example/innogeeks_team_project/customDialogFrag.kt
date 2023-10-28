//package com.example.innogeeks_team_project
//
//class customDialogFrag {
//}

package com.example.innogeeks_team_project

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment

//
//class customDialog {
//}

class customDialogFrag(val myFrag: Fragment) {
    private lateinit var isDialog: AlertDialog

    @SuppressLint("InflateParams")
    fun dialogRunning() {
        val layInf = myFrag.layoutInflater
        val myDialogView = layInf.inflate(R.layout.loading_dialog, null)
        val builder = AlertDialog.Builder(myDialogView.context)
        builder.setView(myDialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog = builder.show()
    }

    fun dialogClose() {
        isDialog.dismiss()
    }
}