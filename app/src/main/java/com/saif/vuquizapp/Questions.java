package com.saif.vuquizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class Questions extends AppCompatActivity {
    public static final String FILE_NAME = "QUIZ";
    public static final String KEY_NAME = "QUESTION";
    Button share_btn, next_btn, optionOne, optionTwo, optionThree, optionFour;
    FloatingActionButton bookMark_btn;
    TextView question, noIndicator;
    LinearLayout optionContainer;
    String Subjects;
    int setNo;
    int count = 0;
    List<questionsModel> list = new ArrayList<>();
    int position = 0;
    private int score = 0;
    private Dialog loadingDialog;
    private List<questionsModel> bookmarkList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int matchedQuestionPosition;
    private InterstitialAd  mInterstitialAd;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        next_btn = findViewById(R.id.next_btn);
        share_btn = findViewById(R.id.share_btn);
        optionContainer = findViewById(R.id.optionContainer);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);
        bookMark_btn = findViewById(R.id.bookMark_btn);
        question = findViewById(R.id.question);
        noIndicator = findViewById(R.id.noIndicator);
        loadAds();


        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        getBookmarks();

        bookMark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modelMatch()){
                    bookmarkList.remove(matchedQuestionPosition);
                    bookMark_btn.setImageDrawable(getDrawable(R.drawable.bookmark_border));

                }else {
                    bookmarkList.add(list.get(position));
                    bookMark_btn.setImageDrawable(getDrawable(R.drawable.bookmark_btn));
                }
            }
        });

//        list.add(new questionsModel("Intersection of two straight lines is","Surface","Curve","Plane","Point","Plane"));
//        list.add(new questionsModel("Plane is a --------------- surface.","One-dimensional","Two-dimensional","Three-dimensional","Dimensionless","Two-dimensional"));
//        list.add(new questionsModel("What is the amplitude of a periodic function defined by ( ) sin 3 x f x \uF03D ?","0","1","1/3","Does not exist","1"));
//        list.add(new questionsModel("how are u","a","b","c","d","b"));
        Subjects = getIntent().getStringExtra("text");
        setNo = getIntent().getIntExtra("setNo", 1);



        //        for loadingDialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialouge);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

//        for (int i = 0; i < 4; i++){
//            optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                @Override
//                public void onClick(View v) {
//                checkAnswer((Button)v);
//                }
//            });
//        }
// firebase code here
        loadingDialog.show();
        myRef.child("SETS").child(Subjects).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.add(dataSnapshot.getValue(questionsModel.class));

                }
                if (list.size() > 0) {
                    for (int i = 0; i < 4; i++) {
                        optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button) v);
                            }
                        });

                    }
                    playAnim(question, 0, list.get(position).getQuestion());

                    next_btn.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {


//                            count = 0;

                            // here is position + 1 of question becauese every time the question increament and it is a mistake in youtube video
//                            playAnim(question, 0, list.get(position+1).getQuestion());
//                            playAnim(question, 0, list.get(position).getQuestion());
                            next_btn.setEnabled(false);
                            enableOption(true);

                            position++;
                                  if (position == list.size()) {




                                      Toast.makeText(getApplicationContext(), "score: "+score, Toast.LENGTH_LONG).show();
//                                // intent to result Activity
                                Intent intent = new Intent(Questions.this, Result.class);
                                intent.putExtra("score", score);
                                intent.putExtra("total", list.size());
                                startActivity(intent);
                                finish();
                                return;
                            }
                            count = 0;
                            playAnim(question, 0, list.get(position).getQuestion());

                        }

                    });
                    share_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String body =list.get(position).getQuestion() + "\n" +
                                    list.get(position).getOptionA() + "\n" +
                                    list.get(position).getOptionB() + "\n" +
                                    list.get(position).getOptionC() + "\n" +
                                    list.get(position).getOptionD();

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"VU QUIZ APP");
                            shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                            startActivity(Intent.createChooser(shareIntent,"Share via"));
                        }
                    });

                } else {
                    finish();
                    Toast.makeText(getApplicationContext(), "no Questions", Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });
//        playAnim(question, 0, list.get(position).getQuestion());
//        next_btn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onClick(View v) {
//                count = 0;
//                // here is position + 1 of question becauese every time the question increament and it is a mistake in youtube video
//                playAnim(question, 0, list.get(position+1).getQuestion());
//                next_btn.setEnabled(false);
//                enableOption(true);
//                position++;
//                if(position == list.size()){
//                    // intent to result Activity
////                        Intent intent = new Intent(getApplicationContext(), Result.class);
////                        startActivity(intent);
//
//                }
//
//            }
//        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    storeBookmarks();
    }

    private void playAnim(View view, int value, String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //value is so due to scaleX, scaleY, and alpha the view is invisible
                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = list.get(position).getOptionA();
                    } else if (count == 1) {
                        option = list.get(position).getOptionB();
                    } else if (count == 2) {
                        option = list.get(position).getOptionC();
                    } else if (count == 3) {
                        option = list.get(position).getOptionD();

                    }
                    playAnim(optionContainer.getChildAt(count), 0, option);
                    count++;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animation) {
                //Here value = 1 so due to scaleX, scaleY and alpha the view is visible
                if (value == 0) {
                    try {
                        ((TextView) view).setText(data);
                        noIndicator.setText(position + 1 + "/" + list.size());
                        if(modelMatch()){
                            bookMark_btn.setImageDrawable(getDrawable(R.drawable.bookmark_btn));

                        }else {
                            bookMark_btn.setImageDrawable(getDrawable(R.drawable.bookmark_border));

                        }


                    } catch (ClassCastException ex) {
                        ((Button) view).setText(data);

                    }
                    view.setTag(data);
                    playAnim(view, 1, data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(Button selectedOption) {
        enableOption(false);
        next_btn.setEnabled(true);
        if (selectedOption.getText().toString().equals(list.get(position).getCorrectOption())) {
            // correct Answer
//        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor())getColor(R.color.light_green)));
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_green)));
            score++;
        } else {
            // incorrect Answer
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_red)));
            Button correctAns = (Button) optionContainer.findViewWithTag(list.get(position).getCorrectOption());
            correctAns.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_green)));


        }
    }

    // when option option is selected user cannot be select second option
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableOption(Boolean enable) {
        for (int i = 0; i < 4; i++) {
            optionContainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                optionContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_200)));

            }
        }
    }
    private void getBookmarks(){
        String json = preferences.getString(KEY_NAME,"");
        Type type = new TypeToken<List<questionsModel>>(){}.getType();
        bookmarkList = gson.fromJson(json,type);

        if (bookmarkList==null){
            bookmarkList = new ArrayList<>();
        }
    }
    private boolean modelMatch(){
        int i = 0;
        boolean matched = false;
        for (questionsModel model : bookmarkList){
            if (model.getQuestion().equals(list.get(position).getQuestion())
            && model.getCorrectOption().equals(list.get(position).getCorrectOption())
            && model.getSetNo() == list.get(position).getSetNo()){
                matched = true;
                matchedQuestionPosition = i;
            }
            i++;

        }
return matched;
    }
private void storeBookmarks(){
        String json = gson.toJson(bookmarkList);
        editor.putString(KEY_NAME,json);
        editor.commit();
}
    private void loadAds(){
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



    }


}