package com.sthapit.sandy.finalncell;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodItemActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    private UserSessionManager userSessionManager;
    TabLayout tabLayout;
    ImageView image;
    int background_color = Color.LTGRAY;
    int tv_color = Color.LTGRAY;
    int fab_color = Color.YELLOW;
    int fab_color_dark = Color.GREEN;
    String title = "yomari";
    int image_id = R.drawable.american;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);


        userSessionManager = new UserSessionManager(getApplicationContext());
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroll);
        scrollView.setFillViewport(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("item_title");
            image_id = extras.getInt("item_image");
        }

        String[] dem = {"asd1", "asd2", "asd3"};
        int id = getResources().getIdentifier(title.toLowerCase() + "_origin", "string", "com.sthapit.sandy.finalncell");
        dem[0] = getResources().getString(id);
        id = getResources().getIdentifier(title.toLowerCase() + "_description", "string", "com.sthapit.sandy.finalncell");
        dem[1] = getResources().getString(id);
        id = getResources().getIdentifier(title.toLowerCase() + "_ingredients", "string", "com.sthapit.sandy.finalncell");
        dem[2] = getResources().getString(id);
        Bundle bundle_items = new Bundle();


        image = (ImageView) findViewById(R.id.image);
        image.setImageResource(image_id);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        setPalette();

        bundle_items.putString("description", dem[0]);
        bundle_items.putString("origin", dem[1]);
        bundle_items.putString("ingredients", dem[2]);
        bundle_items.putInt("color", background_color);
        bundle_items.putInt("tv_color", tv_color);
        bundle_items.putInt("fab_color", fab_color);
        bundle_items.putInt("fab_color_dark", fab_color_dark);

        DesignDemoPagerAdapter adapter = new DesignDemoPagerAdapter(getSupportFragmentManager(), bundle_items);
        adapter.setExtras(extras);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_item_view);
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_item_view);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food_item, menu);
        return true;
    }

    private void setPalette() {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int primaryDark = getResources().getColor(R.color.primary_dark);
                int primary = getResources().getColor(R.color.primary);
                int primaryLight = getResources().getColor(R.color.primary_light);
                int accent = getResources().getColor(R.color.accent);
                int white = Color.WHITE;
                collapsingToolbarLayout.setContentScrimColor(palette.getDarkVibrantColor(primaryDark));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(primaryLight));
                tabLayout.setBackgroundColor(palette.getDarkVibrantColor(primaryDark));
                tabLayout.setSelectedTabIndicatorColor(palette.getMutedColor(primaryLight));
                background_color = palette.getDarkMutedColor(primary);
                tv_color = palette.getLightMutedColor(white);
                fab_color = palette.getVibrantColor(accent);
                fab_color = palette.getDarkVibrantColor(accent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DesignDemoFragment extends Fragment {
        private static final String TAB_POSITION = "tab_position";
        private RecyclerView recyclerView;
        private Bundle extras;
        private String[] info = {"a1", "a2", "a3"};

        public DesignDemoFragment() {

        }


        public void setExtras(Bundle extra) {
            extras = extra;
        }

        public static DesignDemoFragment newInstance(int tabPosition, Bundle extra, Bundle bundle) {
            DesignDemoFragment fragment = new DesignDemoFragment();
            fragment.setExtras(extra);
            String a1 = bundle.getString("description");
            String a2 = bundle.getString("origin");
            String a3 = bundle.getString("ingredients");
            int color = bundle.getInt("color");

            Bundle args = new Bundle();
            args.putInt(TAB_POSITION, tabPosition);
            args.putString("origin", a1);
            args.putString("description", a2);
            args.putString("ingredients", a3);
            args.putInt("color", color);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int tabPosition = args.getInt(TAB_POSITION);
            View view = inflater.inflate(R.layout.drawer_main_header, container, false);

            String a1 = args.getString("description");
            String a2 = args.getString("origin");
            String a3 = args.getString("ingredients");
            int color = args.getInt("color");
            int tv_color = args.getInt("tv_color");
            int fab_color = args.getInt("fab_color");
            int fab_color_dark = args.getInt("fab_color_dark");

            switch (tabPosition) {
                case 0:
                    view = inflater.inflate(R.layout.item_description, container, false);
                    ScrollView scrollView = (ScrollView) view.findViewById(R.id.layout_scrool_item_description);
                    scrollView.setBackgroundColor(color);
                    TextView tv_description_title = (TextView) view.findViewById(R.id.tv_item_description_title);
                    TextView tv_origin = (TextView) view.findViewById(R.id.tv_item_origin);
                    TextView tv_description = (TextView) view.findViewById(R.id.tv_item_description);
                    TextView tv_ingredients = (TextView) view.findViewById(R.id.tv_item_ingredient);
                    if (extras != null) {
                        tv_description_title.setText(extras.getString("item_title"));
                        tv_origin.setText(a1);
                        tv_description.setText(a2);
                        tv_ingredients.setText(a3);
                    }
                    break;

                case 1:
                    view = inflater.inflate(R.layout.food_fragment_list, container, false);
                    recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
                    ArrayList<LocationItem> item;
                    RecyclerView.Adapter mAdapter;

                    final String[] hotel_name = {"Hotel Heaven : The Foodland", "The Hotel Malla", "Moondance", "Nepali Cafe", "The Northfiel Cafe", "Restaurant.. Once upon a time"};
                    final String[] hotel_location = {"Thamel, Kathmandu", "Leknath Marg, Kathmandu 44600", "Lakeside Rd, Pokhara 33700", "New Road, Kathmandu", "Jhamsikhel, Lalitpur", "Lakeside, Pokhara"};
                    final String[] hotel_phone = {"01-4435465", "01-4410320", "061-461835", "01-4498884", "01-5587112", "061-468963"};
                    final int[] icons = {R.drawable.hotel_heaven, R.drawable.hotel_malla, R.drawable.hotel_moondance, R.drawable.hotel_nepali_cafe, R.drawable.hotel_northfield, R.drawable.hotel_once};

                    item = new ArrayList<LocationItem>();

                    for (int i = 0; i < hotel_name.length; i++) {
                        LocationItem feed = new LocationItem();
                        feed.setTitle(hotel_name[i]);
                        feed.setLocation(hotel_location[i]);
                        feed.setPhone(hotel_phone[i]);
                        feed.setThumbnail(icons[i]);
                        item.add(feed);
                    }

                    recyclerView.setHasFixedSize(true);
                    // GridView
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    // create an Object for Adapter
                    mAdapter = new LocationAdapter(item);
                    // set the adapter object to the RecyclerView
                    recyclerView.setAdapter(mAdapter);
                    break;

                case 2:
                    view = inflater.inflate(R.layout.item_user, container, false);
                    LinearLayout linearLayout_whole = (LinearLayout) view.findViewById(R.id.layout_item_user);
                    LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout_user_comment);
                    linearLayout_whole.setBackgroundColor(color);
                    linearLayout.setBackgroundColor(tv_color);
                    break;

            }
            return view;
        }
    }

    static class DesignDemoPagerAdapter extends FragmentStatePagerAdapter {
        Bundle extras;
        private final Bundle fragmentBundle;

        public DesignDemoPagerAdapter(FragmentManager fm, Bundle data) {
            super(fm);
            fragmentBundle = data;
        }

        public void setExtras(Bundle extra) {
            extras = extra;
        }

        @Override
        public Fragment getItem(int position) {
            return DesignDemoFragment.newInstance(position, extras, fragmentBundle);
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
                    tab_title = "Info";
                    break;
                case 1:
                    tab_title = "Location";
                    break;
                case 2:
                    tab_title = "Feedback";
                    break;
                default:
                    tab_title = "food";
            }
            return tab_title;
        }
    }

    public void comment_fab_click(View view) {
        if (userSessionManager.isUserLogin()) {
            EditText et_comment = (EditText) findViewById(R.id.et_user_comment);
            Button bt_comment = (Button) findViewById(R.id.bt_user_comment);
            et_comment.setVisibility(View.VISIBLE);
            bt_comment.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(FoodItemActivity.this, "User must log in to comment", Toast.LENGTH_LONG).show();
        }
    }

    public void comment_button_click(View view) {
        EditText et_comment = (EditText) findViewById(R.id.et_user_comment);
        Button bt_comment = (Button) findViewById(R.id.bt_user_comment);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_user_comment);
        TextView tv_comment = (TextView) findViewById(R.id.tv_user_comment);
        tv_comment.setText(et_comment.getText().toString());
        et_comment.setVisibility(View.INVISIBLE);
        bt_comment.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }
}

