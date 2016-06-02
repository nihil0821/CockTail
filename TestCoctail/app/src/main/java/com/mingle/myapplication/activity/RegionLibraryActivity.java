package com.mingle.myapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.mingle.entity.MenuEntity;
import com.mingle.myapplication.MessageDialog;
import com.mingle.myapplication.R;
import com.mingle.myapplication.WebDialog;
import com.mingle.myapplication.model.SharedPreferenceUtil;
import com.mingle.myapplication.severcall.Servercall;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.mingle.sweetpick.ViewPagerDelegate;

import java.util.ArrayList;

public class RegionLibraryActivity extends AppCompatActivity {
    private SweetSheet mSweetSheet3;
    private RelativeLayout rl;
    Toolbar toolbar;
    Toolbar bottombar;
    Button homeButton;
    Button cinemaButton;
    Button exhibitButton;
    ToggleButton bottomToggleButton;
    ImageView library_back;
    ImageView library_icon;
    ImageView library_edge;
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap bitmap3;

    Servercall servercall;
    String library;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resion_library);

        Intent intent=new Intent(getApplicationContext(), WebDialog.class);
        startActivity(intent);
        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        servercall=new Servercall();
        library="library";
        servercall.postResioninfo(getApplicationContext(), library);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.library);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.library_edge);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.library_icon);

        library_back = (ImageView) findViewById(R.id.library_back);
        library_edge = (ImageView) findViewById(R.id.library_edge);
        library_icon = (ImageView) findViewById(R.id.library_icon);

        library_back.setImageBitmap(bitmap);
        library_edge.setImageBitmap(bitmap2);
        if(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "ResionMajor") == 18243) {
            library_edge.setAnimation(animRotate);
        }
        library_icon.setImageBitmap(bitmap3);


        homeButton = (Button) findViewById(R.id.home_btn);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
                finish();
            }
        });

        cinemaButton = (Button) findViewById(R.id.cinema_btn);
        cinemaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cinema = new Intent(getApplicationContext(), ResionCinemaActivity.class);
                cinema.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cinema);
                finish();
            }
        });

        exhibitButton=(Button)findViewById(R.id.exhibition_btn);
        exhibitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exhibition=new Intent(getApplicationContext(),ResionExhibitionActivity.class);
                exhibition.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exhibition);
                finish();
            }
        });

        rl = (RelativeLayout) findViewById(R.id.rl);
        setupCustomView();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(getLayoutInflater().inflate(R.layout.actionbar_layout, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );

        bottombar = (Toolbar) findViewById(R.id.bottombar);
        setSupportActionBar(bottombar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(getLayoutInflater().inflate(R.layout.bottombar_layout, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
        bottomToggleButton = (ToggleButton) findViewById(R.id.bottomToggleButton);
        bottomToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomToggleButton.isChecked()) {
                    mSweetSheet3.show();
                } else {
                    mSweetSheet3.dismiss();
                }
            }
        });

        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 1); // 다른 지역에서 callservice 사용 안함

        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageDialog();
            }
        });
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmap.recycle();
        bitmap2.recycle();
        bitmap3.recycle();
    }

    public void messageDialog() {
        MessageDialog messgeDialog = new MessageDialog();
        messgeDialog.show(getFragmentManager(), "UserContext");
        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "SectorId", library);
        messgeDialog.setCancelable(true);
    }

    private void setupCustomView() {
        mSweetSheet3 = new SweetSheet(rl);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.AlphaAnimation);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null, false);
        customDelegate.setCustomView(view);
        customDelegate.setSweetSheetColor(getResources().getColor(R.color.colorBottomtab));
        mSweetSheet3.setDelegate(customDelegate);
        mSweetSheet3.setBackgroundEffect(new BlurEffect(8));
        mSweetSheet3.setBackgroundClickEnable(false);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (mSweetSheet3.isShow()) {
            mSweetSheet3.dismiss();
        } else {
            super.onBackPressed();
        }
        bottomToggleButton.setChecked(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}

