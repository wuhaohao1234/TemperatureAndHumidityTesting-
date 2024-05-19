package com.example.temperatureandhumiditytesting.sleep;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author guoyr05@163.com
 * @date 2021/4/25 15:59
 */

public class EventManager {
    public static final String TAG = "EventManager";
    private static EventManager mEventManager = new EventManager();
    private EventDao mEventDao = EventDao.getInstance();
    //保存一份数据

    public List<Integer> getDeletedIds() {
        return deletedIds;
    }

    public void setDeletedIds(List<Integer> deletedIds) {
        this.deletedIds = deletedIds;
    }

    private List<Event> events = new ArrayList<>();
    private List<Integer> deletedIds = new ArrayList<>();

    private EventManager(){
    }

    public static EventManager getInstance() {
        return mEventManager;
    }

    public List<Event> findAll() {
        events =  mEventDao.findAll();
        return events;
    }

    public void flushData() {
        events = mEventDao.findAll();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void removeEvents(final Handler handler, final List<Integer> ids) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int result = mEventDao.remove(ids);
                    Message message = new Message();
                    message.what = Constants.HANDLER_SUCCESS;
                    message.obj = result;
                    message.setTarget(handler);
                    message.sendToTarget();
                } catch (Exception e) {
                    Log.e(TAG, "run: ", e);
                    handler.obtainMessage(Constants.HANDLER_FAILED, new TextException(e)).sendToTarget();
                }
            }
        }).start();
    }

    public boolean removeEvent(Integer id) {
        return mEventDao.remove(Collections.singletonList(id)) != 0;
    }

    public boolean saveOrUpdate(Event event) {
        try {
            if (event.getmId() != null) {
                mEventDao.update(event);
            } else {
                mEventDao.create(event);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "saveOrUpdate: ", e);
            return false;
        }
    }

    public int getLatestEventId() {
        return mEventDao.getLatestEventId();
    }

    public Event getOne(Integer id) {
        return mEventDao.findById(id);
    }

}
