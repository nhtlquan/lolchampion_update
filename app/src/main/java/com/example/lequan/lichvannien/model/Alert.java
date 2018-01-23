package com.example.lequan.lichvannien.model;

import android.media.RingtoneManager;
import android.net.Uri;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.utils.DateUtils;
import java.io.Serializable;
import java.util.Date;

public class Alert implements Serializable {
    private boolean[] arrTimeDistance = new boolean[]{false, false, false, false, true};
    private long endTime;
    private int mFrequency = 0;
    private int mId;
    private String mLabel = "Add Label";
    private boolean mMute;
    private boolean mOn;
    private String mToneTitle;
    private String mToneURI;
    private boolean mVibrate;
    private boolean mWake;
    private int repeatFrequent = 0;
    private long startTime;

    public void setToneDisplay(String toneTitle) {
        this.mToneTitle = toneTitle;
    }

    public String getToneDisplay() {
        if (this.mToneURI != null) {
            return this.mToneTitle;
        }
        return "Default";
    }

    public Uri getToneURI() {
        if (this.mToneURI != null) {
            return Uri.parse(this.mToneURI);
        }
        return RingtoneManager.getDefaultUri(2);
    }

    public String getFrequencyDisplay() {
        int hours = this.mFrequency / 3600;
        int minutes = (this.mFrequency / 60) % 60;
        int seconds = this.mFrequency % 60;
        if (hours > 0) {
            return hours + "h " + minutes + "' " + seconds + "\"";
        }
        if (minutes > 0) {
            return minutes + "' " + seconds + "\"";
        }
        if (seconds > 0) {
            return seconds + "\"";
        }
        return "Set Frequency";
    }

    public String getLongFormFrequencyDisplay() {
        int hours = this.mFrequency / 3600;
        int minutes = (this.mFrequency / 60) % 60;
        int seconds = this.mFrequency % 60;
        if (hours > 0) {
            String str;
            StringBuilder append = new StringBuilder().append(hours);
            if (hours == 1) {
                str = " hour ";
            } else {
                str = " hours ";
            }
            append = append.append(str).append(minutes);
            if (minutes == 1) {
                str = " minute ";
            } else {
                str = " minutes ";
            }
            append = append.append(str).append(seconds);
            if (seconds == 1) {
                str = " second";
            } else {
                str = " seconds";
            }
            return append.append(str).toString();
        } else if (minutes > 0) {
            return minutes + (minutes == 1 ? " minute " : " minutes ") + seconds + (seconds == 1 ? " second" : " seconds");
        } else if (seconds <= 0) {
            return "Set Frequency";
        } else {
            return seconds + (seconds == 1 ? " second" : " seconds");
        }
    }

    public String getLabel() {
        if (this.mLabel == null || this.mLabel.equals("")) {
            return "Set Label";
        }
        return this.mLabel;
    }

    public void setLabel(String label) {
        if (label.length() > 20) {
            label = label.substring(0, 20);
        }
        this.mLabel = label;
    }

    public int getFrequency() {
        return this.mFrequency;
    }

    public void setFrequency(int frequency) {
        this.mFrequency = frequency;
    }

    public boolean isVibrate() {
        return this.mVibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.mVibrate = vibrate;
    }

    public void setToneURI(Uri toneURI) {
        if (toneURI != null) {
            this.mToneURI = toneURI.toString();
        } else {
            this.mToneURI = null;
        }
    }

    public boolean isOn() {
        return this.mOn;
    }

    public void setOn(boolean on) {
        this.mOn = on;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public boolean isWake() {
        return this.mWake;
    }

    public void setWake(boolean wake) {
        this.mWake = wake;
    }

    public boolean isMute() {
        return this.mMute;
    }

    public void setMute(boolean mute) {
        this.mMute = mute;
    }

    public long getStartTimeMillis() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTimeMillis() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return DateUtils.convertDateToString(new Date(this.startTime), DateUtils.DATE_FORMAT4);
    }

    public String getEndDate() {
        return DateUtils.convertDateToString(new Date(this.endTime), DateUtils.DATE_FORMAT4);
    }

    public String getStartTime() {
        return DateUtils.convertDateToString(new Date(this.startTime), DateUtils.TIME_FORMAT1);
    }

    public String getEndTime() {
        return DateUtils.convertDateToString(new Date(this.endTime), DateUtils.TIME_FORMAT1);
    }

    public int getRepeatFrequent() {
        return this.repeatFrequent;
    }

    public void setRepeatFrequent(int repeatFrequent) {
        this.repeatFrequent = repeatFrequent;
    }

    public boolean[] getArrTimeDistance() {
        return this.arrTimeDistance;
    }

    public void resetArrTimeDistance() {
        this.arrTimeDistance = new boolean[]{false, false, false, false, false};
    }

    public boolean isEnableTimeDistance(int index) {
        return this.arrTimeDistance[index];
    }

    public void setDistanceIndexEnable(int index) {
        this.arrTimeDistance[index] = true;
    }

    public int countEnalbeTimeDistance() {
        int count = 0;
        for (boolean z : this.arrTimeDistance) {
            if (z) {
                count++;
            }
        }
        return count;
    }

    public String toString() {
        return new Gson().toJson((Object) this);
    }
}
