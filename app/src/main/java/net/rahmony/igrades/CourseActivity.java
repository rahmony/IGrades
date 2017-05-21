package net.rahmony.igrades;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.rahmony.igrades.SwipeMenuList.SwipeMenu;
import net.rahmony.igrades.SwipeMenuList.SwipeMenuCreator;
import net.rahmony.igrades.SwipeMenuList.SwipeMenuItem;
import net.rahmony.igrades.SwipeMenuList.SwipeMenuListView;

import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CourseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView mCourse_text_addNewCourse ;
    private SwipeMenuListView mCourse_listView_courseTitle ;
    Context context;
    private ArrayList<Course> courseList = new ArrayList<Course>();
    myAdapter arrayAdapter;
    @Override
    protected void onResume() {
        super.onResume();

        mCourse_text_addNewCourse = (TextView) findViewById(R.id.course_text_addNewCourse);
        mCourse_listView_courseTitle = (SwipeMenuListView) findViewById(R.id.course_listView_courseTitle);
        arrayAdapter=new myAdapter(context);
        courseList.clear();
        // Create the Realm configuration
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
        // Open the Realm for the UI thread.
        Realm realm = Realm.getInstance(realmConfig);
        // Begin Transaction
        realm.beginTransaction();
        // Build the query :
        RealmQuery<Course> query = realm.where(Course.class);
        // Execute the query:
        RealmResults<Course> result = query.findAll();

        if(!result.isEmpty())
            mCourse_text_addNewCourse.setVisibility(View.GONE);

            for (int i = 0; i < result.size(); i++)
            {
                courseList.add(result.get(i));
            }
            arrayAdapter.setGrades(courseList);
            mCourse_listView_courseTitle.setAdapter(arrayAdapter);
            mCourse_listView_courseTitle.setOnItemClickListener(this);
        // Commit Transaction
        realm.commitTransaction();
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mCourse_listView_courseTitle.setMenuCreator(creator);

        // step 2. listener item click event
        mCourse_listView_courseTitle.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                // Create the Realm configuration
                RealmConfiguration realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
                // Open the Realm for the UI thread.
                Realm realm = Realm.getInstance(realmConfig);
                realm.beginTransaction();
                RealmQuery<Course> query = realm.where(Course.class);
                RealmResults<Course> result = query.findAll();
                if(result.size()>0){
                    result.get(position).deleteFromRealm();
                    realm.commitTransaction();
                    getlist();
                }else{
                    realm.commitTransaction();
                }


                return false;
            }
        });
    }
    public void getlist(){
        // Create the Realm configuration
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
        // Open the Realm for the UI thread.
        Realm realm = Realm.getInstance(realmConfig);
        realm.beginTransaction();
        // Build the query looking at all users:
        RealmQuery<Course> query = realm.where(Course.class);
        RealmResults<Course> result = query.findAll();

        courseList.clear();
        for (int i = 0; i < result.size(); i++)
        {
            Course grade=result.get(i);
            courseList.add(grade);
        }
        arrayAdapter.setGrades(courseList);
        mCourse_listView_courseTitle.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        realm.commitTransaction();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.course_title);
        setContentView(R.layout.activity_course);
        context=this;


    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.course_add:
                startActivity(new Intent(getBaseContext(),NewCourseActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getBaseContext(),GradeActivity.class).putExtra("courseName" , courseList.get(position).getCourseTitle()));
    }
    private class myAdapter extends BaseAdapter {
        Context context;
        ArrayList<Course> list=new ArrayList<Course>();
        public myAdapter(Context context){
            this.context=context;

        }
        public void setGrades(ArrayList<Course> grades){
            list=grades;
        }
        @Override
        public int getCount() {
            if(list.size()<=0){
                return 0;
            }else{
                return list.size();
            }

        }

        @Override
        public Object getItem(int position) {
            if(list.size()>0){
                return list.get(position);
            }else
                return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            Course grade=list.get(position);
            TextView mText_grade = (TextView) v.findViewById(android.R.id.text1);
            mText_grade.setText(grade.getCourseTitle());
            return v;
        }


    }
}
