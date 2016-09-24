package xyz.hemangkumar.rnfapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

import xyz.hemangkumar.rnfapp.LoginActivity;
import xyz.hemangkumar.rnfapp.R;
import xyz.hemangkumar.rnfapp.models.*;

/**
 * Created by Hemang on 19/09/16.
 */
public class PostBlog extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    FloatingActionButton btn;
    EditText blogTitle, blogAuthor,  blogPicture, blogContent, blogSubHeading;
    String postTime, category;
    View fragment_view;
    FirebaseAuth auth;

    public static PostBlog newInstance() {

        Bundle args = new Bundle();

        PostBlog fragment = new PostBlog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
       return inflater.inflate(R.layout.fragment_post_blog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragment_view = view;
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()==null){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent,3);

        }

        else{
            Toast.makeText(getActivity(), "Already Logged In!", Toast.LENGTH_SHORT).show();
        }

        btn = (FloatingActionButton) view.findViewById(R.id.fab_blog);
        btn.setOnClickListener(this);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.blog_category, R.layout.blog_category_spinner_item);


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        blogAuthor = (EditText) view.findViewById(R.id.blog_author);
        blogContent = (EditText) view.findViewById(R.id.blog_content_text);
        blogPicture = (EditText) view.findViewById(R.id.blog_image_link);
        blogTitle = (EditText) view.findViewById(R.id.blog_title_text);
        blogSubHeading = (EditText) view.findViewById(R.id.blog_subheading_text);




    }

    @Override
    public void onClick(View v) {

        String author = blogAuthor.getText().toString();
        String content = blogContent.getText().toString();
        String picture = blogPicture.getText().toString();
        String title = blogTitle.getText().toString();
        String subheading = blogSubHeading.getText().toString();
        Calendar c = Calendar.getInstance();
        postTime =  c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH ) +
                " " +  c.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.ENGLISH);

        if(author.equals("") || author.equals(" ") ){
            Toast.makeText(getActivity(), "Enter author name!", Toast.LENGTH_SHORT).show();
        }
        else if(content.equals("") || content.equals(" ")){
            Toast.makeText(getActivity(), "Enter content!", Toast.LENGTH_SHORT).show();
        }
        else if(title.equals("")||title.equals(" ")){
            Toast.makeText(getActivity(), "Enter a title for blog!", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("blogs");
            Blog blog = new Blog(author, content, title, postTime, category, picture, subheading);
            ref.push().setValue(blog);
            Snackbar.make(getView(), "Blog Posted!", Snackbar.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:category = "General";
                    break;
            case 1: category = "Stories";
                break;
            case 2: category = "Sports";
                break;
            case 3: category = "Technology";
                break;
            case 4: category = "Science";
                break;
            case 5: category = "Social";
                break;
            default:category = "General";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
