package com.example.csaper6.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button previousButton;
    private Button cheatButton;
    private TextView questionTextView;
    private TextView scoreTextView;
    private int currentIndex;
    private boolean cheatingEnabled;
    private boolean hasCheated;
    private int score;
    //public final int FINAL_SCORE;
    public static final String EXTRA_CURRENT_QUESTION = "question";
    public static final String EXTRA_CURRENT_ANSWER = "answer";
    public static final int REQUEST_CHEATED = 0;
    private Question[] questionBank = {
            new Question (R.string.question_cats, true),
            new Question (R.string.question_dracula, true),
            new Question (R.string.question_dogs, false),
            new Question (R.string.question_blackCat, true)
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_cheat:
                toggleCheating();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleCheating() {
        if (cheatingEnabled) {
            //disable cheating
            cheatingEnabled = false;
            cheatButton.setVisibility(View.GONE);
        }
        else
            //enable cheatingEnabled
            cheatingEnabled = true;
            cheatButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. Wire the buttons and the textView
        trueButton = (Button) findViewById(R.id.trueButton);
        falseButton = (Button) findViewById(R.id.falseButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        questionTextView = (TextView) findViewById(R.id.textview_question);
        scoreTextView = (TextView) findViewById(R.id.textview_score);
        cheatButton = (Button) findViewById(R.id.button_cheat);

        //2. Create a new question object from the string resources
        //Make a Question object and pass the string resource and answer in the constructor

        Question q1 = questionBank[0];
        Question q2 = questionBank[1];
        Question q3 = questionBank[2];
        Question q4 = questionBank[3];


        //3. Set the textView's text to the Question's text

        currentIndex = 0;
        score = 0;
        updateScoreText();
        questionTextView.setText(questionBank[currentIndex].getQuestionId());

        //4. Make a View.OnClickListener for each button using the
        //anonymous inner class way of doing things
        //Inside each button, call the question's checkAnswer method and
        //make an appropriate toast.

        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousQuestion();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
                enableButtons();
            }
        });

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                disableButtons();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                disableButtons();
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use Intents to go from one Activity to another
                Intent openCheatActivity =
                        new Intent(MainActivity.this,CheatActivity.class);
                //load up the intent with extra information to take
                //to the new activity
                //it follows the key:value pair format where
                //you have a key to identify the extra & a value that is
                //stored with it
                openCheatActivity.putExtra(EXTRA_CURRENT_QUESTION,
                        questionBank[currentIndex].getQuestionId());
                openCheatActivity.putExtra(EXTRA_CURRENT_ANSWER,
                        questionBank[currentIndex].isAnswerTrue());
                //startActivity(openCheatActivity);
                startActivityForResult(openCheatActivity, REQUEST_CHEATED);
            }
        });
        cheatingEnabled = false;
        cheatButton.setVisibility(View.GONE);
    }

    private void enableButtons() {
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
    }

    private void disableButtons() {
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
    }

    /*private void enablePrevButton() {
        previousButton.setEnabled(true);
    }

    private void disablePrevButton() {
        previousButton.setEnabled(false);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CHEATED) {
            //extract information from the intent "data"
            hasCheated = data.getBooleanExtra(CheatActivity.EXTRA_CHEATED, false);
            //do something with it, maybe set an instance var to true if they cheated
        }
    }

    private void checkAnswer(boolean userResponse) {
        if(questionBank[currentIndex].checkAnswer(userResponse) == true)
        {
            if (hasCheated == true) {
                Toast.makeText(MainActivity.this, R.string.toast_cheater, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, R.string.toast_correct, Toast.LENGTH_SHORT).show();
                score++;
                updateScoreText();
            }
        }
        else
        {
            Toast.makeText(MainActivity.this, R.string.toast_incorrect, Toast.LENGTH_SHORT).show();
            score--;
            updateScoreText();
        }
        hasCheated = false;
    }

    private void nextQuestion() {
        currentIndex =
                (currentIndex + 1) % questionBank.length; //loops the questions
        /*if (currentIndex + 1 > questionBank.length) {
            //make new score screen activity
            Intent openScoreActivity =
                    new Intent(MainActivity.this, ScoreActivity.class);
            openScoreActivity.putExtra(FINAL_SCORE, score);
            startActivityForResult(openScoreActivity, REQUEST_SCORE);
        }
        else {*/
            questionTextView.setText(questionBank[currentIndex].getQuestionId());
        //}
    }

    private void previousQuestion() {
        if (currentIndex > 0) {
            currentIndex =
                    (currentIndex - 1) % questionBank.length; //loops the questions
            enableButtons();
            questionTextView.setText(questionBank[currentIndex].getQuestionId());
        }
        else
            Toast.makeText(MainActivity.this, "This is the first question!", Toast.LENGTH_SHORT).show();
    }

    private void updateScoreText() {
        scoreTextView.setText(getString(R.string.scoreText) + score);
    }
}

//TODO: Disable prevButton when answer to previous question was right, enable otherwise
//TODO: Make score screen activity that pops up when last question has been answered
