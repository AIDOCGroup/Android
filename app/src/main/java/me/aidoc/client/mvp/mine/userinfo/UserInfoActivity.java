package me.aidoc.client.mvp.mine.userinfo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;

import org.json.JSONArray;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.aidoc.client.R;
import me.aidoc.client.api.helper.HttpUtils;
import me.aidoc.client.entity.BaseResp;
import me.aidoc.client.entity.bean.JsonBean;
import me.aidoc.client.entity.bean.JsonFileReader;
import me.aidoc.client.entity.resp.AreaResp;
import me.aidoc.client.entity.resp.CurrentResp;
import me.aidoc.client.entity.resp.UploadFileResp;
import me.aidoc.client.mvp.common.CommonModel;
import me.aidoc.client.mvp.mine.UserInfoEditActivity;
import me.aidoc.client.util.ImageUtil;
import me.aidoc.client.util.L;
import me.aidoc.client.util.MyConstants;
import me.aidoc.client.util.ToastUtil;
import me.aidoc.client.util.UserUtil;

public class UserInfoActivity extends TakePhotoActivity implements View.OnClickListener {

    @BindView(R.id.rlResetAvatar)
    RelativeLayout rlResetAvatar;
    @BindView(R.id.rlResetNickName)
    RelativeLayout rlResetNickName;
    @BindView(R.id.rl_reset_sign)
    RelativeLayout rlResetSign;
    @BindView(R.id.rl_reset_sex)
    RelativeLayout rlResetSex;
    @BindView(R.id.rl_reset_birthday)
    RelativeLayout rlResetBirthday;
    @BindView(R.id.rl_reset_collection)
    RelativeLayout rlResetCollection;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvSign)
    TextView tvSign;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.tvBirthday)
    TextView tvBirthday;
    @BindView(R.id.tvCollection)
    TextView tvCollection;
    @BindView(R.id.tvNickName)
    TextView tvNickName;
    private PopupWindow popupWindow = null;
    private View popup_view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        textTitle.setText(R.string.text_persion_info);
        init();
        refreshUserInfo();
    }

    private void init() {
        initJsonData();

        CurrentResp currentUser = UserUtil.getCurrentUser();
        if (currentUser != null) {
            ImageUtil.showCircle(ivAvatar, currentUser.getAvatar());
            tvNickName.setText(currentUser.getNickname());
        }
    }


    @OnClick({R.id.rlResetAvatar, R.id.rlResetNickName, R.id.rl_reset_sign, R.id.rl_reset_sex, R.id.rl_reset_birthday, R.id.rl_reset_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlResetAvatar:
                showPopWindow();
                break;
            case R.id.rlResetNickName:
                startActivity(new Intent(this, UserInfoEditActivity.class).putExtra(MyConstants.EXTRA_TITLE, getString(R.string.modify_nickname))
                        .putExtra(MyConstants.EXTRA_ID, 0)
                );
                break;
            case R.id.rl_reset_sign:
                startActivity(new Intent(this, UserInfoEditActivity.class).putExtra(MyConstants.EXTRA_TITLE, getString(R.string.modify_sign))
                        .putExtra(MyConstants.EXTRA_ID, 1)
                );
                break;
            case R.id.rl_reset_sex:
                showSelSexPopWindow();
                break;
            case R.id.rl_reset_birthday:
                initTimePicker();
                break;
            case R.id.rl_reset_collection:
//                showPickerView();
                getCountyArea();
//                selectCollectionArea();
                break;
        }
    }

    private void selectCollectionArea() {
//        BottomDialog mBottomDialog = new BottomDialog(this, mSelectAddressEntity);
    }

    /**
     * 打开头像选择popwindow
     */
    private void showPopWindow() {
        popup_view = getLayoutInflater().inflate(R.layout.popup_layout_photo, null);
        popupWindow = new PopupWindow(popup_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);// 点击其他区域popupwindow消失，上面一句必不可少
        TextView take_picture = popup_view
                .findViewById(R.id.take_picture);
        TextView select_picture_frommobile = (TextView) popup_view
                .findViewById(R.id.select_picture_frommobile);
        TextView popup_cancel = popup_view
                .findViewById(R.id.popup_cancle);
        take_picture.setOnClickListener(v -> openCamera());
        select_picture_frommobile.setOnClickListener(v -> openGallery());
        popup_cancel.setOnClickListener(v -> popupWindow.dismiss());
        popupWindow.showAtLocation(popup_view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 打开性别选择popwindow
     */
    private void showSelSexPopWindow() {
        View popup_view = getLayoutInflater().inflate(R.layout.popup_layout_sex, null);
        popupWindow = new PopupWindow(popup_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);// 点击其他区域popupwindow消失，上面一句必不可少
        TextView take_picture = popup_view
                .findViewById(R.id.take_picture);
        TextView select_picture_frommobile = (TextView) popup_view
                .findViewById(R.id.select_picture_frommobile);
        TextView popup_cancel = popup_view
                .findViewById(R.id.popup_cancle);
        take_picture.setOnClickListener(v -> setSex(1));
        select_picture_frommobile.setOnClickListener(v -> setSex(2));
        popup_cancel.setOnClickListener(v -> popupWindow.dismiss());
        popupWindow.showAtLocation(popup_view, Gravity.BOTTOM, 0, 0);
    }

    private void setSex(int sex) {
        popupWindow.dismiss();
        CommonModel.setSex(sex, new HttpUtils.OnResultListener<BaseResp>() {
            @Override
            public void onSuccess(BaseResp baseResp) {
                ToastUtil.toast(UserInfoActivity.this, getString(R.string.setting_sex_success));
                refreshUserInfo();
            }

            @Override
            public void onError(Throwable error, String msg) {

            }
        });



    }

    /**
     * 照相
     */
    private void openCamera() {
        if(popupWindow!=null && popupWindow.isShowing())
            popupWindow.dismiss();
        CompressConfig compressConfig=new CompressConfig.Builder().setMaxSize(150*1024).setMaxPixel(1000).create();
        TakePhoto takePhoto=getTakePhoto();
        takePhoto.onEnableCompress(compressConfig,false);
//        configTakePhotoOption(takePhoto);
        takePhoto.onPickFromCapture(buildCamraImagePath());
    }

    /**
     * 图库
     */
    private void openGallery() {
        if(popupWindow!=null && popupWindow.isShowing())
            popupWindow.dismiss();
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        TakePhoto takePhoto = getTakePhoto();
        takePhoto.onEnableCompress(compressConfig, false);
        takePhoto.onPickFromGallery();
    }

    /**
     * 拍照保存路径
     *
     * @return
     */
    private Uri buildCamraImagePath() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return (Uri.fromFile(file));
    }

    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        takePhoto.setTakePhotoOptions(builder.create());

    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        uploadPhoto(new File(result.getImage().getCompressPath()));
    }


    /**
     * 上传图片
     *
     * @param file
     */
    private void uploadPhoto(File file) {
        CommonModel.uploadPhoto(file, new HttpUtils.OnResultListener<UploadFileResp>() {
            @Override
            public void onSuccess(UploadFileResp uploadFileResp) {
                if (uploadFileResp.getErr_code() == 0) {
//                    ToastUtil.toast(UserInfoActivity.this, "上传成功");
                    L.e("上传图片返回url==" + uploadFileResp.getUrl());
                    setAvatarUrl(uploadFileResp.getUrl());
                }
            }

            @Override
            public void onError(Throwable error, String msg) {
                ToastUtil.toast(UserInfoActivity.this, getString(R.string.uploadErro) + msg == null ? "" : msg);
            }
        });
    }

    /**
     * 设置头像
     *
     * @param imageUrl
     */
    public void setAvatarUrl(String imageUrl) {
        CommonModel.setAvatar(imageUrl, new HttpUtils.OnResultListener<BaseResp>() {
            @Override
            public void onSuccess(BaseResp baseResp) {
                ToastUtil.toast(UserInfoActivity.this, getString(R.string.set_avatar_success));
                refreshUserInfo();
            }

            @Override
            public void onError(Throwable error, String msg) {
                ToastUtil.toast(UserInfoActivity.this, getString(R.string.uploadAvatar_erro) + msg);
            }
        });
    }

    /**
     * 设置城市ID
     */
    public void setCityId(int cityId) {
        CommonModel.setCityId(cityId, new HttpUtils.OnResultListener<BaseResp>() {
            @Override
            public void onSuccess(BaseResp baseResp) {
                ToastUtil.toast(UserInfoActivity.this, getString(R.string.save_address_success));
                refreshUserInfo();
            }

            @Override
            public void onError(Throwable error, String msg) {
                ToastUtil.toast(UserInfoActivity.this, getString(R.string.save_address_erro) + msg);
            }
        });
    }

    /**
     * 刷新当前用户信息
     */
    public void refreshUserInfo() {
        CommonModel.getCurrentUserInfo(new HttpUtils.OnResultListener<CurrentResp>() {
            @Override
            public void onSuccess(CurrentResp baseResp) {
                ImageUtil.showCircle(ivAvatar, baseResp.getAvatar());
                tvNickName.setText("" + baseResp.getNickname() == null ? "" : baseResp.getNickname());
                tvSign.setText("" + baseResp.getSignature() == null ? "" : baseResp.getSignature());
                tvCollection.setText("" + baseResp.getCity_name() == null ? "" : baseResp.getCity_name());
                tvBirthday.setText("" + baseResp.getBirthday() == null ? "" : baseResp.getBirthday());
                if (baseResp.getSex() != null) {
                    switch (baseResp.getSex()) {//0:未知，1：男，2：女
                        case "0":
//                            tvSex.setText(R.string.unknown);
                            break;
                        case "1":
                            tvSex.setText(R.string.male);
                            break;
                        case "2":
                            tvSex.setText(R.string.female);
                            break;
                    }
                }
                EventBus.getDefault().post(baseResp, MyConstants.TAG_REFRESH_CURRENT);
            }

            @Override
            public void onError(Throwable error, String msg) {
//                ToastUtil.toast(UserInfoActivity.this, getString(R.string.avatar_erro) + msg);
            }
        });
    }

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<JsonBean>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<JsonBean>>> options3Items = new ArrayList<>();

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                ArrayList<JsonBean> options2List = options3Items.get(options1).get(options2);
                int selectCityId;
                if (options2List.size() > 0) {
                    String text = options1Items.get(options1).getName() + "-" +
                            options2Items.get(options1).get(options2).getName() + "-" +
                            options3Items.get(options1).get(options2).get(options3).getName();
                    tvCollection.setText(text);
                    selectCityId = options3Items.get(options1).get(options2).get(options3).getId();
                } else {
                    String text = options1Items.get(options1).getName() + "-" +
                            options2Items.get(options1).get(options2).getName();
                    tvCollection.setText(text);
                    selectCityId = options2Items.get(options1).get(options2).getId();
                }

                //  提交地区修改
                setCityId(selectCityId);

            }
        }).setTitleText("")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY)
                .setContentTextSize(16)
                .setLineSpacingMultiplier(2.0f)
                .setOutSideCancelable(true)
                .build();
          /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(this, "province_json.json");
        ArrayList<JsonBean> shengJson = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = shengJson;

        for (int i = 0; i < shengJson.size(); i++) {//遍历省份
            ArrayList<JsonBean> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<JsonBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < shengJson.get(i).getChildren().size(); c++) {//遍历该省份的所有城市
                cityList.add(shengJson.get(i).getChildren().get(c));
                ArrayList<JsonBean> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                for (int d = 0; shengJson.get(i).getChildren().get(c).getChildren() != null && d < shengJson.get(i).getChildren().get(c).getChildren().size(); d++) {//该城市对应地区所有数据
                    City_AreaList.add(shengJson.get(i).getChildren().get(c).getChildren().get(d));//添加该城市所有地区数据
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            //添加城市数据
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 23);
        Calendar endDate = Calendar.getInstance();
//        endDate.set(2018, 1, 1);
        //时间选择器、、、
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                tvBirthday.setText(getTime(date));
                CommonModel.setBirthday(getTime(date), new HttpUtils.OnResultListener<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp baseResp) {
                        ToastUtil.toast(UserInfoActivity.this, getString(R.string.save_birthday_success));
                    }

                    @Override
                    public void onError(Throwable error, String msg) {

                    }
                });
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) /、/设置外部遮罩颜色
                .setDecorView(null)
                .build();
        pvTime.show();
    }

    private void getCountyArea() {
        if(countryList!=null) countryList.clear();
        CommonModel.getArea("0", "1", "1000", new HttpUtils.OnResultListener<AreaResp>() {
            @Override
            public void onSuccess(AreaResp areaResp) {
                L.e(areaResp.toString());
                if (areaResp != null) {
                    countryList = areaResp.getList();
                    for (AreaResp.Country country : areaResp.getList()) {
                        countryNameList.add(country.getName());
                    }
                    showCountryPickerView();
                }
            }

            @Override
            public void onError(Throwable error, String msg) {
                L.e("" + msg);
            }
        });
    }

    private List<AreaResp.Country> countryList = new ArrayList<>();
    private List<String> countryNameList = new ArrayList<>();

    /**
     * 显示国家
     */
    private void showCountryPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                ToastUtil.toast(UserInfoActivity.this, "" + countryList.get(options1).getName());
                setCityId(countryList.get(options1).getId());
            }
        })
                .setContentTextSize(16)
                .setLineSpacingMultiplier(2.0f)
                .build();
        pvOptions.setPicker(countryNameList);
        pvOptions.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUserInfo();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_backward:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
