package kr.co.landvibe.handicraft.furniture.add;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.landvibe.handicraft.R;
import kr.co.landvibe.handicraft.data.domain.Furniture;
import kr.co.landvibe.handicraft.furniture.preview.FurniturePreviewActivity;
import kr.co.landvibe.handicraft.type.PeriodOfUseType;
import kr.co.landvibe.handicraft.type.StateType;
import kr.co.landvibe.handicraft.type.TradeType;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FurnitureAddActivity extends AppCompatActivity
        implements FurnitureAddContract.View {

    @BindView(R.id.toolbar_furniture_add)
    Toolbar mToolbar;

    @BindView(R.id.et_title)
    EditText mTitleEt;
    @BindView(R.id.et_price)
    EditText mPriceEt;
    @BindView(R.id.tv_state)
    TextView mStateTv;
    @BindView(R.id.tv_trade)
    TextView mTradeTv;
    @BindView(R.id.et_brand)
    EditText mBrandEt;
    @BindView(R.id.tv_period)
    TextView mPeriodOfUseTv;
    @BindView(R.id.et_width_size)
    EditText mWidthEt;
    @BindView(R.id.et_length_size)
    EditText mLengthEt;
    @BindView(R.id.et_height_size)
    EditText mHeightEt;
    @BindView(R.id.tv_desc)
    TextView mDescTv;
    @BindArray(R.array.state_rank)
    String[] mStateList;
    @BindArray(R.array.trade_type)
    String[] mTradeTypeList;
    private String mPriceText = "";
    private EditText descEtInDialog;
    private InputMethodManager imm;
    private StateType currentStateType = StateType.EMPTY;
    private TradeType currentTradeType = TradeType.EMPTY;
    private PeriodOfUseType currentPeriodOfUse = PeriodOfUseType.EMPTY;

    private AlertDialog mStateChoiceDialog;
    private AlertDialog mTradeChoiceDialog;
    private AlertDialog mPeriodOfUseChoiceDialog;
    private AlertDialog mDescDialog;

    private FurnitureAddContract.Presenter mFurnitureAddPresenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_add);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        // Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPriceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && !s.toString().equals(mPriceText)) {
                    DecimalFormat df = new DecimalFormat("###,###");
                    mPriceText = df.format(
                            Long.parseLong(s.toString().replaceAll(",", ""))
                    );
                    mPriceEt.setText(mPriceText);
                    mPriceEt.setSelection(mPriceText.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                s.append(" 원");
            }
        });

        mStateChoiceDialog = new AlertDialog.Builder(this)
                .setTitle("상품 상태")
                .setItems(StateType.names(), (dialog, which) -> {
                    currentStateType = StateType.get(which);
                    mStateTv.setText(currentStateType.getText());
                    mStateChoiceDialog.dismiss();
                }).create();

        mTradeChoiceDialog = new AlertDialog.Builder(this)
                .setTitle("거래 형태")
                .setItems(TradeType.names(), ((dialog, which) -> {
                    currentTradeType = TradeType.get(which);
                    mTradeTv.setText(currentTradeType.getText());
                    mTradeChoiceDialog.dismiss();
                })).create();

        mPeriodOfUseChoiceDialog = new AlertDialog.Builder(this)
                .setTitle("사용 기간")
                .setItems(PeriodOfUseType.names(), ((dialog, which) -> {
                    currentPeriodOfUse = PeriodOfUseType.get(which);
                    mPeriodOfUseTv.setText(currentPeriodOfUse.getText());
                    mPeriodOfUseChoiceDialog.dismiss();
                })).create();


        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogLayout = inflater.inflate(R.layout.desc_dialog, (ViewGroup) findViewById(R.id.dialog_layout));
        descEtInDialog = (EditText) dialogLayout.findViewById(R.id.dialog_desc_et);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mDescDialog = new AlertDialog.Builder(this)
                .setTitle("상세 설명")
                .setView(dialogLayout)
                .setPositiveButton("수정", (DialogInterface dialog, int which) -> {
                    String desc = descEtInDialog.getText().toString().trim();
                    if (desc.length() == 0) {
                        mDescTv.setText("");
                        mDescTv.setVisibility(View.GONE);
                    } else {
                        mDescTv.setVisibility(View.VISIBLE);
                        mDescTv.setText(desc);
                    }

                    imm.hideSoftInputFromWindow(descEtInDialog.getWindowToken(), 0);
                    dialog.dismiss();
                })
                .setNegativeButton("취소", (DialogInterface dialog, int which) -> {
                    imm.hideSoftInputFromWindow(descEtInDialog.getWindowToken(), 0);
                    dialog.dismiss();
                })
                .create();

        mFurnitureAddPresenter = new FurnitureAddPresenter();
        mFurnitureAddPresenter.attachView(this);
    }

    public boolean validFurniture() {
        // TODO valid value
        return true;
    }

    public Furniture formFurniture() {
        Furniture furniture = new Furniture();
        furniture.setTitle(mTitleEt.getText().toString());
        furniture.setPrice(Long.parseLong(mPriceEt.getText().toString().replace(",", "")));
        furniture.setGrade(mTradeTv.getText().toString());
        furniture.setState(mStateTv.getText().toString());
        furniture.setBrand(mBrandEt.getText().toString());
        furniture.setPeriodOfUse(mPeriodOfUseTv.getText().toString());
        furniture.setWidth(Integer.parseInt(mWidthEt.getText().toString()));
        furniture.setLength(Integer.parseInt(mLengthEt.getText().toString()));
        furniture.setHeight(Integer.parseInt(mHeightEt.getText().toString()));
        return furniture;
    }

    @OnClick(R.id.state_container)
    public void createStateChoiceDialog(View v) {
        mStateChoiceDialog.show();
    }

    @OnClick(R.id.trade_container)
    public void createTradeChoiceDialog(View v) {
        mTradeChoiceDialog.show();
    }

    @OnClick(R.id.period_container)
    public void createPeriodChoiceDialog(View v) {
        mPeriodOfUseChoiceDialog.show();
    }

    @OnClick(R.id.desc_container)
    public void createDescDialog(View v) {
        mDescDialog.show();
    }

    @OnClick(R.id.location_container)
    public void showLocation(View v) {

    }

    @OnClick(R.id.btn_preview)
    public void preview(View v) {
        if (validFurniture()) {
            showPreviewActivity(formFurniture());
        } else {
            // TODO AlertDialog
        }
    }

    @OnClick(R.id.btn_register)
    public void register(View v) {
        if (validFurniture()) {
            // TODO 등록
        } else {
            // TODO AlertDialog
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFurnitureAddPresenter.detachView();
    }

    /**
     * FurnitureAddContract.View
     */
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void backToMainActivity() {
        finish();
    }

    @Override
    public void showPreviewActivity(Furniture furniture) {
        final Intent intent = new Intent(this, FurniturePreviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Furniture.KEY, furniture);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
