package com.zaycev.vmusic.background;

import android.content.Context;
import android.os.AsyncTask;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractTask<Params, Chunk, Result> extends AsyncTask<Params, Chunk, Result> {

    protected Context context;
    protected OnTaskFinished onTaskFinished;
    protected AtomicReference<Result> result;
    protected AtomicReference<Params> params;

    public AbstractTask(Context context, OnTaskFinished onTaskFinished) {
        this.context = context;
        this.onTaskFinished = onTaskFinished;
        result = new AtomicReference<>();
        params = new AtomicReference<>();
    }

    @Override
    protected Result doInBackground(Params... params) {

        Result r = null;

        try {
            r = inBackgroundMethod();
            result.set(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract Result inBackgroundMethod() throws Exception;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (onTaskFinished != null)
            onTaskFinished.onFinish(this);
    }

    @Override
    protected void onProgressUpdate(Chunk... values) {
        super.onProgressUpdate(values);
    }

    public Result getResult() {
        return result.get();
    }

    public Params getParams() {
        return params.get();
    }

    public void setParams(Params params) {
        this.params.set(params);
    }

    public interface OnTaskFinished<T> {
        public void onFinish(T task);
    }
}
