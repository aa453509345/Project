package com.example.limingyi.retrofitdemo;

import android.app.DownloadManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.limingyi.retrofitdemo.http.RequestFactroy;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.plugins.RxJavaObservableExecutionHook;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public String Base_URL="http://ip.taobao.com/service/";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tv=(TextView)findViewById(R.id.tv);
        testRxJava();
    }

    private void testRxJava() {

        /*Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>(


        ) {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world1");
                subscriber.onNext("hello world2");
                subscriber.onNext("hello world3");

            }
        });


        Subscriber<String> stringSubscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        };*/

       // observable.subscribe(stringSubscriber);


  /* Observable.just("rxjava").subscribe(new Action1<String>() {
       @Override
       public void call(String s) {
           Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
       }
   });
  */

  /*      *//**
         *
         * map操作符  在订阅之前做改变
         *
         *//*

        Observable.just("rxjava").map(new Func1<String, String>() {


            @Override
            public String call(String s) {

                String re="i can fix"+s;

                return re;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });*/


    /*    final List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        Observable <List<String>> obserable=Observable.create(new Observable.OnSubscribe<List<String>>() {


            @Override
            public void call(Subscriber<? super List<String>> subscriber) {

                subscriber.onNext(list);

            }
        });

         obserable.flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                return Observable.from(strings);
            }
        }).subscribe(new Action1<String>() {
             @Override
             public void call(String s) {
                 Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
             }
         });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public void sendRequest(View v) throws IOException {

        //RequestFactroy.getInstance().list("aaaa");

        OkHttpClient client=new OkHttpClient();
        File file=new File(Environment.getDataDirectory(),"temp");
        if(!file.exists()){
            file.mkdirs();
        }
        Cache cache=new Cache(file,1024*1024*10);
        client.setCache(cache);
        client.setConnectTimeout(30, TimeUnit.SECONDS);
       client.networkInterceptors().add(new Interceptor() {
           @Override
           public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
               FormEncodingBuilder builder=new FormEncodingBuilder();

               Request request=chain.request();
               //Request newRequest= request.newBuilder().url(request.url()+"&aaa=123").build();
           /*    Log.e("http", "url=========" + request.url() + "\n" + "header" + request.headers());
               final com.squareup.okhttp.Response response = chain.proceed(request);*/

               Log.e("http", request.url() + "====" + "\n" + "header" + request.headers());

               com.squareup.okhttp.Response response=chain.proceed(request);

               Log.e("http", request.url()+ "====" + "\n" + "header" + response.headers());

               return response;
           }
       });

        Retrofit retrofit=new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).
                client(client).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        final GitHub gitHub=retrofit.create(GitHub.class);
        Map paramMap=new HashMap();
        paramMap.put("ip","202.202.33.33");
        gitHub.showContribution(paramMap).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Contribution>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Contribution contribution) {

                tv.setText(contribution.toString());
                onCompleted();
            }
        });



    /*    gitHub.showContribution("202.202.33.33").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Contribution>() {
            @Override
            public void call(Contribution contribution) {

                tv.setText(contribution.toString());
            }
        });*/

/*
        <Contribution> call= gitHub.showContribution("202.202.33.33");
        call.enqueue(new Callback<Contribution>() {
            @Override
            public void onResponse(Response<Contribution> response, Retrofit retrofit) {
                Contribution re=response.body();
                tv.setText(re.toString());
            }

            @Override
            public void onFailure(Throwable t) {

                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
*/


       /* Call<Contribution> call=gitHub.showContribution("202.202.33.33");
       call.enqueue(new Callback<Contribution>() {
           @Override
           public void onResponse(Response<Contribution> response, Retrofit retrofit) {
               if(response!=null){
                   Log.e("re","scc");
               }else{
                   Log.e("re","fa");
               }
              String result=response.body().toString();

               Log.e("re", response.body()+""+response.code());

               Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();
             // Log.e("re",result);
           }

           @Override
           public void onFailure(Throwable t) {
               Log.e("re", t.getMessage());
           }
       });*/
/*        try {

            List<Contribution> contributors = call.execute().body();
            Log.e("re",contributors.size()+"");
for (Contribution c:contributors){
    Log.e("re",c.login+"======="+c.contributions);
}

        }catch (Exception e){

        Log.e("re","error");
        }*/

    }


    public interface GitHub{
        @GET("getIpInfo.php")
        Observable<Contribution> showContribution(@QueryMap Map<String,String> op);
    }


    public class Contribution{
        public  String code;
        public  Data data;


       class Data{

           String country;
           String country_id;
           String area;
           String area_id;
           String region;
           String region_id;
           String city;
           String city_id;
           String county;
           String county_id;
           String isp;
           String isp_id;
           String ip;

           @Override
           public String toString() {
               return "Data{" +
                       "country='" + country + '\'' +
                       ", country_id='" + country_id + '\'' +
                       ", area='" + area + '\'' +
                       ", area_id='" + area_id + '\'' +
                       ", region='" + region + '\'' +
                       ", region_id='" + region_id + '\'' +
                       ", city='" + city + '\'' +
                       ", city_id='" + city_id + '\'' +
                       ", county='" + county + '\'' +
                       ", county_id='" + county_id + '\'' +
                       ", isp='" + isp + '\'' +
                       ", isp_id='" + isp_id + '\'' +
                       ", ip='" + ip + '\'' +
                       '}';
           }
       }

        @Override
        public String toString() {
            return "Contribution{" +
                    "code='" + code + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}
