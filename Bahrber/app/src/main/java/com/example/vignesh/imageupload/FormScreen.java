package com.example.vignesh.imageupload;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FormScreen extends AppCompatActivity {

    TextView formTitle, length;
    Button buttonShort, buttonMedium, buttonLong;
    Switch terms;
    LinearLayout lengthLayout;
    ArrayList<Button> lengthButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);

        formTitle = (TextView)findViewById(R.id.formTitle);
        length = (TextView)findViewById(R.id.length);
        buttonShort = (Button)findViewById(R.id.buttonShort);
        buttonMedium = (Button)findViewById(R.id.buttonMedium);
        buttonLong = (Button)findViewById(R.id.buttonLong);
        terms = (Switch)findViewById(R.id.terms);
        lengthLayout = (LinearLayout)findViewById(R.id.lengthLayout);

        //Creates arraylist of thickness buttons
        lengthButtons = new ArrayList<>();
        lengthButtons.add(buttonShort);
        lengthButtons.add(buttonMedium);
        lengthButtons.add(buttonLong);

        buttonShort.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (buttonShort.getBackground().getAlpha() == 0xff) {
                    if (numberOfSelectedButtons(lengthButtons) > 1) {
                        selectButtons(lengthButtons, buttonShort);
                    } else if (numberOfSelectedButtons(lengthButtons) == 1) {
                        resetButtons(lengthButtons);
                    }
                } else {
                    selectButtons(lengthButtons, buttonShort);
                }
            }
        });

        buttonMedium.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (buttonMedium.getBackground().getAlpha() == 0xff) {
                    if (numberOfSelectedButtons(lengthButtons) > 1) {
                        selectButtons(lengthButtons, buttonMedium);
                    } else if (numberOfSelectedButtons(lengthButtons) == 1) {
                        resetButtons(lengthButtons);
                    }
                } else {
                    selectButtons(lengthButtons, buttonMedium);
                }
            }
        });

        buttonLong.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (buttonLong.getBackground().getAlpha() == 0xff) {
                    if (numberOfSelectedButtons(lengthButtons) > 1) {
                        selectButtons(lengthButtons, buttonLong);
                    } else if (numberOfSelectedButtons(lengthButtons) == 1) {
                        resetButtons(lengthButtons);
                    }
                } else {
                    selectButtons(lengthButtons, buttonLong);
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void selectButtons(ArrayList<Button> group, Button target) {
        for (Button button : group) {
            if (button != target) {
                if (button.getBackground().getAlpha() == 0xff) {
                    button.getBackground().setAlpha(0x80);
                }
            } else if (button == target){
                if (button.getBackground().getAlpha() < 0xff) {
                    AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1.0f);
                    alphaAnim.setDuration (400);
                    button.startAnimation(alphaAnim);
                    button.getBackground().setAlpha(0xff);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void resetButtons(ArrayList<Button> group) {
        for (Button button : group) {
            if (button.getBackground().getAlpha() < 0xff) {
                AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1.0f);
                alphaAnim.setDuration (400);
                button.startAnimation(alphaAnim);
                button.getBackground().setAlpha(0xff);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    int numberOfSelectedButtons(ArrayList<Button> group) {
        int numSelectedButtons = 0;

        for (Button button : group) {
            if (button.getBackground().getAlpha() == 0xff) {
                ++numSelectedButtons;
            }
        }

        return numSelectedButtons;
    }
}
