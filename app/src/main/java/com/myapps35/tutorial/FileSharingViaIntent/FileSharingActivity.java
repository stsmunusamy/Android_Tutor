package com.myapps35.tutorial.FileSharingViaIntent;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myapps35.tutorial.R;
import com.myapps35.tutorial.SplashMainIndex.Utility.UtilsClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileSharingActivity extends Activity
{


    private static final int CAMERA_REQUEST = 159;
    private static final int GALLERY_REQUEST = 160;
    private static final int SD_CARD_REQUEST = 161;
    private static final int DOCS_REQUEST = 162;

    private final int REQUEST_CAMERA_PERMISSION = 163;
    private final int REQUEST_GALLERY_PERMISSION = 164;

    private Button btnGallery;
    private Button btnImages;
    private Button btnCamera;
    private Button btnSdCard;
    private Button btnDeviceFiles;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_sharing);

        initialize();

        setOnClickListener();

    }

    private void initialize()
    {

        btnGallery = (Button) findViewById(R.id.btnGallery);

        btnImages = (Button) findViewById(R.id.btnImages);

        btnCamera = (Button) findViewById(R.id.btnCamera);

        btnSdCard = (Button) findViewById(R.id.btnSdCard);

        btnDeviceFiles = (Button) findViewById(R.id.btnDeviceFiles);

    }

    private void setOnClickListener()
    {
        btnCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getImageByCamera();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getImageFromGallery();
            }
        });

        btnImages.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getImageBySdCard();
            }
        });

        btnSdCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getFilesFromLocal();
            }
        });

        btnDeviceFiles.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getDocsFromDevice();
            }
        });
    }


    private void getImageFromGallery()
    {
        if(checkStoragePerimission(this, REQUEST_GALLERY_PERMISSION))
        {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, GALLERY_REQUEST);
        }
    }

    private void getImageBySdCard()
    {
        if(checkCameraPermission(this, REQUEST_GALLERY_PERMISSION))
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select here"), SD_CARD_REQUEST);

            } else
            {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select here"), SD_CARD_REQUEST);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void getDocsFromDevice()
    {
        if(checkCameraPermission(this, REQUEST_GALLERY_PERMISSION))
        {

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            String[] mimetypes = {"text/plain", "application/pdf", "application/msword", "application/excel", "application/x-excel"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
            startActivityForResult(intent, DOCS_REQUEST);
        }
    }

    private void getImageByCamera()
    {
        if(checkCameraPermission(this, REQUEST_CAMERA_PERMISSION))
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile()));
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    public boolean checkCameraPermission(Context context, int REQUEST_CAMERA_PERMISSION)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);

            if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

                return false;
            } else
            {
                return true;
            }
        } else
        {
            return true;
        }
    }

    public boolean checkStoragePerimission(Context context, int REQUEST_GALLERY_PERMISSION)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
            {
                ((Activity) context).requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALLERY_PERMISSION);

                return false;
            } else
            {
                return true;
            }
        } else
        {
            return true;
        }
    }

    private java.io.File getTempFile()
    {
        final java.io.File path = new java.io.File(Environment.getExternalStorageDirectory(), getPackageName());

        if(!path.exists())
        {
            path.mkdir();
        }

        return new java.io.File(path, "image.tmp");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        switch (requestCode)
        {

            case REQUEST_CAMERA_PERMISSION:

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    getImageByCamera();
                }
                break;

            case REQUEST_GALLERY_PERMISSION:

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    getImageFromGallery();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public String getPath(Uri uri, boolean isFromSdCard)
    {
        String filepath = null;

        try
        {
            String[] projection;

            if(isFromSdCard)
            {
                projection = new String[]{MediaStore.Images.Media.DATA};
            } else
            {
                projection = new String[]{MediaStore.MediaColumns.DATA};
            }

            Cursor cursor = managedQuery(uri, projection, null, null, null);

            int column_index;

            if(isFromSdCard)
            {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            } else
            {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            }

            cursor.moveToFirst();

            filepath = cursor.getString(column_index);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return filepath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            switch (requestCode)
            {

                case DOCS_REQUEST:
                    if(resultCode == Activity.RESULT_OK)
                    {
                        if(data != null && data.getData() != null)
                        {

                            /* Method 1 */
                            {
                                Uri returnUri = data.getData();
                                String mimeType = getContentResolver().getType(returnUri);

                                Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                                returnCursor.moveToFirst();

                                Toast.makeText(this, String.valueOf(returnCursor.getString(nameIndex)) + String.valueOf(Long.toString(returnCursor.getLong(sizeIndex))), Toast.LENGTH_LONG).show();

                                getContentResolver().openInputStream(returnUri);

                            }

                            /* Method 2 */
                            {


                            }
                        }
                    }
                    break;


                case SD_CARD_REQUEST:
                    if(resultCode == Activity.RESULT_OK)
                    {
                        Uri selectedImageUri = data.getData();

                        String imgPath;
                        if(String.valueOf(selectedImageUri).startsWith("file:///"))
                        {
                            imgPath = UtilsClass.validateLocalImagePath(selectedImageUri.getPath());
                        } else if(String.valueOf(selectedImageUri).startsWith("content://"))
                        {
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));

                            String root = Environment.getExternalStorageDirectory().toString();

                            File myDir = new File(root + "/Download");

                            if(!myDir.exists())
                            {
                                myDir.mkdirs();
                            }

                            Random generator = new Random();
                            int n = 10000;
                            n = generator.nextInt(n);
                            String fname = "Image-" + n + ".jpg";
                            File file = new File(myDir, fname);

                            if(file.exists())
                                file.delete();

                            try
                            {
                                FileOutputStream out = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                out.flush();
                                out.close();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                            imgPath = myDir + File.separator + fname;

                        } else
                        {
                            imgPath = getPath(selectedImageUri, true);
                        }

                        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);

                        Toast.makeText(this, "Img Path=" + imgPath, Toast.LENGTH_SHORT).show();

                        if(bitmap != null)
                        {
                            showPreviewBeforeSend(this, "Share This Image", bitmap, imgPath, false, 60);
                        } else
                        {
                            Toast.makeText(this, "No Image Found!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;


                case GALLERY_REQUEST:

                    if(resultCode == Activity.RESULT_OK)
                    {

                        try
                        {

                            if(data != null && data.getData() != null)
                            {

                                Uri selectedImage = data.getData();

                                String path = getPath(selectedImage, false);

                                System.out.println("Image path from Gallery" + path);

                                final Bitmap gallerPicture = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                                showPreviewBeforeSend(this, "Share This Image", gallerPicture, path, false, 60);

                            }

                        }
                        catch (Exception e)
                        {

                            Toast.makeText(this, "File is too large to upload", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }
                    }
                    break;


                case CAMERA_REQUEST:

                    if(resultCode == Activity.RESULT_OK)
                    {
                        try
                        {
                            final Bitmap photo;

                            if(data == null)
                            {
                                final File file = getTempFile();

                                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                            } else
                            {
                                if(data.getData() != null)
                                {
                                    photo = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                                } else
                                {
                                    photo = (Bitmap) data.getExtras().get("data");
                                }
                            }

                            Uri selectedImage = getImageUri(this, photo);

                            String path = getPath(selectedImage, false);

                            //                            Bitmap bitmap = BitmapFactory.decodeFile(path);

                            showPreviewBeforeSend(this, "Share This Image", photo, path, true, 60);
                            //                            shareCompressedImage(mContext, path, true, 60, "");

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(this, "File is too large to upload", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void showPreviewBeforeSend(final Context mContext, String imgName, Bitmap bitmap, final String imgPath, final boolean isFromCamera, final int compressRatio)
    {

        Bitmap imgBitMap;

        imgBitMap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, false);

        final Dialog dialog = new Dialog(mContext);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.chat_img_preview_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        Toolbar chatPreviewToolbar = (Toolbar) dialog.findViewById(R.id.chatPreviewToolbar);
        chatPreviewToolbar.setTitle(UtilsClass.validateString(imgName));
        chatPreviewToolbar.setNavigationIcon(ContextCompat.getDrawable(mContext, R.mipmap.fail));

        ImageView chatPreviewImage = (ImageView) dialog.findViewById(R.id.chatPreviewImage);
        chatPreviewImage.setImageBitmap(imgBitMap);

        TextView chatPreviewCaptionTxtView = (TextView) dialog.findViewById(R.id.chatPreviewCaptionTxtView);
        chatPreviewCaptionTxtView.setVisibility(View.GONE);

        LinearLayout chatPreviewCaptionLayout = (LinearLayout) dialog.findViewById(R.id.chatPreviewCaptionLayout);
        chatPreviewCaptionLayout.setVisibility(View.VISIBLE);

        final EditText et_captionTxt = (EditText) dialog.findViewById(R.id.et_captionTxt);

        FrameLayout sendMessageButton = (FrameLayout) dialog.findViewById(R.id.sendMessageButton);

        chatPreviewToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {

                    dialog.dismiss();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


        dialog.show();
    }


    private void getFilesFromLocal()
    {
        List<Intent> targets = new ArrayList<Intent>();
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        String[] mimetypes = {"text/plain", "application/pdf", "application/msword", "application/excel", "application/x-excel"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        List<ResolveInfo> candidates = getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo candidate : candidates)
        {
            String packageName = candidate.activityInfo.packageName;

//            if(!packageName.equalsIgnoreCase("com.google.android.apps.photos") && !packageName.equalsIgnoreCase("com.google.android.apps.plus") && !packageName.equalsIgnoreCase("com.android.documentsui") && !packageName.equalsIgnoreCase("com.google.android.apps.docs"))
            if(!packageName.contains("com.google"))
            {
                Intent iWantThis = new Intent();
                iWantThis.setType("*/*");
                iWantThis.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                iWantThis.setAction(Intent.ACTION_OPEN_DOCUMENT);
                iWantThis.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                iWantThis.setPackage(packageName);
                targets.add(iWantThis);
            }
        }

        Intent chooser = Intent.createChooser(targets.remove(0), "Select Picture");

        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets.toArray(new Parcelable[targets.size()]));
        startActivityForResult(chooser, 1);
    }

}