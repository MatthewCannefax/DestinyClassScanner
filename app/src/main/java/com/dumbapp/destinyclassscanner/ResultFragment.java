package com.dumbapp.destinyclassscanner;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;

import java.util.List;
import java.util.Optional;
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

        analyseImage(viewModel.getCapture());
    }

    private void analyseImage(final Bitmap bitmap) {
        final InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
        final FaceDetectorOptions highAccuracyOpts =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .build();

        final FaceDetector detector = FaceDetection.getClient(highAccuracyOpts);


        detector.process(inputImage)
                .addOnSuccessListener(
                        faces -> {
                            final Face face = faces.get(0);
                            float leftEyeOpen = 0f;
                            float rightEyeOpen = 0f;
                            float smile = 0f;
                            int classValue = 3;
                            if (face.getLeftEyeOpenProbability() != null) {
                                leftEyeOpen = face.getLeftEyeOpenProbability();
                            }
                            if (face.getRightEyeOpenProbability() != null) {
                                rightEyeOpen = face.getRightEyeOpenProbability();
                            }
                            if (face.getSmilingProbability() != null) {
                                smile = face.getSmilingProbability();
                            }

                            classValue = smile > .5f ? 1 : 2;
                            if ((leftEyeOpen < .25f && rightEyeOpen > .5f) ||
                                    (rightEyeOpen < .25f && leftEyeOpen > .5f)) {
                                classValue = 0;
                            }

                            final String className;
                            switch (classValue) {
                                case 0:
                                    className = getString(R.string.hunter);
                                    break;
                                case 1:
                                    className = getString(R.string.warlock);
                                    break;
                                case 2:
                                    className = getString(R.string.titan);
                                    break;
                                default:
                                    className = getString(R.string.face_not_found);
                                    break;
                            }
                            binding.resultTextview.setText(getString(R.string.result_text, className));
                        })
                .addOnFailureListener(
                        e -> {
                        });
    }
}
