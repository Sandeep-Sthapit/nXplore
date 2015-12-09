package com.sthapit.sandy.finalncell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FoodActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private UserSessionManager userSessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        initContrls();
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
    public void login_button_click(View view) {
        if (userSessionManager.isUserLogin()) {
            userSessionManager.clearSession(); //clear session
            Intent intent = new Intent(FoodActivity.this, FoodActivity.class);
            startActivity(intent);
        } else {
            Intent intent_login = new Intent(FoodActivity.this, LoginActivity.class);
            startActivity(intent_login);
        }
    }


    private void initContrls() {

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Home");
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        userSessionManager = new UserSessionManager(getApplicationContext());


        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        LinearLayout headerView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.drawer_main_header, null);
        Button headerButton = (Button) LayoutInflater.from(this).inflate(R.layout.drawer_header_button, null);
        headerView.addView(headerButton);
        if (userSessionManager.isUserLogin()) {
            headerButton.setText("Logout");
        }
        navigationView.addHeaderView(headerView);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(FoodActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        DesignDemoPagerAdapter adapter = new DesignDemoPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    public static class DesignDemoFragment extends Fragment {
        private static final String TAB_POSITION = "tab_position";
        private RecyclerView recyclerView;

        Map<String, Integer> mapIndex;
        ListView foodList;

        public DesignDemoFragment() {

        }

        public static DesignDemoFragment newInstance(int tabPosition) {
            DesignDemoFragment fragment = new DesignDemoFragment();
            Bundle args = new Bundle();
            args.putInt(TAB_POSITION, tabPosition);
            fragment.setArguments(args);
            return fragment;
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int tabPosition = args.getInt(TAB_POSITION);
            View view = inflater.inflate(R.layout.drawer_main_header, container, false);

            switch (tabPosition) {

                case 0:
                    view = inflater.inflate(R.layout.food_fragment_list, container, false);
                    recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
                    CardView cardView;
                    ArrayList<Items> item;
                    RecyclerView.Adapter mAdapter;

                    final String[] versions = {"American", "British", "Chinese", "French", "Indian", "Italian", "Japanese", "Korean", "Mexican", "Nepali"};
                    final int[] icons = {R.drawable.american, R.drawable.british, R.drawable.chinese, R.drawable.french, R.drawable.indian, R.drawable.italian, R.drawable.japanese, R.drawable.korean, R.drawable.mexican, R.drawable.nepali};

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
                    mAdapter = new RecyclerViewAdapter(item);
                    // set the adapter object to the RecyclerView
                    recyclerView.setAdapter(mAdapter);
                    break;

                case 1:
                    view = inflater.inflate(R.layout.food_fragment_list, container, false);
                    recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
                    ArrayList<GlossaryItem> glossaryItem;
                    RecyclerView.Adapter gAdapter;

                    final String[] names = {"Almond","Apple","Apricot","Asafoetida","Avocado","Banana","Barberry, Nepal","Bayar (Chinese Date, Indian Plum)","Bay-Berry","Bay Leaf","Bead Plum","Betel nut ","Black cardamom ","Black Pepper","Black Salt","Blueberry","Brown Mustard Seed","Carom/thyme seed-Jwano","Cashew-nut","Cherry","Chili","Cinnamon","Clove","Coconut","Coconut","Coriander green","Coriander Powder","Coriander powder","Coriander seed","Cumin seed grounded into ball","Cumin seed","Curd/Yogurt","Curry Tree Leaf","Custard Apple or Sugar Apple","Date","Dates","Dates","Dried Ginger ","Fenugreek leaf","Fenugreek","Fig","Fig","Garlic", "Ginger","Goji berry","Gooseberry, Indian","Grape", "Grapefruit","Green Cardomom","Green Chili","Guava","Holy Basil","Jack Fruit","Java Plum, Black Plum","Jimboo","Kumquat","Lapsi","Lemon, (chook-amilo lemon)","Lemon, Citron","Lemon/ Lime ","Lemon, Rough","Lemon (very sour)","Lemon (yellow)","Licorice","Lime (green) Kaagati","Lime sweet","Loquat – Loquat","Lychee, Lichee","Mango","Mint","Mulberry (black, red, local)","Mustard Oil ","Nepali gooseberry","Nutmeg","Oleaster","Onion","Orange","Orange Keep, (Spur Orange, Bitter Orange)","Orange","Orange (sweet or Malta orange)","Papaya","Peach","Pear Chinese ","Pear","Persimmon","Persimmon","Pineapple","Pistachio","Plum","Pomegranate","Pomegranate seed","Pomilo or Pummelo","Poppy seed","Quince","Radish Seed","Raisins","Raspberry Himalayan","Red Chili","Red Pepper ","Rose water","Saffron","Salt","Sati Bayar","Seseme Oil ","Seseme seed","Sour Mango powder","Spice mixture","Starfruit","Strawberry","Tamarind","Tomato","Turmeric","Turmeric","Unrefined Sugar","Wall Nut","Walnut (thick shell Black)","Water Melon","Wild Pear","Wood Apple, Bengal quince"};
                    final String[] nepali = {" Kagji Badam "," Shaayau "," Khurpaani "," Hing "," Gheuphal "," Keraa "," Chutro "," Bayar  ","Berry "," Tej Patta  "," Haade Bayer "," Supari "," Alaichi "," Marich  "," Birenun "," Neelo Aaiselu "," Rayo "," Jwano ","nut "," Paiyun "," Khursani  "," Dalchini "," Luwang "," Naribel "," Nariwal "," Hario Dhaniya "," Dhania powder "," Dhaniya "," Dhania "," Jeera Goli "," Jeera "," Dahi  "," Kary"," Sariphaa "," Khajoor "," Chodda "," Khajur "," Sutho "," Methi leaves "," Methi "," Anjir, Nevaro "," Anjir  "," Lasun  "," Aduwa  "," Goji Beri "," Amalaa "," Angoor "," Sankhatro "," Sukumel "," Hariyo khursani "," Ambaa or Ammaa "," Tulsi "," Rukh Kathar "," Jaamun "," Jimboo "," Muntalaa "," Nepalese Hog Plum ","amilo lemon) "," Bimiro "," Kagati "," Kaathe Jyamir "," Jyaamir "," Nibuaa "," Jethi madhu "," Kaagati "," Chaaksi "," (लौकाट) "," Lichi "," Aanp "," Pudina "," Kimbu "," Tori ko tel "," Amala "," Jaiphal "," Maadilo "," Pyaj "," Junaar "," Keep "," Suntalaa ","  Maushambi "," Papitaa or Mewaa ","aru "," Chinia Naspaati "," Nashpaati "," Haluaabed "," Haluaabed "," Bhuin Katahar "," Pista "," Alubukharaa "," Anaar, Daarim "," Anardana "," Bhogate "," Khus Khus"," Nepali Shaayau "," Mula ko Biu "," Kismis "," Ainselu "," Rato Khursani "," Timur "," Gulab Jal "," Kesar"," Nun "," (Nepal sumac) "," Teel ko tel "," Teel "," Amchor powder "," Garam Masala "," Kantaraa "," Straberi "," Imali "," Golbheda "," Besar"," Haledo/Beshar "," Gur/ Sakhar"," Okhar  "," Haade Okhar "," Kharbujaa "," Mayal "," Bel "};

                    glossaryItem = new ArrayList<GlossaryItem>();

                    for (int i = 0; i < names.length; i++) {
                        GlossaryItem feed = new GlossaryItem();
                        feed.setName(names[i]);
                        feed.setName_nepali(nepali[i]);
                        glossaryItem.add(feed);
                    }

                    recyclerView.setHasFixedSize(true);
                    // GridView
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    // create an Object for Adapter
                    gAdapter = new GlossaryAdapter(glossaryItem);
                    // set the adapter object to the RecyclerView
                    recyclerView.setAdapter(gAdapter);
                    break;
            }
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
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence tab_title;
            switch (position) {
                case 0:
                    tab_title = "Cuisines";
                    break;
                case 1:
                    tab_title = "Glossary";
                    break;
                case 2:
                    tab_title = "Specials";
                    break;
                default:
                    tab_title = "food";
            }
            return tab_title;
        }
    }
}