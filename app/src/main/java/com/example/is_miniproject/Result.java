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
import android.widget.Switch;
import android.widget.TableRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Result extends AppCompatActivity {


    //    EditText idInput;
    EditText idInput,feeInput, nicknameInput, addressInput, emailInput, phoneInput,birthdayInput,nameInput,applicationInput,passwordInput;
    Student student;
    DBHandler dbHandler;
    final Calendar myCalendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Button btnUpdate, btnSignOut, btnDelete;
    TableRow deleteRow;
    boolean admin,safe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActionBar ab = getSupportActionBar();


        if (ab!=null)
        {
            ab.setTitle("Profile");
        }
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

        btnUpdate = findViewById(R.id.btnAdd);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnDelete = findViewById(R.id.btnDelete);

        deleteRow = findViewById(R.id.deleteRow);

        dbHandler = new DBHandler(this,null,null,1);




        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birthdayInput.setText(dateFormat.format(myCalendar.getTime()));
        };

        birthdayInput.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog(Result.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        student = (Student) getIntent().getSerializableExtra("student");
        admin = getIntent().getBooleanExtra("admin",false);
        safe = getIntent().getBooleanExtra("safe",false);



        nameInput.setText(student.getName());
        idInput.setText(String.valueOf(student.getId()));
        addressInput.setText(student.getAddress());
        emailInput.setText(student.getEmail());
        phoneInput.setText(student.getPhone());
        feeInput.setText(String.valueOf(student.getFee()));
        birthdayInput.setText(dateFormat.format(student.getBirthday()));
        applicationInput.setText(student.getApplication());
        passwordInput.setText(student.getPassword());
        nicknameInput.setText(student.getNickname());

        idInput.setEnabled(false);

        if(admin)
        {
            deleteRow.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(this::deleteOnClick);
            btnSignOut.setText("RETURN");
            btnSignOut.setOnClickListener(v -> {
                Intent intent = new Intent(this,AllStudent.class);
                startActivity(intent);});
            btnUpdate.setOnClickListener(this::fullUpdateProfile);
        }
        else
        {
            deleteRow.setVisibility(View.GONE);
            feeInput.setEnabled(false);
            applicationInput.setEnabled(false);
            nameInput.setEnabled(false);
            birthdayInput.setEnabled(false);
            btnSignOut.setOnClickListener(v -> finish());
            btnUpdate.setOnClickListener(this::partialUpdateProfile);
        }

    }

    public void partialUpdateProfile(View v)
    {
        student.setNickname(nicknameInput.getText().toString());
        student.setAddress(addressInput.getText().toString());
        student.setPassword(passwordInput.getText().toString());
        student.setPhone(phoneInput.getText().toString());
        student.setEmail(emailInput.getText().toString());

        try {
            if(safe)
            {
                dbHandler.safePartialUpdateHandler(student);
            }
            else {
                dbHandler.partialUpdateHandler(student);
            }
            messageBox("Update Successfully!");
        } catch (Exception e) {
            messageBox(e.getMessage());
        }
    }

    public void fullUpdateProfile(View v)
    {
        student.setNickname(nicknameInput.getText().toString());
        student.setAddress(addressInput.getText().toString());
        student.setPassword(passwordInput.getText().toString());
        student.setPhone(phoneInput.getText().toString());
        student.setEmail(emailInput.getText().toString());
        student.setName(nameInput.getText().toString());
        student.setApplication(applicationInput.getText().toString());
        student.setFee(Integer.parseInt(feeInput.getText().toString().trim()));
        try {student.setBirthday(dateFormat.parse(birthdayInput.getText().toString()));}
        catch (Exception e) {
            messageBox(e.getMessage());
        }
        if(dbHandler.fullUpdateHandler(student))
        {
            messageBox("Update Successfully!");
        }
        else
        {
            messageBox("Update Fail!");
        }

    }

    public void deleteOnClick(View v)
    {
        if(dbHandler.deleteHandler(student))
        {
            messageBox("Delete Successfully!");
            Intent intent = new Intent(this,AllStudent.class);
            startActivity(intent);

        }
        else
        {
            messageBox("Delete Fail!");
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