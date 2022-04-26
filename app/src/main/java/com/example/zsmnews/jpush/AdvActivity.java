package com.example.zsmnews.jpush;

import android.app.Dialog;
import android.app.Notification;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adorkable.iosdialog.HourChoiceDialog;
import com.adorkable.iosdialog.WeekChoiceDialog;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import cn.jiguang.demo.R;
//import cn.jiguang.demo.baselibrary.BaseUtils;
//import cn.jiguang.demo.baselibrary.ScreenUtils;
//import cn.jiguang.demo.baselibrary.ToastHelper;
import com.example.zsmnews.R;
import com.example.zsmnews.baselibrary.BaseUtils;
import com.example.zsmnews.baselibrary.ScreenUtils;
import com.example.zsmnews.baselibrary.ToastHelper;



import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;

/**
 * Copyright(c) 2020 极光
 * Description
 */
public class AdvActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AdvActivity";

    public static final String TAG_DATA = "tag_data";
    public static final String ALIAS_DATA = "alias_data";
    public static final String MN_DATA = "mn_data";
    private static final String STYLES_DATA_ADD_ACTION = "styles_data_add_action";
    private static final String STYLES_DATA_CUSTOM = "styles_data_custom";
    private static final String STYLES_DATA_BASIC = "styles_data_basic";
    private static final String WEEK_DATA = "week_data";
    private static final String WEEK_DATA_FIRST_SET = "week_data_first_set";
    private static final String HOUR_START_DATA = "hour_start_data";
    private static final String HOUR_END_DATA = "hour_end_data";

    // tag
    private List<com.example.zsmnews.jpush.TagBean> tagList;
    private BaseQuickAdapter<com.example.zsmnews.jpush.TagBean, BaseViewHolder> adapter;
    private RecyclerView tagRecycleView;
    private View tagEditView, tvAddTag, tagGroup;
    private com.example.zsmnews.jpush.TagBean tagAddBean;
    private View tagEditCompleteView;

    //mobilenumber
    private String mn;
    private TextView mnEditView, mnEditViewEmpty;

    //alias
    private String alias;
    private TextView aliasEditView, aliasEditViewEmpty;

    // styles
    private TextView stylesAddActions, stylesAddActionsNumber, stylesCustom, stylesCustomNumber, stylesBasic, stylesBasicNumber;


    // date,time
    private TextView dateEdit,timeEditStart,timeEditEnd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jpush_demo_activity_push_adv);
        ScreenUtils.setStatusBarTransparentAndWordsGray(getWindow());
        iniView();
        initData();
    }

    private void iniView() {
        tagGroup = findViewById(R.id.group_tag_1);
        tagRecycleView = findViewById(R.id.tag_recycle_view);
        tagEditView = findViewById(R.id.tv_edit);
        tvAddTag = findViewById(R.id.tv_add_tag);
        tagEditCompleteView = findViewById(R.id.tv_complete);

        mnEditView = findViewById(R.id.tv_edit_mn);
        mnEditViewEmpty = findViewById(R.id.tv_edit_mn_empty);

        aliasEditView = findViewById(R.id.tv_edit_alias);
        aliasEditViewEmpty = findViewById(R.id.tv_edit_alias_empty);

        stylesAddActions = findViewById(R.id.tv_styles_activate_add_actions);
        stylesAddActionsNumber = findViewById(R.id.tv_styles_number_add_actions);
        stylesBasic = findViewById(R.id.tv_styles_activate_basic);
        stylesBasicNumber = findViewById(R.id.tv_styles_number_basic);
        stylesCustom = findViewById(R.id.tv_styles_activate_custom);
        stylesCustomNumber = findViewById(R.id.tv_styles_number_custom);

        dateEdit = findViewById(R.id.tv_push_accept_date_edit);
        timeEditStart = findViewById(R.id.tv_push_accpet_time_edit_start);
        timeEditEnd = findViewById(R.id.tv_push_accpet_time_edit_end);

        findViewById(R.id.iv_back).setOnClickListener(this);

        tagEditView.setOnClickListener(this);
        tagEditCompleteView.setOnClickListener(this);
        tvAddTag.setOnClickListener(this);
        tagList = new ArrayList<>();
        tagRecycleView.addItemDecoration(new GridSpacingItemDecoration(4, 20, true));
        tagRecycleView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new TagAdapter(tagList);
        tagRecycleView.setAdapter(adapter);

        aliasEditView.setOnClickListener(this);
        aliasEditViewEmpty.setOnClickListener(this);

        mnEditView.setOnClickListener(this);
        mnEditViewEmpty.setOnClickListener(this);

        stylesAddActions.setOnClickListener(this);
        stylesBasic.setOnClickListener(this);
        stylesCustom.setOnClickListener(this);

        dateEdit.setOnClickListener(this);
        timeEditStart.setOnClickListener(this);
        timeEditEnd.setOnClickListener(this);

    }

    private void showEditUi(boolean isEdit) {
        if (isEdit) {
            tagEditView.setVisibility(View.GONE);
            tagEditCompleteView.setVisibility(View.VISIBLE);
        } else {
            tagEditView.setVisibility(View.VISIBLE);
            tagEditCompleteView.setVisibility(View.GONE);
        }
    }

    private void initData() {
        getTagData();

        mn = MMKV.defaultMMKV().getString(MN_DATA, "");
        showMNUi(TextUtils.isEmpty(mn), mn);

        alias = MMKV.defaultMMKV().getString(ALIAS_DATA, "");
        showAliasUi(TextUtils.isEmpty(alias), alias);

        int numberAddAction = MMKV.defaultMMKV().getInt(STYLES_DATA_ADD_ACTION, -1);
        setStyleAddActions(numberAddAction);

        int numberBasic = MMKV.defaultMMKV().getInt(STYLES_DATA_BASIC, -1);
        setStyleBasic(numberBasic);

        int numberCustom = MMKV.defaultMMKV().getInt(STYLES_DATA_CUSTOM, -1);
        setStyleCustom(numberCustom);

        updatePushTime(true);

    }


    private void showTagUi(boolean tagEmpty) {
        if (tagEmpty) {
            tagRecycleView.setVisibility(View.GONE);
            tagEditView.setVisibility(View.GONE);
            tagGroup.setVisibility(View.VISIBLE);
        } else {
            tagRecycleView.setVisibility(View.VISIBLE);
            tagEditView.setVisibility(View.VISIBLE);
            tagGroup.setVisibility(View.GONE);
        }
    }

    private void showAliasUi(boolean aliasEmpty, String name) {
        if (aliasEmpty) {
            aliasEditView.setVisibility(View.GONE);
            aliasEditViewEmpty.setVisibility(View.VISIBLE);
        } else {
            aliasEditView.setVisibility(View.VISIBLE);
            aliasEditView.setText(name);
            aliasEditViewEmpty.setVisibility(View.GONE);
        }
    }

    private void showMNUi(boolean mnEmpty, String mobileNumber) {
        if (mnEmpty) {
            mnEditView.setVisibility(View.GONE);
            mnEditViewEmpty.setVisibility(View.VISIBLE);
        } else {
            mnEditView.setVisibility(View.VISIBLE);
            mnEditView.setText(mobileNumber);
            mnEditViewEmpty.setVisibility(View.GONE);
        }
    }

    private void getTagData() {
        tagAddBean = new com.example.zsmnews.jpush.TagBean();
        tagAddBean.setItemType(com.example.zsmnews.jpush.TagBean.ADD);
        String s = MMKV.defaultMMKV().getString(TAG_DATA, "");
        List<com.example.zsmnews.jpush.TagBean> tagBeanList = new Gson().fromJson(s, new TypeToken<ArrayList<com.example.zsmnews.jpush.TagBean>>() {
        }.getType());

        tagList.clear();
        if (tagBeanList == null || tagBeanList.isEmpty()) {
            showTagUi(true);
            return;
        }
        showTagUi(false);
        tagList.addAll(tagBeanList);

        tagList.add(tagList.size(), tagAddBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            onBackPressed();
        } else if (id == R.id.tv_add_tag) {
            dialogCreate(TYPE_TAG, "");
        } else if (id == R.id.tv_edit_alias_empty) {
            dialogCreate(TYPE_ALIAS, "");
        } else if (id == R.id.tv_edit_alias) {
            dialogCreate(TYPE_ALIAS, alias);
        } else if (id == R.id.tv_edit_mn_empty) {
            dialogCreate(TYPE_MN, "");
        } else if (id == R.id.tv_edit_mn) {
            dialogCreate(TYPE_MN, mn);
        } else if (id == R.id.tv_edit) {
            showEditUi(true);
            tagList.remove(tagAddBean);
            int size = tagList.size();

            for (int i = 0; i < size; i++) {
                tagList.get(i).setEdit(true);
            }
            adapter.notifyDataSetChanged();
        } else if (id == R.id.tv_complete) {
            showEditUi(false);
            if (tagList.isEmpty()) {
                showTagUi(true);
            } else {
                int size = tagList.size();
                for (int i = 0; i < size; i++) {
                    tagList.get(i).setEdit(false);
                }

                String json = new Gson().toJson(tagList);
                MMKV.defaultMMKV().putString(TAG_DATA, json);

                tagList.add(tagList.size(), tagAddBean);
                adapter.notifyDataSetChanged();
            }
        } else if (id == R.id.tv_styles_activate_add_actions) {
            setStyleAddActions(10);
            ToastHelper.showOther(getApplicationContext(), "AddActions Builder - " + 10);
        } else if (id == R.id.tv_styles_activate_custom) {
            setStyleCustom(2);
            ToastHelper.showOther(getApplicationContext(), "Custom Builder - " + 2);
        } else if (id == R.id.tv_styles_activate_basic) {
            setStyleBasic(1);
            ToastHelper.showOther(getApplicationContext(), "Basic Builder - " + 1);
        } else if (id == R.id.tv_push_accpet_time_edit_start) {
            int startTime = Integer.parseInt(MMKV.defaultMMKV().getString(HOUR_START_DATA, "0"));
            new HourChoiceDialog(AdvActivity.this)
                    .init("开始时间")
                    .initData(startTime)
                    .setOnYesCallBack(new HourChoiceDialog.YesCallBack() {
                        @Override
                        public void onYesCallBack(int hour) {
                            if(!checkTimeVaild(hour,-2)) {
                                return;
                            }
                            MMKV.defaultMMKV().putString(HOUR_START_DATA, hour+"");
                            updatePushTime(false);
                        }
                    })
                    .show();
        } else if(id == R.id.tv_push_accpet_time_edit_end){
            int endTime = Integer.parseInt(MMKV.defaultMMKV().getString(HOUR_END_DATA, "23"));
            new HourChoiceDialog(AdvActivity.this)
                    .init("结束时间")
                    .initData(endTime)
                    .setOnYesCallBack(new HourChoiceDialog.YesCallBack() {
                        @Override
                        public void onYesCallBack(int hour) {
                            if(!checkTimeVaild(-2,hour)) {
                                return;
                            }
                            MMKV.defaultMMKV().putString(HOUR_END_DATA, hour+"");
                            updatePushTime(false);
                        }
                    })
                    .show();
        } else if (id == R.id.tv_push_accept_date_edit) {
            String lastChoiceDays = MMKV.defaultMMKV().getString(WEEK_DATA, "");
            boolean notSetWeekYet = MMKV.defaultMMKV().getBoolean(WEEK_DATA_FIRST_SET, true);
            new WeekChoiceDialog(AdvActivity.this)
                    .init()
                    .initData(lastChoiceDays,notSetWeekYet)
                    .setOnYesCallBack(new WeekChoiceDialog.YesCallBack() {
                        @Override
                        public void onYesCallBack(String days, Set<Integer> daysSet) {
                            MMKV.defaultMMKV().putString(WEEK_DATA, days);
                            MMKV.defaultMMKV().putBoolean(WEEK_DATA_FIRST_SET, false);
                            updatePushTime(false);
                        }
                    })
                    .show();

        }
    }

    public static HashMap<String,String> weekMap = new HashMap();

    static {
        weekMap.put("1","一");
        weekMap.put("2","二");
        weekMap.put("3","三");
        weekMap.put("4","四");
        weekMap.put("5","五");
        weekMap.put("6","六");
        weekMap.put("0","日");
    }

    private boolean checkTimeVaild(int startTime,int endTime){
        if(startTime == -2){
            startTime = Integer.parseInt(MMKV.defaultMMKV().getString(HOUR_START_DATA, "-1"));
        }
        if(endTime == -2){
            endTime = Integer.parseInt(MMKV.defaultMMKV().getString(HOUR_END_DATA, "-1"));
        }
        if(startTime == -1){
            startTime = 0;
        }
        if(endTime == -1){
            endTime = 23;
        }
        if (startTime > endTime) {
            ToastHelper.showOther(getApplicationContext(), "开始时间不能大于结束时间");
            return false;
        }
        return true;
    }

    /**
     * 更新接收日期
     * @param onlyUI 仅更新UI
     */
    private void updatePushTime(boolean onlyUI){
        String choiceDays = MMKV.defaultMMKV().getString(WEEK_DATA, "");
        int startTime = Integer.parseInt(MMKV.defaultMMKV().getString(HOUR_START_DATA, "-1"));
        int endTime = Integer.parseInt(MMKV.defaultMMKV().getString(HOUR_END_DATA, "-1"));

        timeEditStart.setText(startTime == -1?"开始时间":startTime+" 点  ");
        timeEditEnd.setText(endTime == -1?"结束时间":endTime+" 点  ");

        if(startTime == -1){
            startTime = 0;
        }
        if(endTime == -1){
            endTime = 23;
        }

        boolean notSetWeekYet = MMKV.defaultMMKV().getBoolean(WEEK_DATA_FIRST_SET, true);
        if(notSetWeekYet){
            choiceDays = "0,1,2,3,4,5,6";// 没有设置过星期数据，则默认全推送
        }

        StringBuffer weekString = new StringBuffer("请选择");
        Set<Integer> daysSet = new HashSet<>();
        if(!TextUtils.isEmpty(choiceDays)){
            String[] sArray = choiceDays.split(",");
            if(sArray.length!=0){
                weekString = new StringBuffer("每周");
            }
            for(int i = 0;i<sArray.length;i++){
                daysSet.add(Integer.parseInt(sArray[i]));
                weekString.append(weekMap.get(sArray[i]));
                if(i<sArray.length-1){
                    weekString.append("、");
                }
            }
        }

        if(!notSetWeekYet){
            dateEdit.setText(weekString.toString());
        }



        if(!onlyUI){
            Log.d(TAG,"setPushTime days:"+choiceDays+" startHour:"+startTime+" endHour:"+endTime);
            JPushInterface.setPushTime(AdvActivity.this,daysSet,startTime,endTime);
        }

    }

    /**
     * 设置通知提示方式 - 多 Action
     */
    private void setStyleAddActions(int number) {
        if (number == -1) {
            return;
        }
        MultiActionsNotificationBuilder builder = new MultiActionsNotificationBuilder(AdvActivity.this);
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(number, builder);

        MMKV.defaultMMKV().putInt(STYLES_DATA_ADD_ACTION, number);

        stylesAddActionsNumber.setVisibility(View.VISIBLE);
        stylesAddActionsNumber.setText("样式编号：" + number);
        stylesAddActions.setVisibility(View.GONE);
    }

    /**
     * 设置通知提示方式 - 基础属性
     */
    private void setStyleBasic(int number) {
        if (number == -1) {
            return;
        }
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(AdvActivity.this);
        builder.statusBarDrawable = R.drawable.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(number, builder);

        MMKV.defaultMMKV().putInt(STYLES_DATA_BASIC, number);

        stylesBasicNumber.setVisibility(View.VISIBLE);
        stylesBasicNumber.setText("样式编号：" + number);
        stylesBasic.setVisibility(View.GONE);
    }


    /**
     * 设置通知栏样式 - 定义通知栏Layout
     */
    private void setStyleCustom(int number) {
        if (number == -1) {
            return;
        }
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(AdvActivity.this, R.layout.jpush_demo_customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.drawable.ic_launcher;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(number, builder);
        MMKV.defaultMMKV().putInt(STYLES_DATA_CUSTOM, number);

        stylesCustomNumber.setVisibility(View.VISIBLE);
        stylesCustomNumber.setText("样式编号：" + number);
        stylesCustom.setVisibility(View.GONE);
    }


    private static final int TYPE_TAG = 1;
    private static final int TYPE_MN = 2;
    private static final int TYPE_ALIAS = 3;
    private static int sequence = 1;

    private void dialogCreate(final int type, String content) {
        if (type == TYPE_TAG && tagList.size() > 10) {
            ToastHelper.showOther(getApplicationContext(), "最多可添加10个标签");
            return;
        }

        String title = "";
        String desc = "";
        String placeHolder = "";

        if (type == TYPE_TAG) {
            title = "新建标签";
            desc = "标签为大小写字母、数字、下划线、中文，单个标签字符长度不超过40";
            placeHolder = "请输入标签内容";
        } else if (type == TYPE_ALIAS) {
            title = "编辑设备别名";
            desc = "设备别名为大小写字母、数字、下划线、中文，单个设备别名字符长度不超过40";
            placeHolder = "请输入标签内容";
        } else if (type == TYPE_MN) {
            title = "添加手机号码";
            placeHolder = "请输入手机号码";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dv = View.inflate(this, R.layout.jpush_demo_dialog_push_add, null);
        TextView tvOk = (TextView) dv.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) dv.findViewById(R.id.tv_cancel);
        final View containerInput = dv.findViewById(R.id.input_container);

        TextView titleTv = dv.findViewById(R.id.tv_title);
        titleTv.setText(title);

        final TextView tvDesc = dv.findViewById(R.id.tv_desc);
        if (TextUtils.isEmpty(desc)) {
            tvDesc.setHint("");
            tvDesc.setVisibility(View.GONE);
        } else {
            tvDesc.setHint(desc);
        }

        final EditText et = dv.findViewById(R.id.et_input);
        et.setHint(placeHolder);
        if (!TextUtils.isEmpty(content)) {
            et.setText(content);
        }

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    containerInput.setSelected(false);
                    tvDesc.setText("");
                }
            }
        });
        final Dialog dialog = builder.setView(dv).create();
        dialog.show();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = et.getText().toString().trim();
                if (type == TYPE_TAG && TextUtils.isEmpty(input)) {
                    containerInput.setSelected(true);
                    return;
                }

                if (type == TYPE_MN && !ExampleUtil.isValidMobileNumber(input)) {
                    containerInput.setSelected(true);
                    tvDesc.setVisibility(View.VISIBLE);
                    tvDesc.setText("请填写正确的手机号");
                    return;
                } else if (!BaseUtils.matches(input)) {
                    containerInput.setSelected(true);
                    tvDesc.setVisibility(View.VISIBLE);
                    tvDesc.setText("标签仅限大小写字母、数字、下划线、中文");
                    return;
                }


                if (type == TYPE_TAG && !TextUtils.isEmpty(input)) {

                    int size = tagList.size() - 1;
                    com.example.zsmnews.jpush.TagBean bean;
                    for (int i = 0; i < size; i++) {
                        bean = tagList.get(i);
                        if (input.equals(bean.getText())) {
                            containerInput.setSelected(true);
                            et.setText("");
                            tvDesc.setText("该标签已存在，请重新输入");
                            return;
                        }
                    }
                    showTagUi(false);
                    setTagData(input);
                } else if (type == TYPE_ALIAS) {
                    alias = input;
                    showAliasUi(TextUtils.isEmpty(alias), alias);
                    MMKV.defaultMMKV().putString(ALIAS_DATA, input);
                    if (TextUtils.isEmpty(alias)) {
                        JPushInterface.deleteAlias(com.example.zsmnews.jpush.AdvActivity.this, sequence++);
                    } else {
                        JPushInterface.setAlias(com.example.zsmnews.jpush.AdvActivity.this, sequence++, alias);
                    }
                } else if (type == TYPE_MN) {
                    mn = input;
                    showMNUi(TextUtils.isEmpty(mn), mn);
                    MMKV.defaultMMKV().putString(MN_DATA, input);
                    JPushInterface.setMobileNumber(com.example.zsmnews.jpush.AdvActivity.this, sequence++, TextUtils.isEmpty(mn)?null:mn);
                }
                dialog.dismiss();
            }
        });
    }

    private void setTagData(String tagName) {
        Set<String> tags = new HashSet<>();
        tags.add(tagName);
        JPushInterface.addTags(this, 0, tags);
        com.example.zsmnews.jpush.TagBean bean = new com.example.zsmnews.jpush.TagBean();
        bean.setText(tagName);
        tagList.add(0, bean);

        tagList.remove(tagAddBean);
        String json = new Gson().toJson(tagList);

        tagList.add(tagList.size(), tagAddBean);
        MMKV.defaultMMKV().putString(TAG_DATA, json);

        adapter.notifyDataSetChanged();
    }


    private class TagAdapter extends BaseMultiItemQuickAdapter<com.example.zsmnews.jpush.TagBean, BaseViewHolder> {


        TagAdapter(List<com.example.zsmnews.jpush.TagBean> data) {
            super(data);
            addItemType(com.example.zsmnews.jpush.TagBean.TEXT, R.layout.jpush_demo_layout_text);
            addItemType(com.example.zsmnews.jpush.TagBean.ADD, R.layout.jpush_demo_layout_add);
        }

        @Override
        protected void convert(@NotNull final BaseViewHolder baseViewHolder, com.example.zsmnews.jpush.TagBean tagBean) {
            int itemType = tagBean.getItemType();
            if (itemType == com.example.zsmnews.jpush.TagBean.TEXT) {
                baseViewHolder.setText(R.id.tv_text, tagBean.getText());
                View deleteIv = baseViewHolder.getView(R.id.iv_x);
                deleteIv.setVisibility(tagBean.isEdit() ? View.VISIBLE : View.INVISIBLE);
                deleteIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = baseViewHolder.getAdapterPosition();
                        Set<String> tags = new HashSet<>();
                        tags.add(tagList.get(position).getText());
                        JPushInterface.deleteTags(com.example.zsmnews.jpush.AdvActivity.this, 0, tags);
                        tagList.remove(position);
                        if (tagList.isEmpty()) {
                            showEditUi(false);
                            showTagUi(true);
                            MMKV.defaultMMKV().putString(TAG_DATA, "");
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            } else {
                baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogCreate(TYPE_TAG, "");
                    }
                });
            }
        }
    }
}
