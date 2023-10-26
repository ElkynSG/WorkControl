package com.esilva.equiposunidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.esilva.equiposunidos.Setting.SettingActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setView();
    }

    private void setView() {
        btSetting = findViewById(R.id.settings);
        btSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.settings:
                startActivity(new Intent(MenuActivity.this, SettingActivity.class));
                break;
            default:
                break;
        }
    }
}