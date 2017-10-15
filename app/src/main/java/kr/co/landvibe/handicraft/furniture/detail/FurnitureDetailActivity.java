package kr.co.landvibe.handicraft.furniture.detail;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.landvibe.handicraft.R;
import kr.co.landvibe.handicraft.data.domain.Furniture;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FurnitureDetailActivity extends AppCompatActivity
        implements FurnitureDetailContract.View, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.toolbar_furniture_detail)
    Toolbar mToolbar;

    @BindView(R.id.slider_furniture_list)
    SliderLayout mFurnitureImageSlider;

    @BindView(R.id.slider_indicator)
    PagerIndicator mPagerIndicator;

    @BindView(R.id.tv_furniture_title)
    TextView mTitleTv;
    @BindView(R.id.tv_furniture_price)
    TextView mPriceTv;
    @BindView(R.id.tv_furniture_state)
    TextView mStateTv;
    @BindView(R.id.tv_furniture_grade)
    TextView mGradeTv;
    @BindView(R.id.tv_furniture_time)
    TextView mTimeTv;
    @BindView(R.id.tv_furniture_brand)
    TextView mBrandTv;
    @BindView(R.id.tv_furniture_type)
    TextView mTypeTv;
    @BindView(R.id.tv_furniture_period)
    TextView mPeriodTv;
    @BindView(R.id.tv_furniture_size)
    TextView mSizeTv;
    @BindView(R.id.tv_furniture_desc)
    TextView mDescTv;
    @BindView(R.id.tv_furniture_location)
    TextView mLocationTv;

    private FurnitureDetailContract.Presenter mFurnitureDetailPresenter;

    private View view;
    private Activity activity;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_detail);
        ButterKnife.bind(this);
        view = getWindow().getDecorView().getRootView();
        activity = this;
        init();
    }

    private void init() {
        // Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long furnitureId = intent.getLongExtra("id", 0);

        mFurnitureDetailPresenter = new FurnitureDetailPresenter();
        mFurnitureDetailPresenter.attachView(this, this);
        mFurnitureDetailPresenter.loadFurniture(furnitureId);

        bindSliderDate(new HashMap<>()); // TODO 네트워크 연동되면 삭제

        mFurnitureImageSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mFurnitureImageSlider.setCustomIndicator(mPagerIndicator);
        mFurnitureImageSlider.addOnPageChangeListener(this);
        setSliderHeight(mFurnitureImageSlider);

    }

    private void setSliderHeight(SliderLayout slider) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels * 6 / 10;
        slider.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
    }

    private void bindSliderDate(HashMap<String, Integer> fileMap) {
        // mockup data
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.f1);
        file_maps.put("Big Bang Theory", R.drawable.f3);
        file_maps.put("House of Cards", R.drawable.f5);
        file_maps.put("Game of Thrones", R.drawable.f8);

        // Image Slider
        for (String name : file_maps.keySet()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            defaultSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
            mFurnitureImageSlider.addSlider(defaultSliderView);
        }
    }

    @OnClick(R.id.buy_container)
    public void contactToSeller(View v) {
        new BottomSheet.Builder(this)
                .setSheet(R.menu.contact_list)
                .setTitle("판매자에게 연락하기")
                .setListener(new BottomSheetListener() {

                    @Override
                    public void onSheetShown(@NonNull BottomSheet bottomSheet) {

                    }

                    @Override
                    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.kakao:
                                String kakaoId = "halfcraft-kakao";
                                ClipboardManager clipboardManager = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                    ClipData clipData = ClipData.newPlainText("label", kakaoId);
                                    clipboardManager.setPrimaryClip(clipData);
                                    new BottomSheet.Builder(view.getContext())
                                            .setTitle("카카오톡 안내")
                                            .setMessage("클립보드에 판매자의 카카오톡 아이디(" + kakaoId + ")를 저장했습니다.\n카카오톡으로 이동하여 친구추가를 하세요!")
                                            .setPositiveButton("닫기")
                                            .setIcon(getDrawable(R.drawable.kakaolink_btn_small))
                                            .show();
                                } else {
                                    Snackbar.make(view, "마쉬멜로우 버전 이상부터 사용할 수 있습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                                break;
                            case R.id.mms:
                                String phone = "010-3692-5036";
                                Uri uri= Uri.parse("smsto:"+phone.replace("-",""));
                                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                                intent.putExtra("sms_body", "반쪽이 공방 어플 보고 연락드렸습니다.");
                                startActivity(intent);
                                break;
                        }
                    }

                    @Override
                    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {

                    }
                })
                .show();
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation
        mFurnitureImageSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFurnitureDetailPresenter.detachView();
        view = null;
        activity = null;
    }

    @OnClick(R.id.tv_furniture_location)
    public void showLocation(View view){
        String addrQuery = mLocationTv.getText().toString();
        Uri addressUriByBuilder = new Uri.Builder()
                .scheme("geo")
                .path("0,0")
                .appendQueryParameter("q",addrQuery)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(addressUriByBuilder);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    /**
     * BaseSliderView.OnSliderClickListener
     *
     * @param slider
     */
    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    /**
     * ViewPagerEx.OnPageChangeListener
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * FurnitureDetailContract.View
     */
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showContactDialog() {

    }

    @Override
    public void bindData(Furniture furniture) {
        runOnUiThread(() -> {
            bindSliderDate(new HashMap<>());
        });
    }
}
