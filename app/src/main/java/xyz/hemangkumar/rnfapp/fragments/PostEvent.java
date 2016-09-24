package xyz.hemangkumar.rnfapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import xyz.hemangkumar.rnfapp.LoginActivity;
import xyz.hemangkumar.rnfapp.R;
import xyz.hemangkumar.rnfapp.models.*;
import xyz.hemangkumar.rnfapp.utils.Months;

/**
 * Created by Hemang on 04/09/16.
 */
public class PostEvent extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    View fragment_view;
    FirebaseAuth auth;
    TextView time_text_view;
    TextView date_text_view;
    Button btn1, btn2;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    FloatingActionButton fab;
    EditText titleEditText, organiserEditText, contactDetailsEditText , venueEditText , detailsEditText;
    String title, organiser, contact, venue, details, date, time, category;

    public static PostEvent newInstance() {
        
        Bundle args = new Bundle();
        
        PostEvent fragment = new PostEvent();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_post_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragment_view = view;

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.post_event_list);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()==null){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,3);

        }

        else{
            Toast.makeText(getActivity(), "Already Logged In!", Toast.LENGTH_SHORT).show();
        }

        organiserEditText = (EditText) view.findViewById(R.id.organiserNameText);
        contactDetailsEditText = (EditText) view.findViewById(R.id.organiserContactText);
        venueEditText = (EditText) view.findViewById(R.id.venueEditText);
        detailsEditText = (EditText) view.findViewById(R.id.detailsEditText);
        titleEditText = (EditText) view.findViewById(R.id.eventEditText);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        time_text_view = (TextView) view.findViewById(R.id.timeTextView);
        date_text_view = (TextView) view.findViewById(R.id.dateTextView);

        btn1 = (Button) view.findViewById(R.id.button);
        btn2 = (Button) view.findViewById(R.id.button2);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.event_category, R.layout.event_category_spinner_item);


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        fab.setOnClickListener(this);

        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                dpd.show(getActivity().getFragmentManager(), "Pick a Date");
                break;

            case R.id.button2:
                tpd.show(getActivity().getFragmentManager(), "Pick a Time");
                break;

            case R.id.fab:

                getDataFromViews();
                Boolean res = validate();

                if(res){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");
                    xyz.hemangkumar.rnfapp.models.Event event = new xyz.hemangkumar.rnfapp.models.Event(organiser,  details, venue, date, time, category, title, contact);
                    ref.push().setValue(event);
                    Snackbar.make(getView(), "Events Created Successfully!", Snackbar.LENGTH_SHORT).show();
                    break;
                }

        }
    }

    private void getDataFromViews() {
        organiser = organiserEditText.getText().toString();
        contact = contactDetailsEditText.getText().toString();
        venue = venueEditText.getText().toString();
        details = detailsEditText.getText().toString();
        title = titleEditText.getText().toString();
    }


    private Boolean validate() {

        if(title.equals("") || title.equals(" ")){
            Snackbar.make(fragment_view, "Events Name not entered!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if(organiser.equals("") || organiser.equals(" ")){
            Snackbar.make(fragment_view, "Organiser not entered!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        else if(contact.equals("") || contact.equals(" ")){
            Snackbar.make(fragment_view, "Contact details not entered!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        else if(venue.equals("") || venue.equals(" ")){
            Snackbar.make(fragment_view, "Venue not entered!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        else if(category.equals("") || category.equals(" ")){
            Snackbar.make(fragment_view, "Please specify a category!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        date = String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth) + " to " + String.valueOf(yearEnd) + "-" + String.valueOf(monthOfYearEnd) + "-" + String.valueOf(dayOfMonthEnd);
        date = String.valueOf(Months.getMonthFromDate(String.valueOf(monthOfYear+1))) + " " + String.valueOf(dayOfMonth);

        date_text_view.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0"+hourOfDayEnd : ""+hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0"+minuteEnd : ""+minuteEnd;
        time = hourString+":"+minuteString+" to "+hourStringEnd+":"+minuteStringEnd;

        time_text_view.setText(time);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position){
            case 0:category = "";
                break;
            case 1: category = "Workshop";
                break;
            case 2: category = "Seminar";
                break;
            case 3: category = "Meetup";
                break;
            case 4: category = "Interactive Session";
                break;
            case 5: category = "Other";
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        category = "Other";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.toolbar_menu_post_fragments, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Snackbar.make(fragment_view, "Logged out Successfully", Snackbar.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) getActivity();
                FragmentManager fragmentManger = activity.getSupportFragmentManager();
                fragmentManger.beginTransaction().replace(R.id.frame_layout, Home.newInstance()).commit();
                activity.getSupportActionBar().setTitle("Home");
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3 && data!=null){

                String status = data.getStringExtra("STATUS");
                if(status!=null){
                 //   Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                    if(status.equals("false")){
                        AppCompatActivity activity = (AppCompatActivity) getActivity();
                        FragmentManager fragmentManger = activity.getSupportFragmentManager();
                        fragmentManger.beginTransaction().replace(R.id.frame_layout, Home.newInstance()).commit();
                        activity.getSupportActionBar().setTitle("Home");
                    }
                }

        }
    }
}
