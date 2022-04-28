package com.example.assignment1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OrientationService extends Service {
    public OrientationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}