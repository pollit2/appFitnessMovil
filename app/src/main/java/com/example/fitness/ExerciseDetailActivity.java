package com.example.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;

public class ExerciseDetailActivity extends AppCompatActivity {

    private TextView tvExerciseName;
    private TextView tvExerciseDescription;
    private ImageView ivExerciseImage;
    private Spinner spinnerDifficulty;
    private Button btnStart;
    private Button btnBack;
    private Button btnHome;
    private TextView tvTimer;

    private long timeInMillis;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        tvExerciseName = findViewById(R.id.tvExerciseName);
        tvExerciseDescription = findViewById(R.id.tvExerciseDescription);
        ivExerciseImage = findViewById(R.id.ivExerciseImage);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        btnStart = findViewById(R.id.btnStart);
        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);
        tvTimer = findViewById(R.id.tvTimer);

        String exerciseName = getIntent().getStringExtra("exerciseName");
        String exerciseDescription = getIntent().getStringExtra("exerciseDescription");
        int exerciseImageResId = getIntent().getIntExtra("exerciseImageResId", R.drawable.default_image); // Cambiado a placeholder

        tvExerciseName.setText(exerciseName);
        tvExerciseDescription.setText(exerciseDescription);
        ivExerciseImage.setImageResource(exerciseImageResId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapter);

        spinnerDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String difficulty = (String) parent.getItemAtPosition(position);
                switch (difficulty) {
                    case "Principiante":
                        timeInMillis = 2 * 60 * 1000; // 2 minutos en milisegundos
                        break;
                    case "Normal":
                        timeInMillis = 5 * 60 * 1000; // 5 minutos en milisegundos
                        break;
                    case "Avanzado":
                        timeInMillis = 10 * 60 * 1000; // 10 minutos en milisegundos
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                timeInMillis = 2 * 60 * 1000; // Valor predeterminado de 2 minutos
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    startTimer();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finaliza la actividad actual y regresa a la anterior
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseDetailActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent); // Inicia la actividad principal (MainActivity)
            }
        });
    }

    private void startTimer() {
        isTimerRunning = true;
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                tvTimer.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                tvTimer.setText("¡Tiempo completado!");
            }
        }.start();
    }
}
