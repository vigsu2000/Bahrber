package com.example.vignesh.imageupload;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FormScreen extends AppCompatActivity {

    TextView formTitle, length;
    Button buttonShort, buttonMediumLength, buttonLong;
    Button buttonFine, buttonMediumThickness, buttonCoarse;
    Button button1a, button1b, button1c, button2a, button2b, button2c, button3a, button3b, button3c, button4a, button4b, button4c;
    LinearLayout lengthLayout, thicknessLayout, hairType1Layout, hairType2Layout, hairType3Layout, hairType4Layout;
    ConstraintLayout constraintLayoutLength, constraintLayoutThickness;
    Switch terms;
    ArrayList<Button> lengthButtons, thicknessButtons, hairTypeButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);

        formTitle = (TextView)findViewById(R.id.formTitle);
        length = (TextView)findViewById(R.id.thickness);
        buttonShort = (Button)findViewById(R.id.buttonShort);
        buttonMediumLength = (Button)findViewById(R.id.buttonMediumLength);
        buttonLong = (Button)findViewById(R.id.buttonLong);
        buttonFine = (Button)findViewById(R.id.buttonFine);
        buttonMediumThickness = (Button)findViewById(R.id.buttonMediumThickness);
        buttonCoarse = (Button)findViewById(R.id.buttonCoarse);
        button1a = (Button)findViewById(R.id.button1a);
        button1b = (Button)findViewById(R.id.button1b);
        button1c = (Button)findViewById(R.id.button1c);
        button2a = (Button)findViewById(R.id.button2a);
        button2b = (Button)findViewById(R.id.button2b);
        button2c = (Button)findViewById(R.id.button2c);
        button3a = (Button)findViewById(R.id.button3a);
        button3b = (Button)findViewById(R.id.button3b);
        button3c = (Button)findViewById(R.id.button3c);
        button4a = (Button)findViewById(R.id.button4a);
        button4b = (Button)findViewById(R.id.button4b);
        button4c = (Button)findViewById(R.id.button4c);
        lengthLayout = (LinearLayout)findViewById(R.id.lengthLayout);
        thicknessLayout = (LinearLayout)findViewById(R.id.thicknessLayout);
        hairType1Layout = (LinearLayout)findViewById(R.id.hairType1Layout);
        hairType2Layout = (LinearLayout)findViewById(R.id.hairType2Layout);
        hairType3Layout = (LinearLayout)findViewById(R.id.hairType3Layout);
        hairType4Layout = (LinearLayout)findViewById(R.id.hairType4Layout);
        constraintLayoutLength = (ConstraintLayout)findViewById(R.id.constraintLayoutLength);
        constraintLayoutThickness = (ConstraintLayout)findViewById(R.id.constraintLayoutThickness);

        //Creates arraylist of length buttons
        lengthButtons = new ArrayList<>();
        lengthButtons.add(buttonShort);
        lengthButtons.add(buttonMediumLength);
        lengthButtons.add(buttonLong);

        //Creates arraylist of thickness buttons
        thicknessButtons = new ArrayList<>();
        thicknessButtons.add(buttonFine);
        thicknessButtons.add(buttonMediumThickness);
        thicknessButtons.add(buttonCoarse);

        //Creates arraylist of hair type buttons
        hairTypeButtons = new ArrayList<>();
        hairTypeButtons.add(button1a);
        hairTypeButtons.add(button1b);
        hairTypeButtons.add(button1c);
        hairTypeButtons.add(button2a);
        hairTypeButtons.add(button2b);
        hairTypeButtons.add(button2c);
        hairTypeButtons.add(button3a);
        hairTypeButtons.add(button3b);
        hairTypeButtons.add(button3c);
        hairTypeButtons.add(button4a);
        hairTypeButtons.add(button4b);
        hairTypeButtons.add(button4c);

        //Allow the buttons to be square
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        lengthLayout.getLayoutParams().height = screenWidth / 3;
        thicknessLayout.getLayoutParams().height = screenWidth / 3;
        constraintLayoutLength.getLayoutParams().height = screenWidth / 2;
        constraintLayoutThickness.getLayoutParams().height = screenWidth / 2;


        for (final Button button : lengthButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    System.out.println();
                    if (button.getBackground().getAlpha() == 0xff) {
                        if (numberOfSelectedButtons(lengthButtons) > 1) {
                            selectButtons(lengthButtons, button);
                        } else if (numberOfSelectedButtons(lengthButtons) == 1) {
                            resetButtons(lengthButtons);
                        }
                    } else {
                        selectButtons(lengthButtons, button);
                    }
                    printButtonStates(lengthButtons);
                    printButtonStates(thicknessButtons);
                }
            });
        }

        for (final Button button : thicknessButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    System.out.println();
                    if (button.getBackground().getAlpha() == 0xff) {
                        if (numberOfSelectedButtons(thicknessButtons) > 1) {
                            selectButtons(thicknessButtons, button);
                        } else if (numberOfSelectedButtons(thicknessButtons) == 1) {
                            resetButtons(thicknessButtons);
                        }
                    } else {
                        selectButtons(thicknessButtons, button);
                    }
                    printButtonStates(lengthButtons);
                    printButtonStates(thicknessButtons);
                }
            });
        }

        for (final Button button : hairTypeButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    System.out.println();
                    if (button.getBackground().getAlpha() == 0xff) {
                        if (numberOfSelectedButtons(hairTypeButtons) > 1) {
                            selectButtons(hairTypeButtons, button);
                        } else if (numberOfSelectedButtons(hairTypeButtons) == 1) {
                            resetButtons(hairTypeButtons);
                        }
                    } else {
                        selectButtons(hairTypeButtons, button);
                    }
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void selectButtons(ArrayList<Button> group, Button target) {
        for (Button button : group) {
            System.out.println(button.getId());
            if (button.getId() != target.getId()) {
                if (button.getBackground().getAlpha() == 0xff) {
                    System.out.println("hello" + button.getId());
                    button.getBackground().setAlpha(0x80);
                }
            } else if (button.getId() == target.getId()){
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
            System.out.println(button.getId());
            if (button.getBackground().getAlpha() < 0xff) {
                AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1.0f);
                alphaAnim.setDuration (400);
                button.startAnimation(alphaAnim);
                button.getBackground().setAlpha(0xff);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void printButtonStates(ArrayList<Button> group) {
        for (Button button : group) {
            System.out.println(button.getId() + ": " + (button.getBackground().getAlpha() == 0xff));
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
