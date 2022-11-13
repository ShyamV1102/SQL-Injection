package com.example.is_miniproject;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHandler dbhandler;
    EditText usernameInput,passwordInput;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch safeSwitch;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Welcome");
        }
        dbhandler = new DBHandler(this, null, null, 1);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        safeSwitch = findViewById(R.id.safeSwitch);
//        passwordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId== EditorInfo.IME_ACTION_DONE){
//                    signOnClick(v);
//                    return true;
//                }
//                return false;
//            }
//        });
        passwordInput.setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction()== KeyEvent.ACTION_DOWN)
            {
                switch (keyCode)
                {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        signOnClick(v);
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });
    }

    public void signOnClick(View view) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.equals("") || password.equals("")) {
            messageBox("Please fill in username and password!");
        } else {
            boolean safe = safeSwitch.isChecked();
            Student student = null ;
            try
            {
                student = dbhandler.findHandler(username, password,safe);
            }
            catch (Exception e)
            {
                messageBox(e.getMessage());
                return;
            }
            if (student == null) {
                messageBox("Incorrect username or password!");
            } else {
                Intent intent;
                if (student.getId()==99999) {
                    intent = new Intent(context, AllStudent.class);
                } else {
                    intent = new Intent(context, Result.class);
                    intent.putExtra("safe",safe);
                    intent.putExtra("admin",false);
                    intent.putExtra("student", student);
                }
                context.startActivity(intent);
                passwordInput.setText("");
            }
        }

    }

    public void exitOnClick(View v)
    {
        finish();
        System.exit(0);
    }

    public void resetOnClick(View v)
    {
        deleteDatabase(dbhandler.getDatabaseName());
        usernameInput.setText("");
        passwordInput.setText("");
    }

    public void messageBox(String title)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(title);
        dialog.setPositiveButton("OK", (dialogInterface, i) -> {});
        dialog.show();
    }


}