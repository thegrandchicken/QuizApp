package com.example.csaper6.quizapp;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {

    private boolean hasCheated;
    public static final String EXTRA_CHEATED = "They cheated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        TextView questionText = (TextView) findViewById(R.id.cheat_question_text);
        final TextView questionAnswerText = (TextView) findViewById(R.id.cheat_answer_text);
        final Button sureButton = (Button) findViewById(R.id.button_return);
        hasCheated = false;

        //gathering up the Intent that brought us here
        Intent i = getIntent();
        //extracting the extra that was put there
        int questionId = i.getIntExtra(MainActivity.EXTRA_CURRENT_QUESTION, -500);
        final boolean answer = i.getBooleanExtra(MainActivity.EXTRA_CURRENT_ANSWER, false);
        //using that data that was passed to the new activity
        questionText.setText(questionId);

        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionAnswerText.setText("" + answer);
                hasCheated = true;
                sureButton.setVisibility(View.GONE);
                //just using this to box up the info to send back
                //so don't need any addresses in the parameters
                Intent i = new Intent();
                i.putExtra(EXTRA_CHEATED, hasCheated);
                setResult(RESULT_OK, i);
                Toast.makeText(CheatActivity.this, "Click back button to return to quiz screen",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
