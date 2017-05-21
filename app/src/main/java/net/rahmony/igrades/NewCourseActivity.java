package net.rahmony.igrades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NewCourseActivity extends AppCompatActivity {

    private EditText mNew_course_et_courseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.new_course_title);
        setContentView(R.layout.activity_new_course);

        mNew_course_et_courseTitle = (EditText)findViewById(R.id.new_course_et_courseTitle);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_course, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_course_save:
            {
                String courseTitle = mNew_course_et_courseTitle.getText().toString().trim();

                // Create the Realm configuration
                RealmConfiguration realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
                // Open the Realm for the UI thread.
                Realm realm = Realm.getInstance(realmConfig);

                // Begin Transaction
                realm.beginTransaction();
                // create object from class 'table of DB'
                Course courseOne = realm.createObject(Course.class);

                // set a title for  course
                courseOne.setCourseTitle(courseTitle);
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
