package xyz.hemangkumar.rnfapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.hemangkumar.rnfapp.R;

/**
 * Created by Hemang on 04/09/16.
 */
public class Calculator extends Fragment {
    public static Calculator newInstance() {

        Bundle args = new Bundle();

        Calculator fragment = new Calculator();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.calculator_list);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_calculator, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
