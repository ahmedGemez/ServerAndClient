package com.example.clientapp.presentation.ui.homefragment

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableField

class SelectedValueBindingAdapter {
    companion object {

        @BindingAdapter("spinnerList")
        @JvmStatic fun setSpinnerList(spinner: Spinner, contentList: List<String>) {
            val adapter = ArrayAdapter(
                spinner.context,
                android.R.layout.simple_spinner_dropdown_item,
                contentList!!
            )
            spinner.adapter = adapter

        }

        @BindingAdapter("selectedValueAttrChanged")
        @JvmStatic fun bindSpinnerData(
            pAppCompatSpinner: Spinner,
            newTextAttrChanged: InverseBindingListener
        ) {
            pAppCompatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    newTextAttrChanged.onChange()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        }


        @BindingAdapter("selectedValue")
        @JvmStatic fun setRealValue(pAppCompatSpinner: AppCompatSpinner, newSelectedValue: Int) {

            if(pAppCompatSpinner.selectedItemPosition != newSelectedValue)
                pAppCompatSpinner.setSelection(newSelectedValue)

        }


        @BindingAdapter("selectedInsideValue")
        @JvmStatic fun setRealValue(pAppCompatSpinner: AppCompatSpinner,value:String?) {

        }


        @InverseBindingAdapter(attribute = "selectedValue")
        @JvmStatic fun captureSelectedValue(pAppCompatSpinner: AppCompatSpinner): Int {
            return pAppCompatSpinner.selectedItemPosition
        }
    }

}