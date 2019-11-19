package com.mustiko.belajar.myintentapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int REQUEST_CODE = 100;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo 2
        Button btnMoveActivity = findViewById(R.id.btn_move_activity);
        btnMoveActivity.setOnClickListener(this);

        Button btnMoveWithDataActivity = findViewById(R.id.btn_move_activity_data);
        btnMoveWithDataActivity.setOnClickListener(this);

        // todo 9
        Button btnMoveWithObject = findViewById(R.id.btn_move_activity_object);
        btnMoveWithObject.setOnClickListener(this);

        // todo 13
        Button btnDialPhone = findViewById(R.id.btn_dial_number);
        btnDialPhone.setOnClickListener(this);

        // todo 19
        Button btnMoveForResult = findViewById(R.id.btn_move_for_result);
        btnMoveForResult.setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_move_activity:
                Intent moveIntent = new Intent(MainActivity.this, MoveActivity.class);
                startActivity(moveIntent);
                break;
            case R.id.btn_move_activity_data:
                // todo 7
                // Perbedaan mendasar antara memindahkan Activity dengan membawa data atau tidak, adalah dengan menempatkan data ke obyek Intent
                // putExtra() mengirimkan data bersamaa dengan obyek intent dan menampung data dengan pasangan key-value
                Intent moveWithDataIntent = new Intent(MainActivity.this, MoveWithDataActivity.class);
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Latihan Android");
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 1);
                startActivity(moveWithDataIntent);
                break;

            case R.id.btn_move_activity_object:
                // todo 11
                // mengirim banyak data dengan berbagai macam tipe data menggunakan pojo dan dibungkus dengan parcelable
                // sehingga data yang benyak tersebut hanya menjadi 1 objek parcelable ketika di kirimkan melalu intent
                // (tidak mengirim data satu persatu berdasarkan tipe data dari data nya)
                Person person = new Person();
                person.setName("nama kamu");
                person.setAge(1);
                person.setEmail("email@email.com");
                person.setCity("jakarta");

                Intent moveWithObjectIntent = new Intent(MainActivity.this, MoveWithObjectActivity.class);
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person);
                startActivity(moveWithObjectIntent);
                break;

            case R.id.btn_dial_number:
                // todo 14
                // menggunakan intent secara implicit untuk proses dial sebauh nomor telepon
                // ACTION_DIAL --> menentukan intent filter dari aplikasi yang dapat menangani ini
                // sehingga aplikasi yang memiliki kemampuan untuk komunikasi (menjalankan action_dial ini)
                // akan muncul pada opsi pilihan saat ditampilkan ke pengguna
                String phoneNumber = "090911019";
                Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(dialPhoneIntent);
                break;

            case R.id.btn_move_for_result:
                // todo 20
                // kita akan menjalankan sebuah activity melalui intent untuk nilai balik
                // ke activity yang menjalankan di mana nilai REQUEST_CODE adalah 110
                Intent moveForResultIntent = new Intent(MainActivity.this, MoveForResultActivity.class);
                startActivityForResult(moveForResultIntent, REQUEST_CODE);
                break;
        }
    }

    // todo 21
    // ketika MoveForResultActivity telah tertutup sempurna (dijalankan finish())
    // maka method ini akan dijalankan
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // apakah requestCode sama dengan yang dikirimkan oleh MainActivity
        if (requestCode == REQUEST_CODE){
            if (resultCode == MoveForResultActivity.RESULT_CODE){
                int selectedValue = data.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0);
                tvResult.setText(String.format("Hasil : %s", selectedValue));
            }
        }
    }
}
