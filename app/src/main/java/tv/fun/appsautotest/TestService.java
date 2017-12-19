package tv.fun.appsautotest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service {
    public void onCreate(){
        super.onCreate();
        Log.i("xuzx", "aaaaaaaaa");
        Toast.makeText(getApplicationContext(), "onCreate service", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("xuzx", "bbbbbbbb");
        Toast.makeText(getApplicationContext(), "onBind", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onDestroy(){
        Log.i("xuzx", "onDestroy");
        Toast.makeText(this, "My Service Stoped", Toast.LENGTH_LONG).show();
    }

//    @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                    Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_LONG).show();
//                    do {
//                        System.out.println("Service is running......");
//                        try {
//                            sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } while (true);
//                }
//            }.start();
//            return super.onStartCommand(intent, flags, startId);
//        }
}