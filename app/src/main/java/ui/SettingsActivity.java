package ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ahwang13.familymapserver.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import model.Model;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner lifeSpinner;
    private Spinner familySpinner;
    private Spinner spouseSpinner;
    private Spinner mapTypeSpinner;
    private Switch lifeSwitch;
    private Switch familySwitch;
    private Switch spouseSwitch;
    private LinearLayout logoutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_settings);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors_array, R.layout.support_simple_spinner_dropdown_item);
        lifeSpinner = (Spinner) findViewById(R.id.lifeSpinner);
        lifeSpinner.setOnItemSelectedListener(this);
        lifeSpinner.setAdapter(adapter);
        lifeSpinner.setSelection(Model.instance().getLifeLineColorSaved());

        familySpinner = (Spinner) findViewById(R.id.familySpinner);
        familySpinner.setOnItemSelectedListener(this);
        familySpinner.setAdapter(adapter);
        familySpinner.setSelection(Model.instance().getFamilyLineColorSaved());

        spouseSpinner = (Spinner) findViewById(R.id.spouseSpinner);
        spouseSpinner.setOnItemSelectedListener(this);
        spouseSpinner.setAdapter(adapter);
        spouseSpinner.setSelection(Model.instance().getSpouseLineColorSaved());

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.maps_array, R.layout.support_simple_spinner_dropdown_item);
        mapTypeSpinner = (Spinner) findViewById(R.id.mapTypeSpinner);
        mapTypeSpinner.setOnItemSelectedListener(this);
        mapTypeSpinner.setAdapter(adapter1);
        mapTypeSpinner.setSelection(Model.instance().getMapTypeSaved());

        lifeSwitch = (Switch) findViewById(R.id.lifeSwitch);
        lifeSwitch.setChecked(Model.instance().isLifeSwitchBoolean());
        lifeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if (isChecked){
                  Model.instance().setLifeSwitchBoolean(true);
              }
              else{
                  Model.instance().setLifeSwitchBoolean(false);
              }
          }
        });
        //lifeSwitch.setChecked(true);

        familySwitch = (Switch) findViewById(R.id.familySwitch);
        familySwitch.setChecked(Model.instance().isFamilySwitchBoolean());
        familySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Model.instance().setFamilySwitchBoolean(true);
                }
                else{
                    Model.instance().setFamilySwitchBoolean(false);
                }
            }
        });

        spouseSwitch = (Switch) findViewById(R.id.spouseSwitch);
        spouseSwitch.setChecked(Model.instance().isSpouseSwitchBoolean());
        spouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Model.instance().setSpouseSwitchBoolean(true);
                }
                else{
                    Model.instance().setSpouseSwitchBoolean(false);
                }
            }
        });

        //final SettingsActivity settingsActivity = this;
        logoutLayout = (LinearLayout) findViewById(R.id.logoutLayout);
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        int color = 0;

        if(spinner.getId() == R.id.mapTypeSpinner)
        {
            Model.instance().setMapTypeSaved(position);
        }
        //int colorSaved = 0;
        else
        {
            if (parent.getItemAtPosition(position).equals("red")) {
                color = Color.RED;
            }
            else if (parent.getItemAtPosition(position).equals("blue")) {
                color = Color.BLUE;
            }
            else if (parent.getItemAtPosition(position).equals("yellow")) {
                color = Color.YELLOW;
            }
            else if (parent.getItemAtPosition(position).equals("green")) {
                color = Color.GREEN;
            }
            else if (parent.getItemAtPosition(position).equals("cyan")) {
                color = Color.CYAN;
            }
            else if (parent.getItemAtPosition(position).equals("gray")) {
                color = Color.GRAY;
            }

            if (spinner.getId() == R.id.lifeSpinner)
            {
                Model.instance().setLifeLineColor(color);
                Model.instance().setLifeLineColorSaved(position);
            }
            else if (spinner.getId() == R.id.familySpinner)
            {
                Model.instance().setFamilyLineColor(color);
                Model.instance().setFamilyLineColorSaved(position);
            }
            else if (spinner.getId() == R.id.spouseSpinner)
            {
                Model.instance().setSpouseLineColor(color);
                Model.instance().setSpouseLineColorSaved(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
