package me.kartikarora.urdashboard.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import me.kartikarora.potato.Potato;
import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.models.Computed;
import me.kartikarora.urdashboard.models.submissions.Completed;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.utils
 * Project : UR Dashboard
 * Date : 2/6/17
 */

public class HelperUtils {

    private static HelperUtils helperUtils;

    public static HelperUtils getInstance() {
        if (helperUtils == null) {
            helperUtils = new HelperUtils();
        }
        return helperUtils;
    }

    private HelperUtils() {
    }

    public void copyToClipboard(Context context, String message, String what) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(message, message);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, what + " copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    public String priceWithCommas(String price) {
        return price.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
    }

    public ArrayMap<String, String> getHeaders(Context context) {
        return getHeaders(Potato.potate(context).Preferences().getSharedPreferenceString(context.getString(R.string.pref_udacity_token)));
    }

    public ArrayMap<String, String> getHeaders(String key) {
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("Authorization", key);
        map.put("Accept", "application/json");
        return map;
    }

    public String capitalize(String original) {
        return (original == null || original.length() == 0 || original.isEmpty()) ? original :
                original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public Date utcToDefault(Date utcDate, Date localDate) {
        return new Date(utcDate.getTime() + TimeZone.getDefault().getOffset(localDate.getTime()));
    }

    public void changeStatusBarColor(Activity activity) {
        int color = R.color.primary_light;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = activity.getWindow().getDecorView();
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        } else {
            color = R.color.primary_dark;
        }
        changeStatusBarColor(activity, color);
    }

    public void changeStatusBarColor(Activity activity, int color) {
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity.getApplicationContext(), color));
    }

    public void clearLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = activity.getWindow().getDecorView();
            int flags = view.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public Computed computeHeavyStuffFromCompletedList(List<Completed> completedList) {
        Collections.sort(completedList);

        int totalCompleted = completedList.size();
        double avgEarned = 0.0;
        long avgTime = 0;
        for (Completed project : completedList) {
            avgEarned += Double.parseDouble(project.getPrice());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            try {
                Date createdDate = format.parse(project.getAssignedAt());
                Date completedDate = format.parse(project.getCompletedAt());
                long diff = completedDate.getTime() - createdDate.getTime();
                avgTime += diff;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String totalEarned = HelperUtils.getInstance().priceWithCommas(String.format("%.2f", avgEarned));
        if (totalCompleted > 0) {
            avgEarned /= totalCompleted;
            avgTime /= totalCompleted;
        }
        Calendar averageTime = Calendar.getInstance();
        averageTime.setTime(new Date(avgTime));
        averageTime.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new Computed(totalCompleted, averageTime, totalEarned, avgEarned);
    }

    public Calendar timeRemainingCalculator(String assignedAt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Date expiry = null;
        Calendar assignedCal = Calendar.getInstance();
        try {
            expiry = format.parse(assignedAt);
            assignedCal.setTime(expiry);
            assignedCal.add(Calendar.HOUR, 12);
            expiry = assignedCal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date rightNow = now.getTime();
        long diff = expiry.getTime() - rightNow.getTime();
        Calendar remaining = Calendar.getInstance();
        remaining.setTime(new Date(diff));
        return remaining;
    }
}
