package ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahwang13.familymapserver.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.Inflater;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import model.Event;
import model.FamilyListAdapter;
import model.Model;
import model.Person;

public class PersonActivity extends AppCompatActivity {

    //private RecyclerView list;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Map<Integer, String> personMap = new TreeMap();
    Map<Integer, String> eventMap = new TreeMap();
    Person person;
    Event event;
    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView genderTextView;
    String personID;
    String eventID;
    /// /Import the Person List
    //private PersonAdapter personAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_person);

        firstNameTextView = (TextView) findViewById(R.id.actualPersonFirstNameTextView);
        lastNameTextView = (TextView) findViewById(R.id.actualPersonLastNameTextView);
        genderTextView = (TextView) findViewById(R.id.actualPersonGenderTextView);

        personID = Model.instance().getCurrPersonID();
        person = Model.instance().getPersonByPersonID(personID);
        event = Model.instance().getEventByEventID(eventID);
        firstNameTextView.setText(person.getFirstName());
        lastNameTextView.setText(person.getLastName());
        if (person.getGender().equals("m"))
        {
            genderTextView.setText("Male");
        }
        else
        {
            genderTextView.setText("Female");
        }

        //get the listview
        expListView = (ExpandableListView) findViewById(R.id.person_event_lists);

        //preparing list data
        prepareListData();

        listAdapter = new FamilyListAdapter(this, listDataHeader, listDataChild);

        //setting list adapter
        expListView.setAdapter(listAdapter);


        final PersonActivity personActivity = this;
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                //
                if (listDataHeader.get(groupPosition).equals("LIFE EVENTS"))
                {
                    String eventID = eventMap.get(childPosition);
                    Model.instance().setCurrEventID(eventID);
                    Intent intent = new Intent(personActivity, MapActivity.class);
                    startActivity(intent);
                }
                else
                {
                    String personID = personMap.get(childPosition);
                    Model.instance().setCurrPersonID(personID);
                    Intent intent = new Intent(personActivity, PersonActivity.class);
                    startActivity(intent);
                }

                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("LIFE EVENTS");
        listDataHeader.add("FAMILY");

        // Adding child data
        //String personID = Model.instance().getCurrEventID();
        Map<String, List<Event>> personEvents = Model.instance().getPersonEvents();
        List<Event> eventList = personEvents.get(Model.instance().getCurrPersonID());
        List<String> eventOutput = new ArrayList<>();
        int count = 0;
        for (Event e : eventList) {
            String eventDescription = e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")";
            eventOutput.add(eventDescription);
            eventMap.put(count++, e.getEventID());
        }

        Person p = Model.instance().getPersonByPersonID(Model.instance().getCurrPersonID());
        Map<String, List<Person>> personChildren = Model.instance().getPersonChildren();
        List<Person> personList = personChildren.get(Model.instance().getCurrPersonID());
        List<String> personOutput = new ArrayList<>();
        //personMap.clear();
        count = 0;

        String fatherID = p.getFather();
        //Model.instance().setCurrPersonID(fatherID);
        Person father = Model.instance().getPersonByPersonID(fatherID);
        if (father != null) {
            personOutput.add(father.getFirstName() + " " + father.getLastName() + "\nFather");
            personMap.put(count++, father.getPersonID());
        }

        String motherID = p.getMother();
        //Model.instance().setCurrPersonID(motherID);
        Person mother = Model.instance().getPersonByPersonID(motherID);
        if (mother != null) {
            personOutput.add(mother.getFirstName() + " " + mother.getLastName() + "\nMother");
            personMap.put(count++, mother.getPersonID());
        }

        String spouseID = p.getSpouse();
        //Model.instance().setCurrPersonID(spouseID);
        Person spouse = Model.instance().getPersonByPersonID(spouseID);
        if (spouse != null) {
            personOutput.add(spouse.getFirstName() + " " + spouse.getLastName() + "\nSpouse");
            personMap.put(count++, spouse.getPersonID());
        }

        Person child = Model.instance().getChildByPersonID(p.getPersonID());
        if (child != null) {
            personOutput.add(child.getFirstName() + " " + child.getLastName() + "\nChild");
            personMap.put(count++, child.getPersonID());
        }
        listDataChild.put(listDataHeader.get(0), eventOutput); // Header, Child data
        listDataChild.put(listDataHeader.get(1), personOutput);
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
