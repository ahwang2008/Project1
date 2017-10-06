package ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.ahwang13.familymapserver.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import model.Event;
import model.FamilyListAdapter;
import model.Model;
import model.Person;

public class SearchActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, String> correspondingPersonIndex;
    HashMap<Integer, String> correspondingEventIndex;
    private EditText searchEditText;
    private Button searchButton;
    private String searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_search);

        searchEditText = (EditText) findViewById(R.id.searchEditText);

        searchButton = (Button) findViewById(R.id.searchButton);

        final SearchActivity searchActivity = this;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareListData();
                listAdapter = new FamilyListAdapter(getBaseContext(), listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        if (listDataHeader.get(groupPosition).equals("Person Search Results")) {
                            String personID = correspondingPersonIndex.get(childPosition);
                            Model.instance().setCurrPersonID(personID);
                            Intent intent = new Intent(searchActivity, PersonActivity.class);
                            startActivity(intent);
                        }
                        else {
                            String eventID = correspondingEventIndex.get(childPosition);
                            Model.instance().setCurrEventID(eventID);
                            Intent intent = new Intent(searchActivity, MapActivity.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        });

        expListView = (ExpandableListView) findViewById(R.id.search_lists);
        // preparing list data
        prepareListData();

        listAdapter = new FamilyListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    public void prepareListData(){
        searchInput = searchEditText.getText().toString();
        Model.instance().setSearchString(searchInput);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        correspondingPersonIndex = new HashMap<>();
        correspondingEventIndex = new HashMap<>();

        // Adding child data
        listDataHeader.add("Person Search Results");
        listDataHeader.add("Event Search Results");

        // Adding child data
        //TODO:FOR SOME REASON, A SET DOESN'T WORK BUT AN ARRAY DOES. USE THAT AND GET RID OF DUPLICATES.
        Set<Person> personResults = Model.instance().getPersonSearchResults();
        Set<Event> eventResults = Model.instance().getEventSearchResults();
        List<String> searchPersonResults = new ArrayList<>();
        List<String> searchEventResults = new ArrayList<>();
        correspondingEventIndex.clear();
        correspondingPersonIndex.clear();
        int count = 0;

        for (Person p : personResults)
        {
            String description = p.getFirstName() + " " + p.getLastName() + "\n" + p.getGender();
            searchPersonResults.add(description);
            correspondingPersonIndex.put(count++, p.getPersonID());
        }
        count = 0;
        for (Event e : eventResults)
        {
            Person p = Model.instance().getPersonByPersonID(e.getPersonID());
            String description = e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")\n" + p.getFirstName() + " " + p.getLastName();
            searchEventResults.add(description);
            correspondingEventIndex.put(count++, e.getEventID());
        }

        listDataChild.put(listDataHeader.get(0), searchPersonResults); // Header, Child data
        listDataChild.put(listDataHeader.get(1), searchEventResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean ret = super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_person, menu);
        MenuItem goToTopButton = menu.findItem(R.id.goToTop);
        goToTopButton.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_angle_double_up).colorRes(R.color.colorAccent).sizeDp(40));
        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goToTop:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                return true;
            default:
                //Create new intent
                //startActivity
                //return true;
        }
        return false;
    }
}
