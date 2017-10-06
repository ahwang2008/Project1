package ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.model.Polyline;
import com.amazon.geo.mapsv2.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.MapView;
import com.amazon.geo.mapsv2.MapsInitializer;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptor;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.example.ahwang13.familymapserver.R;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Event;
import model.MapColor;
import model.Model;
import model.Person;

public class MapFragment extends SupportMapFragment implements AmazonMap.OnMarkerClickListener {

    private AmazonMap mMap;
    private List<Marker> markers = new ArrayList<Marker>();
    private MapView mapView;
    private ImageView genderImageView;
    private TextView personView;
    private TextView eventView;
    private Marker m;
    private List<Polyline> polylines = new ArrayList<Polyline>();
    private String eventType;
    private String eventid;
    private String firstName;
    private String lastName;
    private Map<Marker, Person> markerMap;
    private Map<Marker, Event> markerEventMap;
    private ArrayList<Event> eventList;
    private boolean mainAct;

    public MapFragment() {
        markerMap = new TreeMap();
        markerEventMap = new TreeMap();
        mainAct = false;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //setContentView(R.layout.fragment_map);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(getActivity().getApplicationContext());
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(AmazonMap amazonMap) {
                mMap = amazonMap;
                //mainAct = false;
                updateMap();
            }
        });
        //map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        //map.setMapType(Utils.toGoogleMapType(Model.getSettings().getMapType()));
        //map.setOnMarkerClickListener(markerClickListener);

        //map.clear(...)
        //map.addMarker(...)
        //map.addPolyLine(...)
        //map.moveCamera(...)
        //map.getCameraPosition(...)
        //TODO: CREATE LINES IMMEDIATELY WHEN LINES ARE CHANGED


        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeTextView);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                startActivity(intent);
            }
        });
        genderImageView = (ImageView) v.findViewById(R.id.genderIcon);
        personView = (TextView) v.findViewById(R.id.personName);
        eventView = (TextView) v.findViewById(R.id.eventName);
        return v;
    }

    public void updateMap()
    {
        mMap.clear();
        //Event e = Model.instance().getEventByEventID(Model.instance().getCurrEventID());
        /*
        mMap.setMapType(AmazonMap.MAP_TYPE_HYBRID);
        mMap.setMapType(AmazonMap.MAP_TYPE_NORMAL);
        mMap.setMapType(AmazonMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(AmazonMap.MAP_TYPE_TERRAIN);
        */
        //mMap.setMapType(AmazonMap.);
        int mapType = Model.instance().getMapTypeSaved();
        if (mapType == 0) {
            mMap.setMapType(AmazonMap.MAP_TYPE_NORMAL);
        }
        else if (mapType == 1){
            mMap.setMapType(AmazonMap.MAP_TYPE_HYBRID);;
        }
        else if (mapType == 2){
            mMap.setMapType(AmazonMap.MAP_TYPE_SATELLITE);
        }
        else if (mapType == 3){
            mMap.setMapType(AmazonMap.MAP_TYPE_TERRAIN);
        }


        if (getActivity() instanceof MapActivity)
        {
            Event e = Model.instance().getEventByEventID(Model.instance().getCurrEventID());
            LatLng point = new LatLng(Double.parseDouble(e.getLatitude()), Double.parseDouble(e.getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            onMarkerClick(null);
            //drawPolyLines(e, e.getPersonID());
        }
        eventIterator();

        if (getActivity() instanceof MapActivity) {
            drawPolyLines(Model.instance().getEventByEventID(Model.instance().getCurrEventID()), Model.instance().getCurrPersonID());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mainAct == false)//(getActivity() instanceof MainActivity)
        {
            mainAct = true;
        }
        else {
            //mMap.clear();
            updateMap();
            //POSSIBLY CLEAR THIS OUT BELOW
            //Event e = Model.instance().getEventByEventID();
            //drawPolyLines(e, e.getPersonID());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_main, menu);
        if(getActivity() instanceof MapActivity) //Check to see if in the main activity or map activity, and inflate the corresponding ones
        {
            inflater.inflate(R.menu.menu_person, menu);
            MenuItem goToTopButton = menu.findItem(R.id.goToTop);
            goToTopButton.setIcon(new IconDrawable(getActivity(), FontAwesomeIcons.fa_angle_double_up).colorRes(R.color.colorAccent).sizeDp(40));
        }
        else
        {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goToTop:
                Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(mainIntent
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.filter:
                Intent filterIntent = new Intent(getActivity(), FilterActivity.class);
                startActivity(filterIntent);
                return true;
            case R.id.search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                return true;
            default:
        }
        return false;
    }

    public void eventIterator()
    {
        Set<Event> eventList = Model.instance().getEventList();
        mMap.clear();
        for (Event e : eventList)
        {
            placeEvents(e);
        }
    }

    public void placeEvents(Event event)
    {
        float hue = 0;
        if (event.getEventType().equals("birth"))
        {
            hue = BitmapDescriptorFactory.HUE_RED;
            eventType = "birth";
        }
        else if (event.getEventType().equals("death"))
        {
            hue = BitmapDescriptorFactory.HUE_BLUE;
            eventType = "death";
        }
        else if (event.getEventType().equals("baptism"))
        {
            hue = BitmapDescriptorFactory.HUE_GREEN;
            eventType = "baptism";
        }
        else if (event.getEventType().equals("marriage"))
        {
            hue = BitmapDescriptorFactory.HUE_YELLOW;
            eventType = "marriage";
        }

            // Set marker options with the marker position, a simple title,
            // and snippet text with the latitude / longitude location
        LatLng point = new LatLng(Double.parseDouble(event.getLatitude()), Double.parseDouble(event.getLongitude()));

        //BitmapDescriptor itemBitmap = BitmapDescriptorFactory.fromBitmap(mMapImage);
        mMap.setOnMarkerClickListener(this);
        MarkerOptions opt = new MarkerOptions()
                    .position(point)
                    .icon(BitmapDescriptorFactory.defaultMarker(hue));
        //mMap.clear();

        // Add the marker to the map
        m = mMap.addMarker(opt);
        m.setSnippet(event.getPersonID());
        m.setTitle(event.getEventID());
        //Model.instance().setPersonEvents(eventList);

        //Model.instance().setCurrPersonID(event.getPersonID());
        //Person p = Model.instance().getPersonByPersonID();
        // Keep track of the added markers in a list
        markers.add(m);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String personID = Model.instance().getCurrPersonID();
        String eventID = Model.instance().getCurrEventID();
        //TODO:COMMENT THIS OUT

        if (getActivity() instanceof MapActivity)
        {

        }
        else {
            personID = marker.getSnippet();
            Model.instance().setCurrPersonID(personID);
            eventID = marker.getTitle();
            Model.instance().setCurrEventID(eventID);
        }
        Person p = Model.instance().getPersonByPersonID(personID);
        Event e = Model.instance().getEventByEventID(eventID);

        if(p.getGender().equals("m"))
        {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.colorPrimary).sizeDp(40);
            genderImageView.setImageDrawable(genderIcon);
        }
        else if (p.getGender().equals("f"))
        {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.colorAccent).sizeDp(40);
            genderImageView.setImageDrawable(genderIcon);
        }
        TextView name = (TextView) personView;
        name.setText(p.getFirstName() + " " + p.getLastName());
        TextView eventDescription = (TextView) eventView;
        eventDescription.setText(e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")");
        if (getActivity() instanceof MainActivity) {
            drawPolyLines(e, personID);
        }
        return false;
    }

    public void drawPolyLines(Event e, String personID)
    {
        for (Polyline polyline : polylines)
        {
            polyline.remove();
        }
        polylines.clear();
        if (Model.instance().isLifeSwitchBoolean() == true) {
            drawLifeStoryLines(personID);
        }
        if (Model.instance().isFamilySwitchBoolean() == true) {
            drawFamilyTreeLines(e, personID, 0);
        }
        if (Model.instance().isSpouseSwitchBoolean() == true) {
            drawSpouseLines(personID);
        }
    }

    public void drawLifeStoryLines(String personID)
    {
        for (String s : Model.instance().getPersonEvents().keySet())
        {
            if (s.equals(personID))
            {
                //Draw the polylines
                List<Event> ev = Model.instance().getPersonEvents().get(s);
                List<Event> eve = Model.instance().setEventsChronologically(ev);
                ArrayList<LatLng> points = new ArrayList<LatLng>(eve.size());
                for (Event q : eve)
                {
                    LatLng point = new LatLng(Double.parseDouble(q.getLatitude()), Double.parseDouble(q.getLongitude()));
                    points.add(point);
                }
                PolylineOptions opt = new PolylineOptions()
                        .addAll(points)
                        .width(5)
                        .color(Model.instance().getLifeLineColor());
                Polyline polyline = mMap.addPolyline(opt);
                polylines.add(polyline);
            }
        }
    }

    public void drawFamilyTreeLines(Event pointA, String personID, int generations)
    {
        //Access father and mother birth event
        //Model.instance().setCurrPersonID(personID);
        if (pointA != null) {
            Person p = Model.instance().getPersonByPersonID(personID);
            float thickness = 5.0f / (generations + 1);
            Event fatherBirthEvent = null;
            Event motherBirthEvent = null;
            String fatherID = p.getFather();
            String motherID = p.getMother();
            if (fatherID == null || motherID == null) {
                return;
            } else {
                for (String s : Model.instance().getPersonEvents().keySet()) {
                    if (s.equals(p.getFather())) {
                        List<Event> events = Model.instance().getPersonEvents().get(s);
                        ArrayList<LatLng> points = new ArrayList<LatLng>(events.size());

                        //Event e = Model.instance().getEventByEventID();
                        LatLng point = new LatLng(Double.parseDouble(pointA.getLatitude()), Double.parseDouble(pointA.getLongitude()));
                        points.add(point);

                        for (Event q : events) {
                            if (q.getEventType().equals("birth")) {
                                fatherBirthEvent = q;
                                point = new LatLng(Double.parseDouble(q.getLatitude()), Double.parseDouble(q.getLongitude()));
                                points.add(point);
                            }
                        }
                        PolylineOptions opt = new PolylineOptions()
                                .addAll(points)
                                .width(thickness)
                                .color(Model.instance().getFamilyLineColor());
                        Polyline polyline = mMap.addPolyline(opt);
                        polylines.add(polyline);
                        //return;
                    }
                    if (s.equals(p.getMother())) {
                        List<Event> events = Model.instance().getPersonEvents().get(s);
                        ArrayList<LatLng> points = new ArrayList<LatLng>(events.size());

                        //Event e = Model.instance().getEventByEventID();
                        LatLng point = new LatLng(Double.parseDouble(pointA.getLatitude()), Double.parseDouble(pointA.getLongitude()));
                        points.add(point);

                        for (Event q : events) {
                            if (q.getEventType().equals("birth")) {
                                motherBirthEvent = q;
                                point = new LatLng(Double.parseDouble(q.getLatitude()), Double.parseDouble(q.getLongitude()));
                                points.add(point);
                            }
                        }
                        PolylineOptions opt = new PolylineOptions()
                                .addAll(points)
                                .width(thickness)
                                .color(Model.instance().getFamilyLineColor());
                        Polyline polyline = mMap.addPolyline(opt);
                        polylines.add(polyline);
                    }
                }
            }
            drawFamilyTreeLines(fatherBirthEvent, fatherID, generations++);
            drawFamilyTreeLines(motherBirthEvent, motherID, generations++);
        }
    }

    public void drawSpouseLines(String personID)
    {
        Person p = Model.instance().getPersonByPersonID(personID);
        String spouseID = p.getSpouse();

        for (String s : Model.instance().getPersonEvents().keySet())
        {
            if (s.equals(spouseID))
            {
                //Draw the polylines
                List<Event> firstEvent = Model.instance().getPersonEvents().get(s);
                ArrayList<LatLng> points = new ArrayList<LatLng>(firstEvent.size());

                Event e = Model.instance().getEventByEventID(Model.instance().getCurrEventID());
                LatLng point = new LatLng(Double.parseDouble(e.getLatitude()), Double.parseDouble(e.getLongitude()));
                points.add(point);

                for (Event q : firstEvent)
                {
                    point = new LatLng(Double.parseDouble(q.getLatitude()), Double.parseDouble(q.getLongitude()));
                    points.add(point);
                    break; //Grabs only the first event
                }
                PolylineOptions opt = new PolylineOptions()
                        .addAll(points)
                        .width(5)
                        .color(Model.instance().getSpouseLineColor());
                Polyline polyline = mMap.addPolyline(opt);
                polylines.add(polyline);
                return;
            }
        }
    }
}
