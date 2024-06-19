package com.example.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvEjercicios;
    private ArrayList<Exercise> ejercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvEjercicios = findViewById(R.id.lvEjercicios);

        ejercicios = new ArrayList<>();
        ejercicios.add(new Exercise("Flexiones ", "Ejercicio para fortalecer el pecho y los tríceps.", R.drawable.push_ups));
        ejercicios.add(new Exercise("Sentadillas", "Ejercicio para fortalecer las piernas y los glúteos.", R.drawable.squats));
        ejercicios.add(new Exercise("Saltos", "Ejercicio de cuerpo completo para mejorar la resistencia.", R.drawable.burpees));
        ejercicios.add(new Exercise("Planchas", "Ejercicio para fortalecer el core.", R.drawable.plank));
        ejercicios.add(new Exercise("Estocadas", "Ejercicio para fortalecer las piernas y mejorar el equilibrio.", R.drawable.lunges));
        ejercicios.add(new Exercise("Sentadas-Escalada", "Ejercicio para mejorar la resistencia cardiovascular y fortalecer el core.", R.drawable.mountain_climbers));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Exercise ejercicio : ejercicios) {
            adapter.add(ejercicio.getName());
        }

        lvEjercicios.setAdapter(adapter);

        lvEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise selectedExercise = ejercicios.get(position);
                Intent intent = new Intent(MainActivity.this, ExerciseDetailActivity.class);
                intent.putExtra("exerciseName", selectedExercise.getName());
                intent.putExtra("exerciseDescription", selectedExercise.getDescription());
                intent.putExtra("exerciseImageResId", selectedExercise.getImageResId());
                startActivity(intent);
            }
        });
    }
}
