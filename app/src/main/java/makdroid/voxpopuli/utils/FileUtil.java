package makdroid.voxpopuli.utils;

import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Grzecho on 22.11.2015.
 */
public class FileUtil {

    private static final String[] MIME_TYPES = {"image/png", "image/jpeg", "audio/mpeg", "text/plain", "application/pdf"};

    public static File getDiskCacheDir(Context context) {
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath);
    }


    public static boolean isExternalStorageRemovable() {
        return Environment.isExternalStorageRemovable();
    }


    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static boolean isMimeTypeValid(String url) {
        boolean returnValue = false;
        String type = getMimeType(url);

        if (type != null) {
            for (String m : getListMimeTypes()) {
                if (m.equalsIgnoreCase(type)) {
                    returnValue = true;
                    break;
                }
            }
        }
        return returnValue;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = getExtension(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String getExtension(String url) {
        return MimeTypeMap.getFileExtensionFromUrl(url);
    }

    private static ArrayList<String> getListMimeTypes() {
        return new ArrayList<String>(Arrays.asList(MIME_TYPES));
    }

}
