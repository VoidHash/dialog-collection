# DialogCollection
A collections of dialogs ready to be use in any Android project

### Toast
```kotlin
Toast.makeText(this, message, Toast.LENGTH_LONG).show()
```

### Snackbar
```kotlin
Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG)
.setAction("My Action Str", myActionFunc()).show()
```

### Alert Dialog
```kotlin
//Instantiate builder variable
val builder = AlertDialog.Builder(view.context)

// set dialog title
builder.setTitle("This is your alert dialog title")

//set content message
builder.setMessage("You may have your message content here!\n\n So nice!")

//set a icon for your dialog
builder.setIcon(R.drawable.ic_android_green_24dp)

//set positive button
builder.setPositiveButton("Accept") { dialog, id ->
    Toast.makeText(this, "You accepted this dialog",Toast.LENGTH_SHORT).show()
}

//set negative button
builder.setNegativeButton("Refuse") { dialog, id ->
    Toast.makeText(this, "You NOT accepted this dialog",Toast.LENGTH_SHORT).show()
}

//set neutral button
builder.setNeutralButton("Next time") {dialog, id->
    Toast.makeText(this, "Leave to next time!",Toast.LENGTH_SHORT).show()
}
builder.show()
```

### List Dialog
```kotlin
val items = arrayOf("Elden Ring", "Doom", "Metroid", "Mega Man X", "Final Fantasy Tatics")
AlertDialog.Builder(this)
    .setTitle("Click to chose a game")
    
    //set a list of item to be displayed
    .setItems(items) { dialog, position ->
        Toast.makeText(applicationContext, "${items[position]} has been chose", Toast.LENGTH_LONG).show()
    }
    .setNegativeButton("Cancel") { dialog, position ->
        Toast.makeText(applicationContext, "Closing dialog", Toast.LENGTH_LONG).show()
    }
    .show()
```

### Checkbox Dialog
```kotlin
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
```

### Custom Dialog
```kotlin
val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
val customDialog = AlertDialog.Builder(this)
    .setView(dialogView)
    .show()
val edtName = dialogView.findViewById<EditText>(R.id.edtName)

val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
btnCancel.setOnClickListener { customDialog.dismiss() }

val btnSubmit = dialogView.findViewById<Button>(R.id.btnSubmit)
btnSubmit.setOnClickListener {
    if(edtName.text.length == 0) {
        edtName.error = "Required"
    } else {
        Toast.makeText(this,"Your name is: "+edtName.text, Toast.LENGTH_LONG).show()
        customDialog.dismiss()
    }
}
```

### Date Picker Dialog
Check [DatePickerHelper.kt](https://github.com/VoidHash/DialogCollection/blob/master/app/src/main/java/com/voidhash/dialogcollection/DatePickerHelper.kt)
```kotlin
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
```

### Date Picker Dialog
Check [TimePickerHelper.kt](https://github.com/VoidHash/DialogCollection/blob/master/app/src/main/java/com/voidhash/dialogcollection/TimePickerHelper.kt)
```kotlin
val timePicker = TimePickerHelper(this, false)
val cal = Calendar.getInstance()
val h = cal.get(Calendar.HOUR_OF_DAY)
val m = cal.get(Calendar.MINUTE)

timePicker.showDialog(h, m, object : TimePickerHelper.Callback {
    override fun onTimeSelected(hourOfDay: Int, minute: Int) {
        val hourStr = if (hourOfDay < 10) "0${hourOfDay}" else "${hourOfDay}"
        val minuteStr = if (minute < 10) "0${minute}" else "${minute}"
        Toast.makeText(this@MainActivity,"Time Picked -> ${hourStr}:${minuteStr}",
            Toast.LENGTH_LONG).show()
    }
})
```
