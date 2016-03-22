package com.example.objectedge.rxjavatest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.app.RxActivity;
import rx.android.lifecycle.LifecycleObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * It's important to extends RxActivity to wraps observables into view lifecycle.
 */
public class MainActivity extends RxActivity implements Observer<List<MyEntity>>{

    private Observable<List<MyEntity>>  observable;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.message);

        // Retrofit 2.0 implementation (but also works in retrofit 1.x)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())

                // This guy is very important
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        PostResource resource = retrofit.create(PostResource.class);

        // setup observable to work async and return sync
        observable = resource
                .findAll()
                // work on async thread
                .subscribeOn(Schedulers.io())
                // returns to main thread
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    protected void onStart() {
        super.onStart();

        LifecycleObservable
                // wraps this observable into activity lifecycle
                .bindActivityLifecycle(lifecycle(), observable)

                // start the request
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
        Log.d("pablo","------------FINISH---------------");
        textView.setText("It's done. Look the log to see the results");
    }

    @Override
    public void onError(Throwable e) {
        Log.e("pablo","error", e);
        textView.setText("Finished with errors");
    }

    @Override
    public void onNext(List<MyEntity> postEntities) {
        // it happens on main thread because of "subscribeOn(AndroidSchedulers.mainThread())"

        for(MyEntity post: postEntities){
            Log.d("pablo",post.toString());
        }
    }
}
