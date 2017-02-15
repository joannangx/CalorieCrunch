package com.example.wingsoffreedom.caloriecrunch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateExercise extends Fragment {
    private boolean profileEntered = false;
    private int userWeight;

    private Map<String, float[]> exerciseMap = new HashMap<String, float[]>();
    private List<float[]> forHundred = new ArrayList<float[]>(Arrays.asList(new float[] {350, (float) 0.0042},
            new float[] {200, (float) 0.0075}, new float[] {225, (float) 0.0065}, new float[] {25, (float) 3.5},
            new float[] {25, (float) 3.5}, new float[] {10, (float) 8.0}, new float[] {100, (float) 0.015},
            new float[] {12, (float) 4.0}, new float[] {20, (float) 3.3}, new float[] {12, (float) 7.0},
            new float[] {13, (float) 8.0}, new float[] {15, (float) 7.0}));

    private List<String> allExercises =  new ArrayList<>(Arrays.asList("Pushups", "Situps", "Squats", "Leg-Lifts",
            "Plank", "Jumping Jacks", "Pullups", "Cycling", "Walking", "Jogging", "Swimming", "Stair-Climbing"));

    private EditText weightInput;
    private EditText calorieInput;
    private Button exerciseButton;
    private List<TextView> compareCalories = null;

    private TextView exerciseLine = null;
    private LinearLayout displayLayout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_exercise, container, false);

        for(int i = 0; i < allExercises.size(); i++) {
            exerciseMap.put(allExercises.get(i), forHundred.get(i));
        }

        calorieInput = (EditText) view.findViewById(R.id.calorie_input);
        weightInput = (EditText) view.findViewById(R.id.weight_input_ex);
        exerciseButton = (Button) view.findViewById(R.id.exerConvert_btn);

        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int calAmount = (Integer) Integer.parseInt((String) calorieInput.getText().toString());

                displayLayout = (LinearLayout) view.findViewById(R.id.display_layout);

                if (compareCalories != null) {
                    for (TextView view : compareCalories) {
                        displayLayout.removeView(view);
                    }
                }
                compareCalories = new ArrayList<TextView>();

                if (exerciseLine != null) {
                    displayLayout.removeView(exerciseLine);
                } else {
                    exerciseLine = new TextView(view.getContext());
                }
                String weightString = (String) weightInput.getText().toString();
                if (!weightString.isEmpty()) {
                    userWeight = Integer.parseInt(weightString);
                    profileEntered = true;
                }

                exerciseLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                exerciseLine.setText("The following exercises burn " + String.valueOf(calAmount) + " calories:");
                exerciseLine.setTextSize(27);

                displayLayout.addView(exerciseLine);

                for (String ex : exerciseMap.keySet()) {
                    float tempAmount;

                    if (profileEntered) {
                        if (ex.equals("Pushups") || ex.equals("Situps") || ex.equals("Squats") || ex.equals("Pullups")) {
                            tempAmount = (float) ((float) calAmount/((float) ((float)userWeight/2.2)*((float)exerciseMap.get(ex)[1])));
                        } else {
                            tempAmount = (float) (((float) calAmount/((float) ((float)userWeight/2.2)*((float)exerciseMap.get(ex)[1])))*60.0);
                        }
                    } else {
                        tempAmount = (float) ((float) exerciseMap.get(ex)[0] / 100.0) * (float) calAmount;
                    }

                    TextView tempView = new TextView(view.getContext());
                    tempView.setTextSize(23);
                    tempView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    if (ex.equals("Pushups") || ex.equals("Situps") || ex.equals("Squats") || ex.equals("Pullups")) {
                        tempView.setText(ex + "         " + String.valueOf((int)tempAmount) + " reps");
                    } else {
                        tempView.setText(ex + "         " + String.valueOf((int) tempAmount) + " mins");
                    }

                    compareCalories.add(tempView);
                    displayLayout.addView(tempView);
                }
            }
        });

        return view;
    }
}
