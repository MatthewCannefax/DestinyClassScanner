package com.dumbapp.destinyclassscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.dumbapp.destinyclassscanner.databinding.ActivityMainBinding;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainViewModelFactory mainViewModelFactory;

    private NavHostFragment navHostFragment;
    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((DCSApplication) getApplicationContext()).getDCSApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        viewModel = new ViewModelProvider(this, mainViewModelFactory).get(MainViewModel.class);

        navHostFragment = NavHostFragment.create(R.navigation.main_navigation_graph);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit();
    }
}