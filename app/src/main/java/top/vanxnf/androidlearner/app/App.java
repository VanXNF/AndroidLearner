package top.vanxnf.androidlearner.app;

import android.app.Application;

import me.yokeyword.fragmentation.Fragmentation;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true)
                .handleException((Exception e) -> {})
                .install();
    }
}
