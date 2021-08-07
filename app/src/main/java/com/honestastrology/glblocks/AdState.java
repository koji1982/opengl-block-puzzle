package com.honestastrology.glblocks;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import static android.content.ContentValues.TAG;

class AdState {
    
    private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-2263172161263292/7094260160";
    private static final String INTERSTITIAL_TEST_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String MOVIE_TEST_ID        = "ca-app-pub-3940256099942544/8691691433";
    private static final String BANNER_TEST_ID       = "ca-app-pub-2263172161263292/2334486566";
    
    private static AdView         _bannerAd;
    private static InterstitialAd _interstitialAd;
    
    static void initialize(Activity activity){
        MobileAds.initialize(
                        activity,
                        (initializationStatus) -> {});
    }
    
    static boolean isValid(){
        return true;
    }
    
    static String getInterstitialUnitId(){
        return INTERSTITIAL_UNIT_ID;
    }
    
    static String getInterstitialTestId(){
        return INTERSTITIAL_TEST_ID;
    }
    
    static String getMovieTestId(){
        return MOVIE_TEST_ID;
    }
    
    static String getBannerTestId(){
        return BANNER_TEST_ID;
    }
    
    static void showBannerAd(Activity activity){
//        _adView = activity.findViewById(R.id.gl_ad);
        AdRequest adRequest = new AdRequest.Builder().build();
//		_adView.setAdSize( AdSize.BANNER );
        _bannerAd.loadAd(adRequest);
    }
    
    static void makeBannerAd(Activity activity, ViewGroup adLayout){
        _bannerAd = new AdView(activity);
        _bannerAd.setAdUnitId( BANNER_TEST_ID );
        _bannerAd.setAdSize( AdSize.BANNER );
        //LayoutParams側のgravityは他のViewに対しての位置関係(xmlのlayout_gravity)
        //を表し、LinearLayout側のsetGravity()はその内側のchildViewに掛かる
        //重力(xmlのgravity)を表す
        LinearLayout.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        _bannerAd.setLayoutParams( layoutParams );
        _bannerAd.loadAd( new AdRequest.Builder().build() );
        adLayout.addView(_bannerAd);
    }
    
    static boolean isInterstitialPrepared(){
        return ( _interstitialAd != null );
    }
    
    static void showInterstitialAd(Activity activity){
        _interstitialAd.show( activity );
    }
    
    static void prepareInterstitialAd(Activity activity){
        new Handler().post( () -> {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(
                    activity,
//					_interstitialTestId,
                    AdState.getMovieTestId(),
//					_interstitialUnitId,
                    adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            _interstitialAd = interstitialAd;
                            setAdScreenCallback( activity );
                            Log.i(TAG, "onAdLoaded");
                        }
                        
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.i(TAG, loadAdError.getMessage());
                            _interstitialAd = null;
                            // Gets the domain from which the error came.
                            String errorDomain = loadAdError.getDomain();
                            // Gets the error code. See
                            // https://developers.google.com/android/reference/com/google/android/gms/ads/AdRequest#constant-summary
                            // for a list of possible codes.
                            int errorCode = loadAdError.getCode();
                            // Gets an error message.
                            // For example "Account not approved yet". See
                            // https://support.google.com/admob/answer/9905175 for explanations of
                            // common errors.
                            String errorMessage = loadAdError.getMessage();
                            // Gets additional response information about the request. See
                            // https://developers.google.com/admob/android/response-info for more
                            // information.
                            ResponseInfo responseInfo = loadAdError.getResponseInfo();
                            // Gets the cause of the error, if available.
                            AdError cause = loadAdError.getCause();
                            // All of this information is available via the error's toString() method.
                            Log.d("Ads", loadAdError.toString());
                        }
                    });
        });
    }
    
    private static void setAdScreenCallback(Activity activity){
        _interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.");
                prepareInterstitialAd( activity );
            }
            
            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.");
            }
            
            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                _interstitialAd = null;
                Log.d("TAG", "The ad was shown.");
            }
        });
    }
}
