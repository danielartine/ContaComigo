package com.devmob.contacomigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.devmob.contacomigo.R;
import com.devmob.contacomigo.fragments.ItemFragmento;
import com.devmob.contacomigo.fragments.PessoaFragmento;
import com.devmob.contacomigo.fragments.RestauranteFragmento;
import com.devmob.contacomigo.fragments.SectionsStatePagerAdapter;
import com.devmob.contacomigo.fragments.TotalFragmento;

/**
 * Created by devmob on 03/05/17.
 */



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int RESTAURANTEFRAG = 0;
    private static final int ITEMFRAG = 1;
    private static final int PESSOAFRAG = 2;
    private static final int TOTALFRAG = 3;

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.containter);
        //setup the pager
        setupViewPager(mViewPager);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.restIcon:
                                item.setChecked(true);
                                setViewPager(RESTAURANTEFRAG);
                                break;
                            case R.id.moneyIcon:
                                //textFavorites.setVisibility(View.VISIBLE);
                                //textSchedules.setVisibility(View.GONE);
                                //item.setIcon(R.drawable.ic_people_black_48dp);
                                item.setChecked(true);
                                setViewPager(ITEMFRAG);
                                //Toast.makeText(ItemsActivity.this, "Money", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.personIcon:
                                item.setChecked(true);
                                setViewPager(PESSOAFRAG);
                                break;
                            case R.id.tempIcon:
                                //bottomNavigationView.setItemBackgroundResource(R.color.black);
                                item.setChecked(true);
                                setViewPager(TOTALFRAG);
                                break;
                        }
                        return false;
                    }
                });



    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RestauranteFragmento(), "Restaurante");
        adapter.addFragment(new ItemFragmento(), "Item");
        adapter.addFragment(new PessoaFragmento(), "Pessoa");
        adapter.addFragment(new TotalFragmento(), "Total");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }
}