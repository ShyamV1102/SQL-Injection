package com.example.is_miniproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    DBHandler dbHandler;
    EditText idInput,feeInput, nicknameInput, addressInput, emailInput, phoneInput,birthdayInput,nameInput,applicationInput,passwordInput;
    Button addButton;
    final Calendar myCalendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar ab = getSupportActionBar();


        if (ab!=null)
        {
            ab.setTitle("Add a New Student");
        }

        dbHandler = new DBHandler(this,null,null,1);

        idInput = findViewById(R.id.idInput);
        nameInput = findViewById(R.id.nameInput);
        feeInput = findViewById(R.id.feeInput);
        applicationInput = findViewById(R.id.applicationInput);
        passwordInput = findViewById(R.id.passwordInput);
        nicknameInput = findViewById(R.id.nicknameInput);
        addressInput = findViewById(R.id.addressInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        birthdayInput = findViewById(R.id.birthdayInput);

        addButton = findViewById(R.id.addButton);

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birthdayInput.setText(dateFormat.format(myCalendar.getTime()));
        };

        birthdayInput.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

    }

    public Student checkInput()
    {
        int id,fee;
        String name, password;
        Date birthday;
        try {
            id = Integer.parseInt(idInput.getText().toString().trim());
            fee = Integer.parseInt(feeInput.getText().toString().trim());
        }
        catch (Exception e)
        {
            messageBox("Id and Fee can only be integers!");
            return null;
        }

        try {
            name = nameInput.getText().toString();
            password = passwordInput.getText().toString();
        }
        catch (Exception e)
        {
            messageBox("Name and Password should not be empty!");
            return null;
        }

        try{
            birthday = dateFormat.parse(birthdayInput.getText().toString());}
        catch (Exception e){
            messageBox("Pick a birthday!");
            return null;
        }

        return new Student(id,name, password,applicationInput.getText().toString(),
                nicknameInput.getText().toString(),
                phoneInput.getText().toString(),
                emailInput.getText().toString(),
                addressInput.getText().toString(),
                fee,dateFormat.format(birthday));
    }

    public void addOnClick(View v)
    {
        Student student = checkInput();
        if(student!=null)
        {
            try{
                dbHandler.addInterface(student);
                Intent intent = new Intent(this,AllStudent.class);
                startActivity(intent);
            }
            catch (Exception e)
            {
                messageBox(e.getMessage());
            }
        }
    }

    public void messageBox(String title)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(title);
        dialog.setPositiveButton("OK", (dialogInterface, i) -> {});
        dialog.show();
    }


}