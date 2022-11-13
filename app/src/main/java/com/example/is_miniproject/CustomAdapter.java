package com.example.is_miniproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Student> students;

    String id_icon,phone_icon,birthday_icon,email_icon,address_icon;

    public CustomAdapter(Context context, Activity activity, ArrayList<Student> students) {
        this.context = context;
        this.activity = activity;
        this.students = students;
        id_icon = context.getResources().getString(R.string.id_card_icon);
        phone_icon = context.getResources().getString(R.string.phone_icon);
        birthday_icon = context.getResources().getString(R.string.birthday_icon);
        email_icon = context.getResources().getString(R.string.email_icon);
        address_icon = context.getResources().getString(R.string.address_icon);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.activity_data_viewer,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        holder.nameViewer.setText(students.get(position).getName());
        holder.idViewer.setText(id_icon+"  " +String.valueOf(students.get(position).getId()));
        holder.applicationViewer.setText("Application:"+students.get(position).getApplication());
        holder.feeViewer.setText("(₹"+String.valueOf(students.get(position).getFee())+"/yr)");
        holder.nicknameViewer.setText(students.get(position).getNickname());
        holder.addressViewer.setText(address_icon+ "  " + students.get(position).getAddress());
        holder.phoneViewer.setText(phone_icon+"  " + students.get(position).getPhone());
        holder.emailViewer.setText(email_icon+"  " + students.get(position).getEmail());
        holder.birthdayViewer.setText(birthday_icon+"  " + dateFormat.format(students.get(position).getBirthday()));
        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Result.class);
            intent.putExtra("admin",true);
            intent.putExtra("student",students.get(position));
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameViewer, idViewer, applicationViewer, feeViewer, nicknameViewer, phoneViewer, addressViewer, emailViewer, birthdayViewer;
        LinearLayout mainLayout;
        Typeface solidFont,regularFont;

        MyViewHolder(@NonNull View itemViewer)
        {
            super(itemViewer);

            solidFont = Typeface.createFromAsset(context.getAssets(), "fa-solid-900.ttf" );
            regularFont = Typeface.createFromAsset(context.getAssets(), "fa-regular-400.ttf" );

            nameViewer = itemViewer.findViewById(R.id.nameViewer);
            idViewer = itemViewer.findViewById(R.id.idViewer);
            applicationViewer = itemViewer.findViewById(R.id.applicationViewer);
            feeViewer = itemViewer.findViewById(R.id.feeViewer);
            nicknameViewer = itemViewer.findViewById(R.id.nicknameViewer);
            phoneViewer = itemViewer.findViewById(R.id.phoneViewer);
            addressViewer = itemViewer.findViewById(R.id.addressViewer);
            emailViewer = itemViewer.findViewById(R.id.emailViewer);
            birthdayViewer = itemViewer.findViewById(R.id.birthdayViewer);
            mainLayout = itemViewer.findViewById(R.id.mainLayout);

            Animation translateAnimation = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            mainLayout.setAnimation(translateAnimation);

            idViewer.setTypeface(regularFont);
            phoneViewer.setTypeface(solidFont);
            addressViewer.setTypeface(solidFont);
            birthdayViewer.setTypeface(solidFont);
            emailViewer.setTypeface(regularFont);
        }
    }
}
