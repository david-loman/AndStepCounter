package com.fromgeoto.linxiangpeng.stepcounter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author: linxiangpeng 16/4/30 :上午12:05
 * 404!!1
 */
public class StepCounter {

    // 安装
    public static boolean install (Context context){
        if (StepUtils.stepSensorIsAvailable(context)){
            registerRepeatService(context);
            return true;
        }
        return false;
    }

    // 卸载
    public static boolean unInstall (Context context){
        unRegisterRepeatService(context);
        return true;
    }

    // 获取当前步数
    public static void fetchStep (Context context, final ObtainStepListener listener){
        if (StepUtils.stepSensorIsAvailable(context)){
            final StepCounterManager manager = new StepCounterManager(context);
            StepUtils.obtainSteps(context, new UpdateStepListener() {
                @Override
                public void updateStep(long time, long step) {
                    manager.update(StepCounterManager.UPDATE_STEP_COUNT,time,step);
                    listener.obtain(manager.getStepNumber(),manager.getStepCount(),manager.getLastStep(),manager.getStepTime());
                }
            });
        }else{
            Toast.makeText(context,"当前设备不支持",Toast.LENGTH_LONG).show();
        }
    }

    // 注册每日更新服务
    private static void registerRepeatService(Context context){
        if (context == null){
            return;
        }
        StepCountModel model = new StepCountModel(context);
        if (model != null && model.getStepNumber() < 0){
            Intent intent = new Intent(context, UpdateStepNumberService.class);
            intent.putExtra(UpdateStepNumberService.STEP_TYPE,StepCounterManager.UPDATE_STEP_NUMBER);
            context.startService(intent);
        }
        Intent intent = new Intent(context, UpdateStepNumberService.class);
        intent.putExtra(UpdateStepNumberService.STEP_TYPE,StepCounterManager.UPDATE_STEP_NUMBER);
        PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(),0,intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE,59);
        calendar.add(Calendar.SECOND,59);
        calendar.add(Calendar.MILLISECOND,0);

        AlarmManager manager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    // 注销每日更新服务
    private static void unRegisterRepeatService(Context context){
        Intent intent = new Intent(context, UpdateStepNumberService.class);
        intent.putExtra(UpdateStepNumberService.STEP_TYPE,StepCounterManager.UPDATE_STEP_NUMBER);
        PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(),0,intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE,59);
        calendar.add(Calendar.SECOND,59);
        calendar.add(Calendar.MILLISECOND,0);

        AlarmManager manager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }
}
