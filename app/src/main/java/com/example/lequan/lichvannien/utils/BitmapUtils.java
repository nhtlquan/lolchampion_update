package com.example.lequan.lichvannien.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import com.example.lequan.lichvannien.common.Prefs;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BitmapUtils {
    private static final String IMAGE_DIRECTORY_NAME = "benson";

    public static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (mediaStorageDir.exists() || mediaStorageDir.mkdirs()) {
            return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + new SimpleDateFormat(DateUtils.DATE_FORMAT1, Locale.getDefault()).format(new Date()) + ".jpg");
        }
        Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create benson directory");
        return null;
    }

    public static String convertBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 80, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
    }

    public static Bitmap processToteImage(String image_path, Bitmap bm) {
        try {
            int orientation = new ExifInterface(image_path).getAttributeInt("Orientation", 1);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90.0f);
            } else if (orientation == 3) {
                matrix.postRotate(180.0f);
            } else if (orientation == 8) {
                matrix.postRotate(270.0f);
            }
            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = maxSize;
        return Bitmap.createScaledBitmap(image, width, (int) (((float) width) / (((float) image.getWidth()) / ((float) image.getHeight()))), true);
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        if (width > height) {
            return Math.round(((float) height) / ((float) reqHeight));
        }
        return Math.round(((float) width) / ((float) reqWidth));
    }

    private static Target picassoImageTarget(final Context context) {
        return new Target() {
            public void onBitmapLoaded(final Bitmap bitmap, LoadedFrom from) {
                new Thread(new Runnable() {
                    public void run() {
                        IOException e;
                        Throwable th;
                        File dir = new File(context.getFilesDir(), Prefs.KEY_AVATAR);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File myImageFile = new File(dir + File.separator + "facebook_avatar");
                        FileOutputStream fos = null;
                        try {
                            FileOutputStream fos2 = new FileOutputStream(myImageFile);
                            try {
                                bitmap.compress(CompressFormat.PNG, 100, fos2);
                                try {
                                    fos2.close();
                                    Prefs.setValue(context, Prefs.KEY_AVATAR, myImageFile.getAbsolutePath());
                                    fos = fos2;
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                    fos = fos2;
                                }
                            } catch (Throwable th3) {
                                fos = fos2;
                                fos.close();
                                Prefs.setValue(context, Prefs.KEY_AVATAR, myImageFile.getAbsolutePath());
                                throw th3;
                            }
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            try {
                                fos.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            Prefs.setValue(context, Prefs.KEY_AVATAR, myImageFile.getAbsolutePath());
                        }
                    }
                }).start();
            }

            public void onBitmapFailed(Drawable errorDrawable) {
            }

            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable == null) {
                }
            }
        };
    }

    public static void saveFBAvatarImage(Context context, String imageUrl) {
        Picasso.with(context).load(imageUrl).into(picassoImageTarget(context));
    }
}
