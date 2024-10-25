package com.example.sharedpreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextId;
    private Button buttonSave;
    private TextView textViewInfo;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextId = findViewById(R.id.editTextId);
        buttonSave = findViewById(R.id.buttonSave);
        textViewInfo = findViewById(R.id.textViewInfo);

        sharedPreferences = getSharedPreferences("StudentInfo", MODE_PRIVATE);

        displayStudentInfo();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String id = editTextId.getText().toString().trim();

                if (name.isEmpty() || id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (sharedPreferences.contains(id)) {
                        Toast.makeText(MainActivity.this, "Thông tin với thời gian này đã tồn tại. Vui lòng chọn thời gian khác.", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(id, name);
                        editor.apply();

                        Toast.makeText(MainActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                        displayStudentInfo();
                    }
                }
            }
        });
    }

    private void displayStudentInfo() {
        StringBuilder info = new StringBuilder("List to do:\n");
        for (String id : sharedPreferences.getAll().keySet()) {
            info.append("Thời gian: ").append(id)
                    .append("\nCông việc: ").append(sharedPreferences.getString(id, ""))
                    .append("\n\n");
        }
        textViewInfo.setText(info.toString());
    }
}
