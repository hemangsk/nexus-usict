package xyz.hemangkumar.rnfapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sackcentury.shinebuttonlib.ShineButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BlogPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_post);
        ShineButton shineButton = (ShineButton) findViewById(R.id.shine_button);
        shineButton.init(BlogPostActivity.this);

        ShineButton shineButton2 = (ShineButton) findViewById(R.id.shine_button2);
        shineButton2.init(BlogPostActivity.this);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
