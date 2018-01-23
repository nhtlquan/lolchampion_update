package com.example.lequan.lichvannien.network;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.utils.NSLog;
import java.lang.ref.WeakReference;

public abstract class SmartAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private static final String TAG = SmartAsyncTask.class.getSimpleName();
    private WeakReference<BaseActivity> ref;

    public SmartAsyncTask(BaseActivity owner) {
        NSLog.m1451d(TAG, "Created new SmartAsyncTask: " + getHashCode());
        if (owner != null) {
            NSLog.m1451d(TAG, "Owner activity: " + owner.hashCode());
            this.ref = new WeakReference(owner);
        }
    }

    protected void onPostExecute(final Result result) {
        super.onPostExecute(result);
        NSLog.m1451d(TAG, "onPostExecute: " + getHashCode());
        if (this.ref.get() == null || isCancelled() || ((BaseActivity) this.ref.get()).isStopped()) {
            NSLog.m1451d(TAG, "onPostExecute: Stopped. Exit now.");
            return;
        }
        final Handler handler = new Handler();
        new Thread() {

            class C13051 implements Runnable {
                C13051() {
                }

                public void run() {
                    BaseActivity owner = (BaseActivity) SmartAsyncTask.this.ref.get();
                    if (owner == null || owner.isStopped() || SmartAsyncTask.this.isCancelled()) {
                        NSLog.m1451d(SmartAsyncTask.TAG, "Screen is stopped. Exit now.");
                        return;
                    }
                    NSLog.m1451d(SmartAsyncTask.TAG, "Owner is visible now. Deliver postExecute. Owner hashcode: " + owner.hashCode());
                    try {
                        SmartAsyncTask.this.postExecute(result);
                    } catch (Exception e) {
                        NSLog.m1452e(SmartAsyncTask.TAG, "Error posting execute!");
                        e.printStackTrace();
                    }
                }
            }

            public void run() {
                BaseActivity owner = (BaseActivity) SmartAsyncTask.this.ref.get();
                if (owner != null && !SmartAsyncTask.this.isCancelled()) {
                    synchronized (owner) {
                        while (owner != null) {
                            if (owner.isPaused()) {
                                try {
                                    NSLog.m1451d(SmartAsyncTask.TAG, "Owner is pause, wait. Owner hashCode: " + owner.hashCode());
                                    owner.wait();
                                } catch (InterruptedException e) {
                                    NSLog.m1452e(SmartAsyncTask.TAG, "InterruptedException: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    NSLog.m1451d(SmartAsyncTask.TAG, "hanlder post: " + SmartAsyncTask.this.getHashCode());
                    handler.post(new C13051());
                }
            }
        }.start();
    }

    protected void postExecute(Result result) {
        NSLog.m1451d(TAG, "SmartAsyncTask, postExecute: " + getHashCode());
    }

    @SuppressLint({"NewApi"})
    public void executeTask(Params... params) {
        if (VERSION.SDK_INT >= 11) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            execute(params);
        }
    }

    private int getHashCode() {
        return hashCode();
    }
}
