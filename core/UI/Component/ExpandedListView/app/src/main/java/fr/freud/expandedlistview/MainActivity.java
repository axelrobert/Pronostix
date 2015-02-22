package fr.freud.expandedlistview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import indexedlistview.ExpandedIndexedListView;


public class MainActivity extends Activity {

    // more efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();
    private AnimatedExpandableListView mAnimatedExpandableListView;

    private static String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
        "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" , "#" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createData();
        final ExpandedIndexedListView listView = (ExpandedIndexedListView) findViewById(R.id.expandable_indexed_list_view);
        mAnimatedExpandableListView = listView.getList();
        mAnimatedExpandableListView.setGroupIndicator(null);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        mAnimatedExpandableListView.setAdapter(adapter);
        mAnimatedExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                Log.i("MainActivity", "group click");
                if (mAnimatedExpandableListView.isGroupExpanded(groupPosition)) {
                    mAnimatedExpandableListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    mAnimatedExpandableListView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });
    }

    public void createData() {
        List<String> countries = populateCountries();
        int index = 0;
        for (String letter : alphabet) {
            Group group = new Group(letter);
            for (int i = 0; i < 2; i++) {
                group.children.add(countries.get(2 * index + i));
            }
            groups.append(index, group);
            index += 1;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<String> populateCountries() {
        List<String> countries = new ArrayList<String>();
        countries.add("Afghanistan");
        countries.add("Albania");
        countries.add("Bahrain");
        countries.add("Bangladesh");
        countries.add("Cambodia");
        countries.add("Cameroon");
        countries.add("Denmark");
        countries.add("Djibouti");
        countries.add("East Timor");
        countries.add("Ecuador");
        countries.add("Fiji");
        countries.add("Finland");
        countries.add("Gabon");
        countries.add("Georgia");
        countries.add("Haiti");
        countries.add("Holy See");
        countries.add("Iceland");
        countries.add("India");
        countries.add("Jamaica");
        countries.add("Japan");
        countries.add("Kazakhstan");
        countries.add("Kenya");
        countries.add("Laos");
        countries.add("Latvia");
        countries.add("Macau");
        countries.add("Macedonia");
        countries.add("Namibia");
        countries.add("Nauru");
        countries.add("Oman");
        countries.add("Ouzbekistan");
        countries.add("Pakistan");
        countries.add("Palau");
        countries.add("Qatar");
        countries.add("Quebec");
        countries.add("Romania");
        countries.add("Russia");
        countries.add("Saint Kitts and Nevis");
        countries.add("Saint Lucia");
        countries.add("Taiwan");
        countries.add("Tajikistan");
        countries.add("Uganda");
        countries.add("Ukraine");
        countries.add("Vanuatu");
        countries.add("Venezuela");
        countries.add("Wales");
        countries.add("West England");
        countries.add("Xeres");
        countries.add("X-Men");
        countries.add("Yemen");
        countries.add("Yougoslavia");
        countries.add("Zambia");
        countries.add("Zimbabwe");
        countries.add("0");
        countries.add("2");
        return countries;
    }
}
