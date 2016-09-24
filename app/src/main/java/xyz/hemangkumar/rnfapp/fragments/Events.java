package xyz.hemangkumar.rnfapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import xyz.hemangkumar.rnfapp.R;
import xyz.hemangkumar.rnfapp.models.Event;


/**
 * Created by Hemang on 04/09/16.
 */
public class Events extends Fragment {

    RecyclerView recycler;

    public static Events newInstance() {

        Bundle args = new Bundle();

        Events fragment = new Events();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.event_list);



        Firebase.setAndroidContext(getActivity());

        FirebaseRecyclerAdapter<Event, EventHolder> mAdapter;


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");

        recycler = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recycler.setLayoutManager(layoutManager);

        mAdapter = new FirebaseRecyclerAdapter<Event, EventHolder>(Event.class, R.layout.event_list_item, EventHolder.class, ref) {
            @Override
            public void populateViewHolder(EventHolder eventViewHolder, Event event, int position) {
                eventViewHolder.bindView(event);
                eventViewHolder.setListener(event.getDetail(), event.getOrganiser(), event.getContact(), getActivity());
            }
        };
        recycler.setAdapter(mAdapter);
        recycler.smoothScrollToPosition(mAdapter.getItemCount());


    }


    public static class EventHolder extends RecyclerView.ViewHolder {
        View view;

        public EventHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void bindView(Event event){
            TextView titleTextView, dateTextView, timeTextView, categoryTextView, venueTextView;
            titleTextView = (TextView) view.findViewById(R.id.event_title);
            dateTextView = (TextView) view.findViewById(R.id.event_date);
            timeTextView = (TextView) view.findViewById(R.id.event_time);
            categoryTextView = (TextView) view.findViewById(R.id.event_category);
            venueTextView = (TextView) view.findViewById(R.id.event_venue);
            TextView sidebarTextView = (TextView) view.findViewById(R.id.sidebarText);

            titleTextView.setText(event.getTitle());
            dateTextView.setText(event.getDate());
            timeTextView.setText(event.getTitle());
            categoryTextView.setText(event.getCategory());
            venueTextView.setText(event.getVenue());

            if(event.getTitle().length()>0)
                sidebarTextView.setText(String.valueOf(event.getTitle().charAt(0)));
            switch (event.getCategory()){
                case "Workshop":
                    sidebarTextView.setBackgroundColor(Color.CYAN);
                    break;
                case "Seminar":
                    sidebarTextView.setBackgroundColor(Color.CYAN);
                    break;
                case "Meetup":
                    sidebarTextView.setBackgroundColor(Color.CYAN);
                    break;
                case "Interactive Session":
                    sidebarTextView.setBackgroundColor(Color.CYAN);
                    break;
                case "Other":
                    sidebarTextView.setBackgroundColor(Color.CYAN);
                    break;
                default:sidebarTextView.setBackgroundColor(Color.CYAN);
                        break;
            }
        }


        public void setListener(String detail, String organiser, String contact, Context ctx){

            final String d = detail;
            final Context c = ctx;
            final String org = organiser, contactString = contact;

            Button btn = (Button) view.findViewById(R.id.event_details_button);

            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(c);

                    dialog.setContentView(R.layout.dialog_fragment_event_list_item);
                    dialog.setTitle("Hello!");

                    TextView textViewUser = (TextView) dialog.findViewById(R.id.event_detail);
                    textViewUser.setText(d);

                    TextView textViewOrg = (TextView) dialog.findViewById(R.id.event_organiser);
                    textViewOrg.setText(org);

                    TextView textViewContact = (TextView) dialog.findViewById(R.id.event_contact);
                    textViewContact.setText(contactString);

                    Button ok = (Button) dialog.findViewById(R.id.ok_button);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(dialog.isShowing()){
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show();
                }
            });

        }
    }

}
