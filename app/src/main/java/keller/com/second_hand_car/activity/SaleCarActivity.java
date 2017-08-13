package keller.com.second_hand_car.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import keller.com.second_hand_car.R;
import keller.com.second_hand_car.model.CarInfor;
import keller.com.second_hand_car.ui.SelectPicPopupWindow;
import keller.com.second_hand_car.utils.ReadProperties;
import keller.com.second_hand_car.utils.ToastUtils;

public class SaleCarActivity extends AppCompatActivity {

    private ImageView photoImageView;
    private Uri photoUri;
    private String photoPath;
    private static final int TAKE_PHOTO=0;
    private static final int PICK_PHOTO=1;
    private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框
    private SharedPreferences sp;
    private Bitmap bm = null;
    private EditText sacity;
    private EditText saName;
    private EditText satvtime;
    private EditText sddistance;
    private String sacity1;
    private String saName1;
    private String satvtime1;
    private String sddistance1;
    private String saPrice1;
    private EditText saPrice2;
    private Handler mHandler;
    private  CarInfor carInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_car);
        initViews();
        init();
    }

    /**
     * 初始化控件和监听器
     */
    private void initViews(){
        photoImageView=(ImageView)findViewById(R.id.showImg);
        sacity = (EditText)findViewById(R.id.sacity);
        saName = (EditText)findViewById(R.id.saName);
        satvtime = (EditText)findViewById(R.id.satvtime);
        sddistance= (EditText)findViewById(R.id.sddistance);
        saPrice2= (EditText)findViewById(R.id.saPrice);
        mHandler = new Handler();
    }

    private void init() {
        sp=getSharedPreferences("userinfo", 0);
    }



    public void albumBut(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_PHOTO);

    }
    public void showImage(View v){
        menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
        menuWindow.showAtLocation(findViewById(R.id.mainLayout),
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

    }
    public void my_back(View v){
        finish();

    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.takePhotoBtn:
                    cameraBut();
                    break;
                // 相册选择图片
                case R.id.pickPhotoBtn:
                    albumBut();
                    break;
                default:
                    break;
            }
        }
    };
    public void cameraBut(){
        //只是加了一个uri作为地址传入
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile=createImgFile();
        photoUri=Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
        startActivityForResult(intent,TAKE_PHOTO);

    }
    public void sSubmit(View v){
        //检查是否登陆
        String username = sp.getString("username","");
        if (!username.equals("")){
//            String imagePath = BitmapUtils.savePhoto(bm, Environment
//                    .getExternalStorageDirectory().getAbsolutePath(), String
//                    .valueOf(System.currentTimeMillis()));
//            Log.e("imagePath", imagePath+"");
//
//            carInfor = new CarInfor();
            initData();
//            carInfor.setcName(saName1);
//            carInfor.setcDistance(sddistance1);
//            carInfor.setcCity(sacity1);
//            carInfor.setcLicensrtime(satvtime1);
//            carInfor.setpUrl(imagePath);
//            carInfor.setcPrice(saPrice1);

            new ImageAsyncTask().execute();

        }else {
            ToastUtils.showToastSafe(this,"请先登录");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    private void initData() {

        sacity1 =sacity.getText().toString().trim();
        saName1=saName.getText().toString().trim();
        satvtime1=satvtime.getText().toString().trim();
        sddistance1=sddistance.getText().toString().trim();
        saPrice1=saPrice2.getText().toString().trim();


    }

    /**
     * 自定义图片名，获取照片的file
     */
    private File createImgFile(){
        //创建文件
        String fileName="img_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+".jpg";//确定文件名
//        File dir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File dir=Environment.getExternalStorageDirectory();
        File dir;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            dir=Environment.getExternalStorageDirectory();
        }else{
            dir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        File tempFile=new File(dir,fileName);
        try{
            if(tempFile.exists()){
                tempFile.delete();
            }
            tempFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        //获取文件路径
        photoPath=tempFile.getAbsolutePath();
        return tempFile;
    }

    /**
     * 压缩图片
     */
    private void setImageBitmap(){
        //获取imageview的宽和高
        int targetWidth=photoImageView.getWidth();
        int targetHeight=photoImageView.getHeight();

        //根据图片路径，获取bitmap的宽和高
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(photoPath,options);
        int photoWidth=options.outWidth;
        int photoHeight=options.outHeight;

        //获取缩放比例
        int inSampleSize=1;
        if(photoWidth>targetWidth||photoHeight>targetHeight){
            int widthRatio=Math.round((float)photoWidth/targetWidth);
            int heightRatio=Math.round((float)photoHeight/targetHeight);
            inSampleSize=Math.min(widthRatio,heightRatio);
        }

        //使用现在的options获取Bitmap
        options.inSampleSize=inSampleSize;
        options.inJustDecodeBounds=false;
        bm= BitmapFactory.decodeFile(photoPath,options);
        photoImageView.setImageBitmap(bm);
    }

    //将图片添加进手机相册
    private void galleryAddPic(){
        Intent mediaScanIntent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(photoUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ContentResolver resolver = getContentResolver();
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case TAKE_PHOTO:
                    setImageBitmap();
                    galleryAddPic();
                    break;
                case PICK_PHOTO:
                    Uri originalUri = data.getData(); // 获得图片的uri

                    try {
                        bm = MediaStore.Images.Media.getBitmap(resolver,
                                originalUri);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    photoImageView.setImageBitmap(ThumbnailUtils.extractThumbnail(
                            bm, 100, 100)); // 使用系统的一个工具类，参数列表为 Bitmap Width,Height
                    // 这里使用压缩后显示，否则在华为手机上ImageView 没有显示
                    // 显得到bitmap图片
                    photoImageView.setImageBitmap(bm);
                    //安卓4.4以上用这个方法获取地址
                    photoPath=getPathByUri4kitkat(this,originalUri);
//                    String[] proj = { MediaStore.Images.Media.DATA };
//                    // 好像是android多媒体数据库的封装接口，具体的看Android文档
//                    Cursor cursor = managedQuery(originalUri, proj, null, null,
//                            null);
//                    // 按我个人理解 这个是获得用户选择的图片的索引值
//                    int column_index = cursor
//                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    // 将光标移至开头 ，这个很重要，不小心很容易引起越界
//                    cursor.moveToFirst();
//                    // 最后根据索引值获取图片路径
//                    photoPath = cursor.getString(column_index);
//                    // tv.setText(path);
//                    Log.d("path", photoPath);
//                    // 有了照片路径，之后就是压缩图片，和之前没有什么区别
//                    // setImageBitmap();
                    break;
            }
        }
    }
    private ProgressDialog dialog;

    class ImageAsyncTask extends AsyncTask<Object,Integer,String> {
        File uploadFile = new File(photoPath);
        long totalSize = uploadFile.length(); // Get size of file, bytes
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialog = new ProgressDialog(SaleCarActivity.this);
            dialog.setTitle("正在上传...");
            dialog.setMessage("0k/"+totalSize/1000+"k");
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(0);
            dialog.show();
        }
        @Override
        protected String doInBackground(Object... params) {
            // TODO Auto-generated method stub
            uploadFile();
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            dialog.dismiss();

            ToastUtils.showToastSafe(SaleCarActivity.this, "恭喜您添加成功！");

        }

    }
    private String newName ="image.jpg";

    /* 上传文件至Server的方法 */
    private void uploadFile(){

        /*sacity1 =sacity.getText().toString().trim();
        saName1=saName.getText().toString().trim();
        satvtime1=satvtime.getText().toString().trim();
        sddistance1=sddistance.getText().toString().trim();
        saPrice1=saPrice2.getText().toString().trim();*/
        String end ="\r\n";
        String twoHyphens ="--";
        String boundary ="*****";
        try
        {
            URL url =new ReadProperties().getUrl(SaleCarActivity.this,
                    "CarInforController/insertCarinf.do?uid="+sp.getString("uid","3")+"&name="
                            +saName1+"&city="+sacity1+"&sptime="+satvtime1+"&distance="+sddistance1
                            +"&price="+saPrice1);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
          /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
          /* 设置传送的method=POST */
            con.setRequestMethod("POST");
          /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary="+boundary);
          /* 设置DataOutputStream */
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "+
                    "name=\"file1\";filename=\""+
                    newName +"\""+ end);
            ds.writeBytes(end);
          /* 取得文件的FileInputStream */
            FileInputStream fStream =new FileInputStream(photoPath);
          /* 设置每次写入1024bytes */
            int bufferSize =1024;
            byte[] buffer = new byte[bufferSize];
            int length =-1;
          /* 从文件读取数据至缓冲区 */
            while((length = fStream.read(buffer)) !=-1)
            {
            /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* close streams */
            fStream.close();
            ds.flush();
          /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) !=-1 )
            {
                b.append( (char)ch );
            }
          /* 将Response显示于Dialog */
//            showDialog("上传成功"+b.toString().trim());
            Log.i("上传成功",b.toString().trim());
          /* 关闭DataOutputStream */
            ds.close();
        }
        catch(Exception e)
        {
            System.out.println("------------------------------");
            System.out.println("------------------------------");
            System.out.println("------------------------------");
            System.out.println(e);
          //  Log.i("上传失败",e.toString());
//            showDialog("上传失败"+e);
        }
    }
//    /* 显示Dialog的method */
//    private void showDialog(String mess)
//    {
//        new AlertDialog.Builder(this).setTitle("Message")
//                .setMessage(mess)
//                .setNegativeButton("确定",new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                    }
//                })
//                .show();
//    }

    // 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
    @SuppressLint("NewApi")
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
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
                final String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
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
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
