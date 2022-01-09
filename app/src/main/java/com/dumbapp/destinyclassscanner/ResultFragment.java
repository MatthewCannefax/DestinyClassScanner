package com.dumbapp.destinyclassscanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dumbapp.destinyclassscanner.databinding.FragmentResultsBinding;

import java.util.Random;

public class ResultFragment extends Fragment {

    private MainViewModel viewModel;
    private FragmentResultsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((DCSApplication) requireActivity().getApplicationContext()).getDCSApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Random random = new Random();
        final int randomInt = random.nextInt(3);
        final String className;
        switch (randomInt) {
            case 0:
                className = getString(R.string.hunter);
                break;
            case 1:
                className = getString(R.string.warlock);
                break;
            default:
            case 2:
                className = getString(R.string.titan);
        }
        binding.resultTextview.setText(getString(R.string.result_text, className));
    }
}
