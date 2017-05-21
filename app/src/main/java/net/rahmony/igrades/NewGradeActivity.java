package net.rahmony.igrades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class NewGradeActivity extends AppCompatActivity {



    private EditText mNew_grade_et_earned_grade ;
    private EditText mNew_grade_et_total_grade ;
    private Spinner mNew_grade_spinner_gradeTitle;

    private String gradeTitle ;
    private double earnedGrade ;
    private double totalGrade ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.new_grade_title);
        setContentView(R.layout.activity_new_grade);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_grade, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_grade_save:
            {
                Bundle extras = getIntent().getExtras();
                String courseName = extras.getString("courseName");

                mNew_grade_et_earned_grade = (EditText) findViewById(R.id.new_grade_et_earned_grade);
                mNew_grade_et_total_grade = (EditText) findViewById(R.id.new_grade_et_total_grade);
                mNew_grade_spinner_gradeTitle = (Spinner) findViewById(R.id.new_grade_spinner_gradeTitle);

                gradeTitle = mNew_grade_spinner_gradeTitle.getSelectedItem().toString();
                earnedGrade = Double.parseDouble(mNew_grade_et_earned_grade.getText().toString());
                totalGrade = Double.parseDouble(mNew_grade_et_total_grade.getText().toString());



                // Create the Realm configuration
                RealmConfiguration realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
                // Open the Realm for the UI thread.
                Realm realm = Realm.getInstance(realmConfig);

                // Begin Transaction
                realm.beginTransaction();

                // create object from Grade 'table of DB'
                Grade gradeOne = realm.createObject(Grade.class);

                // set values
                gradeOne.setCourseTitle(courseName);
                gradeOne.setGradeTitle(gradeTitle);
                gradeOne.setEarnedGrade(earnedGrade);
                gradeOne.setTotalGrade(totalGrade);

                // Commit Transaction
                realm.commitTransaction();


                finish();
            }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
