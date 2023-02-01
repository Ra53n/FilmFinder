package com.example.filmfinder.presentation.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.example.filmfinder.databinding.ContentProviderFragmentBinding


class ContentProviderFragment : Fragment() {
    private var _binding: ContentProviderFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContentProviderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun checkPermission() {
        with(requireContext()) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    showDialog()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private val REQUEST_CODE = 111

    private fun requestPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
            ), REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (permissions.contains(Manifest.permission.READ_CONTACTS) && permissions.contains(
                    Manifest.permission.CALL_PHONE
                )
            ) {
                when {
                    grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED -> getContacts()
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> showDialog()
                    shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> showDialog()
                    else -> {
                        Toast.makeText(requireContext(), "Need permissions!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к кнотактам")
            .setMessage("Для просмотра контаков нужно выдать соответствующие разрешения!")
            .setPositiveButton("Предоставить доступ") { _, _ -> requestPermission() }
            .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    @SuppressLint("Range")
    private fun getContacts() {
        context?.let {
            val contentResolver = it.contentResolver
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursor?.let {
                for (i in 0 until cursor.count) {
                    cursor.moveToPosition(i)
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val id = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID)
                    )
                    val phones = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = " + id,
                        null,
                        null
                    )
                    while (phones?.moveToNext()!!) {
                        val phoneNumber: String =
                            phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        addView(name, phoneNumber)
                    }
                    phones.close()
                }
            }
        }
    }

    private fun addView(name: String, phone: String) {
        binding.containerForContacts.addView(TextView(requireContext()).apply {
            text = "$name - $phone"
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                160
            )
            setPadding(16)
            textSize = 24f
            setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
                startActivity(intent)
            }
        })
    }

    companion object {
        fun newInstance() = ContentProviderFragment()
    }
}