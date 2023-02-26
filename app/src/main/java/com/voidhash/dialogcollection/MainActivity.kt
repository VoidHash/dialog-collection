package com.voidhash.dialogcollection

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnToast: Button = btnToast
        val btnAlert: Button = btnAlert
        val btnList: Button = btnList
        val btnCheckbox: Button = btnCheckbox
        val btnEditText: Button = btnEditText
        val btnCustom: Button = btnCustom
        val btnDatePicker: Button = btnDatePicker
        val btnTimePicker: Button = btnTimePicker

        btnToast.setOnClickListener {
            showToast("This is a Toast message!")
        }

        btnSnackBar.setOnClickListener {
            showSnackBar("This is a SnackBar message!")
        }

        btnAlert.setOnClickListener {
            showAlertDialog(btnToast.rootView)
        }

        btnList.setOnClickListener {
            showListDialog()
        }

        btnCheckbox.setOnClickListener {
            showCheckboxDialog()
        }

        btnEditText.setOnClickListener {
            showEditTextDialog()
        }

        btnCustom.setOnClickListener {
            showCustomDialog()
        }

        btnDatePicker.setOnClickListener {
            showDatePickerDialog()
        }

        btnTimePicker.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showSnackBar(message: String) {
       Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG)
           .setAction("Call Toast", View.OnClickListener {
            showToast("Snackbar action call me here!")
        }).show()
    }

    private fun showAlertDialog(view: View) {
        //Instantiate builder variable
        val builder = AlertDialog.Builder(view.context)

        // set dialog title
        builder.setTitle("This is your alert dialog title")

        //set content message
        builder.setMessage("You may have your message content here!\n\n So nice!")

        //set a icon for your dialog
        builder.setIcon(R.drawable.ic_android_green_24dp)

        //set positive button
        builder.setPositiveButton(
            "Accept") { dialog, id ->
            Toast.makeText(this, "You accepted this dialog",Toast.LENGTH_SHORT).show()
        }

        //set negative button
        builder.setNegativeButton(
            "Refuse") { dialog, id ->
            Toast.makeText(this, "You NOT accepted this dialog",Toast.LENGTH_SHORT).show()
        }

        //set neutral button
        builder.setNeutralButton("Next time") {dialog, id->
            Toast.makeText(this, "Leave to next time!",Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun showListDialog() {
        val items = arrayOf("Elden Ring", "Doom", "Metroid", "Mega Man X", "Final Fantasy Tatics")
        AlertDialog.Builder(this)
            .setTitle("Click to chose a game")
            .setItems(items) { dialog, position ->
                Toast.makeText(applicationContext, "${items[position]} has been chose", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel") { dialog, position ->
                Toast.makeText(applicationContext, "Closing dialog", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private fun showCheckboxDialog() {
        val items = arrayOf("Elden Ring", "Doom", "Metroid", "Mega Man X", "Final Fantasy Tatics")
        val selectedItems = ArrayList<String>()
        AlertDialog.Builder(this)
            .setTitle("Multiple Choices Dialog")

            .setMultiChoiceItems(items, null) { dialog, position, isChecked ->
                if (isChecked) {
                    selectedItems.add( items[position] )
                } else if (selectedItems.contains(items[position])) {
                    selectedItems.remove(items[position])
                }
            }
            .setPositiveButton("Done") { dialog, position ->
                Toast.makeText(applicationContext,
                    "Selected items: " + Arrays.toString(selectedItems.toArray()),
                    Toast.LENGTH_SHORT).show();
            }
            .show()
    }

    private fun showEditTextDialog() {
        val editText = EditText(this)
        val container = LinearLayout(this)
        container.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(80, 0, 80, 0)
        editText.layoutParams = layoutParams
        container.addView(editText, layoutParams)
        AlertDialog.Builder(this)
            .setTitle("EditText Alert Dialog")
            .setMessage("Please input your name..")
            .setView(container)
            .setPositiveButton("OK") { dialog, which ->
                Toast.makeText(applicationContext, "Your name is ${editText.text.toString()}",
                    Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(applicationContext, "Cancel is pressed", Toast.LENGTH_LONG).show()
            }
            .show()

    }

    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()
        val edtName = dialogView.findViewById<EditText>(R.id.edtName)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            customDialog.dismiss()
        }
        val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            if(edtName.text.length == 0) {
                edtName.error = "Required"
            } else {
                Toast.makeText(this,"Your name is: "+edtName.text, Toast.LENGTH_LONG).show()
                customDialog.dismiss()
            }
        }
    }

    private fun showDatePickerDialog() {

        val datePicker = DatePickerHelper(this)
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)

        datePicker.setMinDate(cal.timeInMillis)

        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                Toast.makeText(this@MainActivity,"Date picked -> ${dayStr}/${monthStr}/${year}",
                    Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun showTimePickerDialog() {

        val timePicker = TimePickerHelper(this, false)

        val cal = Calendar.getInstance()
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)

        timePicker.showDialog(h, m, object : TimePickerHelper.Callback {
            override fun onTimeSelected(hourOfDay: Int, minute: Int) {
                val hourStr = if (hourOfDay < 10) "0${hourOfDay}" else "${hourOfDay}"
                val minuteStr = if (minute < 10) "0${minute}" else "${minute}"
                Toast.makeText(this@MainActivity,"Time Picked -> ${hourOfDay}:${minuteStr}",
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}