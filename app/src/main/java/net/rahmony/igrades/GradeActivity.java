package net.rahmony.igrades;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class GradeActivity extends AppCompatActivity {

    private TextView mGrade_text_addNewGrade ;
    private SwipeMenuListView mGrade_listView_GradeTitle ;

    ArrayList<Grade> list_grade = new ArrayList<Grade>();
     myAdapter arrayAdapter;
    Context context;
     String courseName;

    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();
         courseName = extras.getString("courseName");

        mGrade_text_addNewGrade = (TextView) findViewById(R.id.grade_text_addNewGrade);
        mGrade_listView_GradeTitle = (SwipeMenuListView) findViewById(R.id.grade_listView_gradeTitle);

        // Create the Realm configuration
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
        // Open the Realm for the UI thread.
        Realm realm = Realm.getInstance(realmConfig);

        // Begin Transaction
        realm.beginTransaction();

        // Build the query looking at all users:
        RealmQuery<Grade> query = realm.where(Grade.class)
                                        .equalTo("courseTitle" , courseName )
                                         .isNotNull("gradeTitle");
        // Execute the query:
        RealmResults<Grade> result;
        result = query.findAll();

        if(!result.isEmpty())
            mGrade_text_addNewGrade.setVisibility(View.GONE);
        list_grade.clear();

        for (int i = 0; i < result.size(); i++)
        {
            Grade grade=result.get(i);;
            list_grade.add(grade);
        }
         arrayAdapter = new myAdapter(context);
         arrayAdapter.setGrades(list_grade);
         mGrade_listView_GradeTitle.setAdapter(arrayAdapter);
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
        mGrade_listView_GradeTitle.setMenuCreator(creator);

        // step 2. listener item click event
        mGrade_listView_GradeTitle.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                // Create the Realm configuration
                RealmConfiguration realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
                // Open the Realm for the UI thread.
                Realm realm = Realm.getInstance(realmConfig);
                realm.beginTransaction();
                // Build the query looking at all users:
                RealmQuery<Grade> query = realm.where(Grade.class)
                        .equalTo("courseTitle" , courseName )
                        .isNotNull("gradeTitle");
                // Execute the query:
                RealmResults<Grade> result;
                result = query.findAll();
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
        RealmQuery<Grade> query = realm.where(Grade.class)
                .equalTo("courseTitle" , courseName )
                .isNotNull("gradeTitle");
        // Execute the query:
        RealmResults<Grade> result;
        result = query.findAll();

            list_grade.clear();
            for (int i = 0; i < result.size(); i++)
            {
                Grade grade=result.get(i);
                list_grade.add(grade);
            }
            arrayAdapter.setGrades(list_grade);
            mGrade_listView_GradeTitle.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

            realm.commitTransaction();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.grade_title);
        setContentView(R.layout.activity_grade);
        context=this;

    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grade, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grade_add:
                Bundle extras = getIntent().getExtras();
                String courseName = extras.getString("courseName");
                startActivity(new Intent(getBaseContext(),NewGradeActivity.class).putExtra("courseName" , courseName));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class myAdapter extends BaseAdapter {
        Context context;
        ArrayList<Grade> list=new ArrayList<Grade>();
        public myAdapter(Context context){
            this.context=context;

        }
        public void setGrades(ArrayList<Grade> grades){
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
            View v = LayoutInflater.from(context).inflate(R.layout.list_grade, parent, false);
            Grade grade=list.get(position);
            TextView mText_grade = (TextView) v.findViewById(R.id.list_grade_text_gradeType);
            mText_grade.setText(grade.getGradeTitle());

            TextView mText_earned_grade = (TextView) v.findViewById(R.id.list_grade_text_earned_val);
            mText_earned_grade.setText(String.valueOf(grade.getEarnedGrade()));

            TextView mText_totalgrade = (TextView) v.findViewById(R.id.list_grade_text_total_val);
            mText_totalgrade.setText(String.valueOf(grade.getTotalGrade()));


            return v;
        }


    }
}
