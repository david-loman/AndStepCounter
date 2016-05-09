package com.fromgeoto.linxiangpeng.stepcounter;

import android.content.Context;

/**
 * @author: linxiangpeng 16/5/5 :下午11:59
 * 404!!1
 */
class StepCounterManager {

    public static final int UPDATE_STEP_COUNT = 1;  // 更新今日总步数
    public static final int UPDATE_STEP_NUMBER = 2; // 更新今日起始步数

    private static long sStepNumber = StepCountModel.VALUE_NOT_SET; // 今日起始步数
    private static long sStepCount = StepCountModel.VALUE_NOT_SET;  // 今日总步数
    private static long sStepTime = StepCountModel.VALUE_NOT_SET;   // 上次更新时间
    private static long sLastStep = StepCountModel.VALUE_NOT_SET;   // 上次步数

    private Context mContext;
    private StepCountModel mStepCountModel;

    public StepCounterManager (Context context){
        mContext = context;
        mStepCountModel = new StepCountModel(context);
        initStepData();
    }

    // 更新计步器数据
    void update(int type,long time,long step){
        switch (type){
            case (UPDATE_STEP_NUMBER):
                sStepNumber = step;
                sLastStep = step;
                sStepTime = time;
                sStepCount = 0;
                break;
            case (UPDATE_STEP_COUNT):
                if (sStepNumber == StepCountModel.VALUE_NOT_SET){
                    sStepNumber = step;
                    sLastStep = step;
                    sStepTime = time;
                    sStepCount = 0;
                }
                // 如果系统重启,系统会将 step 归零
                if (sStepNumber > step){
                    sLastStep = 0;
                }
                sStepCount += step - sLastStep;
                sLastStep = step;
                sStepTime = time;
                break;
            default:
                sLastStep = step;
                sStepTime = time;
        }
        mStepCountModel.updateStepNumber(sStepNumber);
        mStepCountModel.updateStepCounts(sStepCount);
        mStepCountModel.updateLastStep(sLastStep);
        mStepCountModel.updateStepTime(sStepTime);
    }

    long getStepNumber(){
        return sStepNumber;
    }

    long getStepCount(){
        return sStepCount;
    }

    long getStepTime() {
        return sStepTime;
    }

    long getLastStep() {
        return sLastStep;
    }

    // 初始化记步数据
    private void initStepData (){
        if (sStepNumber == StepCountModel.VALUE_NOT_SET){
            sStepNumber = mStepCountModel.getStepNumber();
        }
        if (sStepCount == StepCountModel.VALUE_NOT_SET){
            sStepCount = mStepCountModel.getStepCounts();
        }
        if (sStepTime == StepCountModel.VALUE_NOT_SET){
            sStepTime = mStepCountModel.getStepTime();
        }
        if (sLastStep == StepCountModel.VALUE_NOT_SET){
            sLastStep = mStepCountModel.getLastStep();
        }
    }
}
