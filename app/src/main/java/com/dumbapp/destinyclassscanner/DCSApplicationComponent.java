package com.dumbapp.destinyclassscanner;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface DCSApplicationComponent {
    void inject(MainActivity activity);

    void inject(SplashFragment fragment);

    void inject(HomeFragment fragment);

    void inject(CameraFragment fragment);

    void inject(ResultFragment fragment);
}
