package xyz.hemangkumar.rnfapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.hemangkumar.rnfapp.R;

/**
 * Created by Hemang on 04/09/16.
 */
public class TimeTable extends Fragment {
    public static TimeTable newInstance() {

        Bundle args = new Bundle();

        TimeTable fragment = new TimeTable();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.tt_list);
    }
}
