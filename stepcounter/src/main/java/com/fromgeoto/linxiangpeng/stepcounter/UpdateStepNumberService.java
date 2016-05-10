package com.fromgeoto.linxiangpeng.stepcounter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class UpdateStepNumberService extends Service {

    public static final String STEP_TYPE = "step_type";
    private int type;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null){
            type = StepCounterManager.UPDATE_STEP_NUMBER;
        }else {
            type = intent.getIntExtra(STEP_TYPE, StepCounterManager.UPDATE_STEP_COUNT);
        }
        final StepCounterManager manager = new StepCounterManager(UpdateStepNumberService.this);
        if (StepUtils.stepSensorIsAvailable(UpdateStepNumberService.this) && manager != null){
            StepUtils.obtainSteps(UpdateStepNumberService.this, new UpdateStepListener() {
                @Override
                public void updateStep(long time, long step) {
                    Toast.makeText(UpdateStepNumberService.this, "更新步数类型 : "+step, Toast.LENGTH_SHORT).show();
                    manager.update(type,time,step);
                }
            });
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
