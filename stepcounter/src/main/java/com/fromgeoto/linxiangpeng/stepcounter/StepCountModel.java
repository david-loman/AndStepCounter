package com.fromgeoto.linxiangpeng.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: linxiangpeng 16/4/16 :下午9:46
 * 404!!1
 */
class StepCountModel {

    public static final int SENSOR_NOT_FOUND = -3;
    public static final int SYSTEM_ERROR = -2;
    public static final int VALUE_NOT_SET = -1;
    public static final int STEP_COUNT_NOT_START = 0;

    static final String SP_STEP = "sp_step";
    static final String SP_STEP_NUMBER = "sp_step_number";
    static final String SP_STEP_COUNTS = "sp_step_counts";
    static final String SP_LAST_STEP = "sp_last_step";
    static final String SP_STEP_TIME = "sp_step_time";

    private Context mContext;
    private SharedPreferences mSp;

    StepCountModel(Context context) {
        mContext = context;
        if (mContext != null) {
            mSp = mContext.getSharedPreferences(SP_STEP, Context.MODE_PRIVATE);
        }
    }

    boolean updateLastStep(long steps) {
        return updateStepItem(SP_LAST_STEP, steps);
    }

    boolean updateStepCounts(long steps) {
        return updateStepItem(SP_STEP_COUNTS, steps);
    }

    boolean updateStepNumber(long steps) {
        return updateStepItem(SP_STEP_NUMBER, steps);
    }

    boolean updateStepTime(long time) {
        return updateStepItem(SP_STEP_TIME, time);
    }

    long getStepCounts() {
        return getStepItem(SP_STEP_COUNTS);
    }

    long getStepNumber() {
        return getStepItem(SP_STEP_NUMBER);
    }

    long getStepTime() {
        return getStepItem(SP_STEP_TIME);
    }

    long getLastStep() {
        return getStepItem(SP_LAST_STEP);
    }

    private long getStepItem(String name) {
        if (mSp == null) {
            return SYSTEM_ERROR;
        }
        return mSp.getLong(name, VALUE_NOT_SET);
    }

    private boolean updateStepItem(String name, long value) {
        boolean isSuccess = false;
        if (mSp != null) {
            mSp.edit().putLong(name, value).commit();
            isSuccess = true;
        }
        return isSuccess;
    }
}
