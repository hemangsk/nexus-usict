package xyz.hemangkumar.rnfapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import xyz.hemangkumar.rnfapp.R;
import xyz.hemangkumar.rnfapp.models.*;

/**
 * Created by Hemang on 04/09/16.
 */
public class Board extends Fragment {
    public static Board newInstance() {

        Bundle args = new Bundle();

        Board fragment = new Board();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.board_list);

        Firebase.setAndroidContext(getActivity());
        FirebaseRecyclerAdapter<Blog, BlogHolder> firebaseRecyclerAdapter;


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("blogs");
        Blog blog = new Blog("Hemang", "THis", "Theory Of Everything", "January 6", "LEISURE", "The Story of my Tale");
        ref.push().setValue(blog);
        ref.push().setValue(blog);

        ref.push().setValue(blog);

        ref.push().setValue(blog);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("blogs");
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recyclerview_blog);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int mod = position % 3;

                if(mod == 0){
                    return 2;
                }
                else if(mod == 1 || mod == 2 ){
                    return 1;
                }
                else{
                    return 2;
                }

            }
        });

        recycler.setLayoutManager(gridLayoutManager);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogHolder>(Blog.class, R.layout.blog_list_item, BlogHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(BlogHolder blogViewHolder, Blog blog, int position) {
                blogViewHolder.setTitle(blog.getTitle());
               // blogViewHolder.setText(blog.getText());
                blogViewHolder.setDate(blog.getDate());
                blogViewHolder.setAuthor(blog.getAuthor());
                blogViewHolder.setCategory(blog.getCategory());
                blogViewHolder.setSubHeading(blog.getSubheading());
            }
        };

        recycler.setAdapter(firebaseRecyclerAdapter);
        recycler.smoothScrollToPosition(firebaseRecyclerAdapter.getItemCount());


    }

    private static class BlogHolder extends RecyclerView.ViewHolder{

        View view;

        public BlogHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setTitle(String data){
            TextView textView = (TextView) view.findViewById(R.id.blog_title);
            textView.setText(data.toString());

        }
        public void setText(String data){
           // TextView textView = (TextView) view.findViewById(R.id.blog_text);
            //textView.setText(data.toString());

        }
        public void setAuthor(String data){
            TextView textView = (TextView) view.findViewById(R.id.blog_author);
            textView.setText(data.toString());

        }
        public void setDate(String data){
            TextView textView = (TextView) view.findViewById(R.id.blog_date);
            textView.setText(data.toString());

        }
        public void setCategory(String data){
            TextView textView = (TextView) view.findViewById(R.id.blog_category);
            textView.setText(data.toString());

        }

        public void setSubHeading(String data){
            TextView textView = (TextView) view.findViewById(R.id.blog_subheading_text);
            textView.setText(data.toString());
        }
    }

}
