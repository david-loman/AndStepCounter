package com.fromgeoto.linxiangpeng.stepcounter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

/**
 * @author: linxiangpeng 16/5/7 :上午1:30
 * 404!!1
 */
class StepUtils {

    private static boolean hasStepSensor = false;

    // 检查是否支持记步传感器
    static boolean stepSensorIsAvailable(Context context){
        // 检查是否有记步传感器
        if (hasStepSensor == false){
            try {
                SensorManager sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
                Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                if (stepSensor != null){
                    hasStepSensor = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return hasStepSensor;
    }

    // 异步获取步数
    static void obtainSteps(Context context,UpdateStepListener listener){
        if (stepSensorIsAvailable(context)) {
            SensorManager sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
            Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManager.registerListener(new StepEventListener(listener), stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }
}
