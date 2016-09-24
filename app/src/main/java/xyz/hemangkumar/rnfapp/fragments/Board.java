package xyz.hemangkumar.rnfapp.fragments;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import xyz.hemangkumar.rnfapp.R;
import xyz.hemangkumar.rnfapp.models.*;
import xyz.hemangkumar.rnfapp.utils.Ellipsize;

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
                else {
                    return 1;
                }

            }
        });

        recycler.setLayoutManager(gridLayoutManager);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogHolder>(Blog.class, R.layout.blog_list_item, BlogHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(BlogHolder blogViewHolder, Blog blog, int position) {
                blogViewHolder.bindView(blog);
            }
        };

        recycler.setAdapter(firebaseRecyclerAdapter);
        recycler.smoothScrollToPosition(firebaseRecyclerAdapter.getItemCount());


    }

    private static class BlogHolder extends RecyclerView.ViewHolder{

        View view;
        TextView title, text, subheading;
        Context ctx;

        public BlogHolder(View itemView) {
            super(itemView);
            view = itemView;
            ctx = itemView.getContext();
        }

        public void bindView(Blog blog){

            TextView title, subheading, text;
            title = (TextView) view.findViewById(R.id.blog_title);
            subheading = (TextView) view.findViewById(R.id.blog_subheading_text);
            text = (TextView) view.findViewById(R.id.blog_text);

            ImageView image;
            image = (ImageView) view.findViewById(R.id.blog_img_view);

            if(blog.getPicture()!=null){
                if(!blog.getPicture().equals("") && !blog.getPicture().equals(" ")){
                    Picasso.with(ctx)
                            .load(blog.getPicture())
                            .into(image);
                }
                else{
                    image.setImageResource(android.R.color.transparent);
                }
            }

            title.setText(blog.getTitle());
            text.setText(blog.getText());
            subheading.setText(blog.getSubheading());

        }


    }

}
