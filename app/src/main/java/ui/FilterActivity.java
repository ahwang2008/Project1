package ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ahwang13.familymapserver.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Filter;

import model.Event;
import model.FamilyListAdapter;
import model.Model;
import model.Person;

public class FilterActivity extends AppCompatActivity {

    private List<Person> peopleList;
    private Switch maleSwitch;
    private Switch femaleSwitch;
    private Switch fatherSwitch;
    private Switch motherSwitch;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_filter);

        maleSwitch = (Switch) findViewById(R.id.maleSwitch);
        femaleSwitch = (Switch) findViewById(R.id.femaleSwitch);
        fatherSwitch = (Switch) findViewById(R.id.fatherSwitch);
        motherSwitch = (Switch) findViewById(R.id.motherSwitch);

        maleSwitch.setChecked(Model.instance().isMaleSwitchBoolean());
        maleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Model.instance().setMaleSwitchBoolean(true);
                }
                else {
                    Model.instance().setMaleSwitchBoolean(false);
                }
            }
        });

        femaleSwitch.setChecked(Model.instance().isFemaleSwitchBoolean());
        femaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Model.instance().setFemaleSwitchBoolean(true);
                }
                else{
                    Model.instance().setFemaleSwitchBoolean(false);
                }
            }
        });

        fatherSwitch.setChecked(Model.instance().isFatherSwitchBoolean());
        fatherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Model.instance().setFatherSwitchBoolean(true);
                }
                else {
                    Model.instance().setFatherSwitchBoolean(false);
                }
            }
        });

        motherSwitch.setChecked(Model.instance().isMotherSwitchBoolean());
        motherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Model.instance().setMotherSwitchBoolean(true);
                }
                else {
                    Model.instance().setMotherSwitchBoolean(false);
                }
            }
        });

        expListView = (ExpandableListView) findViewById(R.id.event_filter_list);
        prepareListData();
        listAdapter = new FamilyListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

    }

    public void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Event Type");

        // Adding child data
        List<String> eventsList = new ArrayList<>();
        Set<String> eventsSet = new HashSet<>();
        for (Event e : Model.instance().getEventList()) {
            eventsSet.add(e.getEventType());
        }
        for (String s : eventsSet){
            eventsList.add(s);
        }

        listDataChild.put(listDataHeader.get(0), eventsList); // Header, Child data
    }

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
        }
        return false;
    }
}
