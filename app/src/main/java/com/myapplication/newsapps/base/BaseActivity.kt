package com.myapplication.newsapps.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.myapplication.newsapps.util.ProgressDialog

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    private lateinit var progressDialog: Dialog
//    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog.progressDialog(this)
    }

    abstract fun getBinding(layoutInflater: LayoutInflater): VB

    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }

    fun addFragment(manager: FragmentManager, fragment: Fragment, frameId: Int) {
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun showProgressDialog() {
        progressDialog.show()
    }

    fun dismissProgressDialog() {
        progressDialog.dismiss()
    }

    fun showToast(msg: String?) {
        msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
    }

    fun showSnackBar(message: String?) {
        message?.let {
            Snackbar.make(findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG).show()
        }
    }
}