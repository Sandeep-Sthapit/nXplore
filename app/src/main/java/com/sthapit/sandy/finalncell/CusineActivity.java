package com.sthapit.sandy.finalncell;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CusineActivity extends ActionBarActivity {


    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private UserSessionManager userSessionManager;
    ImageView image;
    String title = "food";
    int image_id = R.drawable.american;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusine);

        initControls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //if user clicks login button in drawerHeader
    public void login_button_click(View view){
        if(userSessionManager.isUserLogin())
        {
            userSessionManager.clearSession(); //clear session
            Intent intent = new Intent(CusineActivity.this, CusineActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent_login = new Intent(CusineActivity.this, LoginActivity.class);
            startActivity(intent_login);
        }
    }


    private void initControls() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("item_title");
            image_id = extras.getInt("item_image");
        }

        image = (ImageView) findViewById(R.id.image_cusine);
        image.setImageResource(image_id);
        //toolbar
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_cusine);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        setPalette();

        toolbar = (Toolbar) findViewById(R.id.toolbar_cusine);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            if(extras!= null){
                actionBar.setTitle(extras.getString("item_title"));
            }
            else
                actionBar.setTitle("title");
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_cusine);
        userSessionManager = new UserSessionManager(getApplicationContext());


        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_cusine);
        LinearLayout headerView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.drawer_main_header, null);
        Button headerButton = (Button) LayoutInflater.from(this).inflate(R.layout.drawer_header_button, null);
        headerView.addView(headerButton);
        if(userSessionManager.isUserLogin())
        {
            headerButton.setText("Logout");
        }
        navigationView.addHeaderView(headerView);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(CusineActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        DesignDemoPagerAdapter adapter = new DesignDemoPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_cusine);
        viewPager.setAdapter(adapter);

    }
    private void setPalette() {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int primaryDark = getResources().getColor(R.color.primary_dark);
                int primary = getResources().getColor(R.color.primary);
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantColor(primaryDark));
            }
        });

    }

    public static class DesignDemoFragment extends Fragment {
        private RecyclerView recyclerView;
        public DesignDemoFragment() {

        }

        public static DesignDemoFragment newInstance(int tabPosition) {
            DesignDemoFragment fragment = new DesignDemoFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            View view = inflater.inflate(R.layout.drawer_main_header, container, false);

                    view = inflater.inflate(R.layout.food_fragment_list, container, false);
                    recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
                    CardView cardView;
                    ArrayList<Items> item;
                    RecyclerView.Adapter mAdapter;

                    final String[] versions = {"DalBhat", "Dhindo","Khichuri" , "Kwati", "Phapar", "SandhekoGundruk", "SellRoti", "Yomari"};
                    final int[] icons = {R.drawable.dalbhat, R.drawable.dhindo, R.drawable.khichuri, R.drawable.kwati, R.drawable.phapar, R.drawable.sandheko_gundruk, R.drawable.selroti, R.drawable.yomari};

                    item = new ArrayList<Items>();

                    for (int i = 0; i < versions.length; i++) {
                        Items feed = new Items();
                        feed.setTitle(versions[i]);
                        feed.setThumbnail(icons[i]);
                        item.add(feed);
                    }

                    recyclerView.setHasFixedSize(true);
                    // GridView
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    // create an Object for Adapter
                    mAdapter = new CusineItemAdapter(item);
                    // set the adapter object to the RecyclerView
                    recyclerView.setAdapter(mAdapter);


            return view;
        }
    }


    static class DesignDemoPagerAdapter extends FragmentStatePagerAdapter {

        public DesignDemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DesignDemoFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence tab_title;
            switch (position) {
                case 0:
                    tab_title = "Cusines";
                    break;
                default:
                    tab_title = "food";
            }
            return tab_title;
        }
    }
}