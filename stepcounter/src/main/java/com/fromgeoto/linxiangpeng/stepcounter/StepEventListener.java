package com.fromgeoto.linxiangpeng.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * @author: linxiangpeng 16/5/7 :上午1:49
 * 404!!1
 */
class StepEventListener implements SensorEventListener{

    private UpdateStepListener mUpdateStepListener;

    StepEventListener(UpdateStepListener listener){
        mUpdateStepListener = listener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long steps = (long) (event.values[0] + 0.5f);
        mUpdateStepListener.updateStep(System.currentTimeMillis(),steps);
    }
}
