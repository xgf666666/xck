package com.xx.baseutilslibrary.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.DeviceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 图片选择工具
 * 作者: LeiXiaoXing on 2016/9/30 11:26
 */


public class ImageChooseHelper {
    /**
     * 相机获取图片
     */
    private final int REQUEST_CODE_PHOTO = 1;
    /**
     * 图库获取图片
     */
    private final int REQUEST_CODE_ALBUM = 2;
    /**
     * 图片裁剪
     */
    private final int REQUEST_CODE_CROP = 3;
    /**
     * 头像文件
     */
    private File file;
    /**
     * 是否开启裁剪
     * 默认开启
     */
    private boolean isCrop = true;
    /**
     * 使用弱引用保存Activity实例
     */
    private WeakReference<Activity> activityWeakReference;
    private WeakReference<FragmentActivity> fragmentActivityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;
    private OnFinishChooseImageListener listener;
    private OnFinishChooseAndCropImageListener mOnFinishChooseAndCropImageListener;
    private int width, height;
    private int compressQuality = 100;//文件压缩比率
    private String dirPath;//文件存储路径
    private String authority;//FileProvider路径配置
    private Uri mTempUri;//临时图片Uri

    private ImageChooseHelper(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    private ImageChooseHelper(Fragment fragment) {
        fragmentWeakReference = new WeakReference<>(fragment);
        fragmentActivityWeakReference = new WeakReference<FragmentActivity>((FragmentActivity) fragment.getActivity());
    }

    /**
     * 获取真实图片路径
     *
     * @param uri
     * @return
     */
    public static String getRealPathFromURI(Context context, Uri uri) {
//        String res = null;
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
//        if (cursor == null) {
//            return "";
//        }
//        if (cursor.moveToFirst()) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            res = cursor.getString(column_index);
//        }
//        cursor.close();
//        return res;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {

//                LogUtils.logInfoStar("isExternalStorageDocument");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
//                LogUtils.logInfoStar("isDownloadsDocument");
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
//                LogUtils.logInfoStar("isMediaDocument");
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public Context getContext() {
        return getActivity();
    }

    /**
     * Android N下获取文件Uri的正确姿势
     *
     * @param file 文件
     * @return
     */
    private Uri getUri(File file) {
        if (file == null)
            throw new NullPointerException("文件不存在");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            if (TextUtils.isEmpty(authority)) {
                throw new NullPointerException("Provider路径未配置");
            }
            return FileProvider.getUriForFile(getActivity(),
                    authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 获取弱引用中的Activity实例
     *
     * @return activity
     */
    private Activity getActivity() {
        if (fragmentActivityWeakReference != null) {
            return fragmentActivityWeakReference.get();
        }
        return activityWeakReference.get();
    }

    /**
     * 初始化文件名
     */
    private File initFile(String fileName) {
        File dir;
        if (TextUtils.isEmpty(dirPath)) {
            //如果没有配置,默认使用外置存储
            dir = Environment.getExternalStorageDirectory();
        } else {
            dir = new File(dirPath);
            if (!dir.exists())
                dir.mkdirs();
        }
        file = new File(dir, fileName);
        return file;
    }

    /**
     * 进入相机
     */
    public void startCamearPic() {
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", false);// 全屏
        intent.putExtra("showActionIcons", false);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        // 指定调用相机拍照后照片的储存路径
        initFile("photo.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri(file));

        if (fragmentWeakReference != null) {
            fragmentWeakReference.get().startActivityForResult(intent, REQUEST_CODE_PHOTO);
        } else {
            if (getActivity() != null) {
                getActivity().startActivityForResult(intent, REQUEST_CODE_PHOTO);
            }
        }

    }

    /**
     * 进入图库选图
     */
    public void startImageChoose() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");

        //进入选图
        if (fragmentWeakReference != null) {
            fragmentWeakReference.get().startActivityForResult(intent, REQUEST_CODE_ALBUM);
        } else {
            getActivity().startActivityForResult(intent, REQUEST_CODE_ALBUM);
        }


    }

    /**
     * 获取图片的ContextUri
     *
     * @param imageFile 图片文件
     * @return ContextUri
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 裁剪照片
     *
     * @param uri 裁剪uri
     */
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", width);
//        intent.putExtra("aspectY", height);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);

        String manufacturer = DeviceUtils.getManufacturer();
        if ("Xiaomi".equals(manufacturer)) {
            //适配小米后
            mTempUri = Uri.parse("file:///" + dirPath + File.separator + "temp.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mTempUri);
        } else {
            //适配小米前
            intent.putExtra("return-data", true);
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        if (fragmentWeakReference != null) {
            fragmentWeakReference.get().startActivityForResult(intent, REQUEST_CODE_CROP);
        } else {
            getActivity().startActivityForResult(intent, REQUEST_CODE_CROP);
        }
    }

    /**
     * 结果处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_PHOTO:
                if (isCrop) {
                    startPhotoZoom(getImageContentUri(file));
                } else {
                    if (listener != null) {
                        //相机，不裁剪,直接返回Uri和照片文件
                        listener.onFinish(getImageContentUri(file), file);
//                        fileCompress(getImageContentUri(file));
                    }
                }
                break;
            case REQUEST_CODE_ALBUM:

                if (data == null) {
                    return;
                }
                if (isCrop) {
                    startPhotoZoom(data.getData());
                } else {
                    if (listener != null) {
                        //不裁剪,直接返回Uri
                        listener.onFinish(data.getData(), new File(getRealPathFromURI(getActivity(), data.getData())));
//                        fileCompress(data.getData());
                    }
                }
                break;
            case REQUEST_CODE_CROP:

                String manufacturer = DeviceUtils.getManufacturer();
                if (!"Xiaomi".equals(manufacturer)) {
                    //适配小米前
                    if (data == null) {
                        return;
                    }
                    if (data.getExtras() != null) {
                        Bundle bundle = data.getExtras();
                        Bitmap photo = bundle.getParcelable("data");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        if (photo != null) {
                            photo.compress(Bitmap.CompressFormat.PNG, compressQuality, baos);
                        }

                        FileOutputStream fos = null;
                        if (mOnFinishChooseAndCropImageListener != null) {

                            try {
                                if (file != null) {
                                    file.getParentFile().delete();//删除照片
                                }
                                //将裁剪出来的Bitmap转换成本地文件
                                File file = initFile("image.png");
                                fos = new FileOutputStream(file);
                                fos.write(baos.toByteArray());
                                fos.flush();
                                //通知图库有更新
//                                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));

                                //裁剪过后返回Bitmap,处理生成文件用来上传
                                mOnFinishChooseAndCropImageListener.onFinish(photo, file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (fos != null)
                                    try {
                                        fos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                if (baos != null)
                                    try {
                                        baos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                            }
                        }

                    }
                } else {
                    //适配小米后
                    try {
                        Bitmap photo = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(Uri.parse("file:///" + dirPath + File.separator + "temp.jpg")));
                       //压缩
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        if (photo != null) {
                            photo.compress(Bitmap.CompressFormat.PNG, compressQuality, baos);
                        }

                        FileOutputStream fos = null;
                        if (mOnFinishChooseAndCropImageListener != null) {

                            try {
                                if (file != null) {
                                    file.getParentFile().delete();//删除照片
                                }
                                //将裁剪出来的Bitmap转换成本地文件
                                File file = initFile("image.png");
                                fos = new FileOutputStream(file);
                                fos.write(baos.toByteArray());
                                fos.flush();
                                //通知图库有更新
                                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));

                                //裁剪过后返回Bitmap,处理生成文件用来上传
                                mOnFinishChooseAndCropImageListener.onFinish(photo, file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (fos != null)
                                    try {
                                        fos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                if (baos != null)
                                    try {
                                        baos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                            }
                        }
                       //压缩
//
//                        File file = new File(new URI(mTempUri.toString()));
//                        //通知图库有更新
//                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
//                        //裁剪过后返回Bitmap,处理生成文件用来上传
//                        mOnFinishChooseAndCropImageListener.onFinish(photo, file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    }
                }


                break;
        }
    }
    /**
     * 相机相册压缩不裁切返回
     */
    public void fileCompress(Uri uri){
        try {
            Bitmap photo = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
            //压缩
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (photo != null) {
                photo.compress(Bitmap.CompressFormat.PNG, compressQuality, baos);
            }
            FileOutputStream fos = null;
            if (listener != null) {

                try {
                    if (file != null) {
                        file.getParentFile().delete();//删除照片
                    }
                    //将Bitmap转换成本地文件
                    File file = initFile("image.png");
                    fos = new FileOutputStream(file);
                    fos.write(baos.toByteArray());
                    fos.flush();
                    //通知图库有更新
                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));

                    //裁剪过后返回Bitmap,处理生成文件用来上传
                    listener.onFinish(uri, file);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null)
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (baos != null)
                        try {
                            baos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }

            //压缩

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 裁剪图片完成回调监听
     */
    public interface OnFinishChooseAndCropImageListener {
        void onFinish(Bitmap bitmap, File file);
    }

    /**
     * 仅选图完成回调监听
     */
    public interface OnFinishChooseImageListener {
        void onFinish(Uri uri, File file);
    }

    /**
     * 构造者
     */
    static public class Builder {

        private ImageChooseHelper imageChooseHelper;

        public Builder setUpFragment(Fragment fragment) {
            imageChooseHelper = new ImageChooseHelper(fragment);
            return this;
        }

        public Builder setUpActivity(Activity activity) {
            imageChooseHelper = new ImageChooseHelper(activity);
            return this;
        }

        public Builder setAuthority(String authority) {
            imageChooseHelper.authority = authority;
            return this;
        }

        /**
         * 设置存储路径
         *
         * @param dirPath 存储路径
         * @return
         */
        public Builder setDirPath(String dirPath) {
            imageChooseHelper.dirPath = dirPath;
            return this;
        }

        public Builder isCrop(boolean isCrop) {
            imageChooseHelper.isCrop = isCrop;
            return this;
        }

        public Builder setSize(int width, int height) {
            imageChooseHelper.width = width;
            imageChooseHelper.height = height;
            return this;
        }

        public Builder setCompressQuality(int compressQuality) {
            imageChooseHelper.compressQuality = compressQuality;
            return this;
        }


        /**
         * 图片裁剪返回监听
         *
         * @param listener
         */
        public Builder setOnFinishChooseImageListener(OnFinishChooseImageListener listener) {
            imageChooseHelper.listener = listener;
            return this;
        }

        /**
         * 设置选图裁剪回调监听
         *
         * @param onFinishChooseAndCropImageListener 选图裁剪回调监听
         */
        public Builder setOnFinishChooseAndCropImageListener(OnFinishChooseAndCropImageListener onFinishChooseAndCropImageListener) {
            imageChooseHelper.mOnFinishChooseAndCropImageListener = onFinishChooseAndCropImageListener;
            return this;
        }

        public ImageChooseHelper create() {
            return imageChooseHelper;
        }
    }
}
