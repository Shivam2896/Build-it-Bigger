package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by DELL on 04-Oct-16.
 */
public class JokeTest extends ApplicationTestCase<Application> implements JokeListener {

    private CountDownLatch signal;
    private String joke;

    public JokeTest(Class<Application> applicationClass) {
        super(applicationClass);
    }

    public void testJoke(){
        try{
            signal = new CountDownLatch(1);
            new EndpointsAsyncTask(this).execute();
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull("joke is null", joke);
            assertFalse("joke is empty", joke.isEmpty());
        }catch(Exception ex){
            fail();
        }
    }

    @Override
    public void onReceived(String joke) {
        this.joke = joke;
        signal.countDown();
    }
}
