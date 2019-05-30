package academy.learnprogramming.pooling;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import academy.learnprogramming.pooling.ApiUrl.ApiInterface;
import academy.learnprogramming.pooling.ApiUrl.ApiUrl;
import academy.learnprogramming.pooling.response.PeopleResponse;
import academy.learnprogramming.pooling.response.SalaryResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class detail extends AppCompatActivity {
    RelativeLayout relativeLayout;
     String imageFilePath;
    Toolbar toolbar;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,house,s;
    private View main;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        relativeLayout = findViewById(R.id.r);
        toolbar = findViewById(R.id.toolbar2);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        t6 = findViewById(R.id.t6);
        t7 = findViewById(R.id.t7);
        s = findViewById(R.id.s);
        house = findViewById(R.id.house);
        t9 = findViewById(R.id.t9);
        t11 = findViewById(R.id.t11);
        t10 = findViewById(R.id.name);

        imageView = findViewById(R.id.image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("सिविल लाइन वार्ड ");
        getSupportActionBar().setElevation((float) 10.0);
        //toolbar.setSubtitle("Welcome");
        toolbar.setLogo(R.drawable.kamal);
        String name= getIntent().getStringExtra("name");

        //t9.setText("jkgjgjhgjgjh");
        if (Build.VERSION.SDK_INT >= 28) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
        }
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiService=retrofit.create(ApiInterface.class);

        Call<SalaryResponse> call=apiService.salary(name);
        call.enqueue(new Callback<SalaryResponse>() {
            @Override
            public void onResponse(Call<SalaryResponse> call, Response<SalaryResponse> response) {
                try {
                    if(response.body().getBooth().equalsIgnoreCase("booth15")){
                       t11.setText("prashala bhawan katora talab, civil line");
                    }else if(response.body().getBooth().equalsIgnoreCase("booth24")){
                        t11.setText("Sant kavram sha kanya uma shala katoratalab rum no:04");
                    }else if(response.body().getBooth().equalsIgnoreCase("booth10")){
                        t11.setText("Ambedkar Bhawan Civil line Room no: 1");
                    }else if(response.body().getBooth().equalsIgnoreCase("booth20")){
                        t11.setText("prashala bhawan katora talab, civil line");
                    }else{
                        t11.setText("prashala bhawan katora talab, civil line");
                    }
                    if (response.body().getGender().equalsIgnoreCase("Male")){
                        t10.setText(response.body().getName()+"("+response.body().getAge()+" M)");
                    }else{
                        t10.setText(response.body().getName()+"("+response.body().getAge()+" F)");
                    }

                    Log.d("page no",""+response.body().getPno());
                    t1.setText(response.body().getBooth());
                    t2.setText(response.body().getPno());
                    t3.setText(response.body().getSno());
                    t4.setText(response.body().getVno());
                    //t5.setText(response.body().getFname());
                    //t6.setText(response.body().getAge());
                    //t7.setText(response.body().getGender());
                    t5.setText(response.body().getFname());
                    house.setText(response.body().getHouse());
                    //t8.setText(response.body().getHouse());
                    s.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String no = t9.getText().toString();

                String msg = t10.getText()+",Booth No.:"+t1.getText()
                        +",Booth Name:"+t11.getText()
                        +",P-No.:"+t2.getText()+",S-No.: "+t3.getText()
                        +",Voter Id No.:"+t4.getText()+",H-No.:"+house.getText();


                sendSMS(no,msg);

                            try {


                                Bitmap bm = screenShot(getView());
                                File file = saveBitmap(bm, "mantis_image.png");
                                Log.i("chase", "filepath: " + file.getAbsolutePath());

                                Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
                                //Uri uri = FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName() + ".fileprovider", new File(file.getAbsolutePath()));

                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out Your Voting Details.");
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                shareIntent.setType("image/*");
                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(Intent.createChooser(shareIntent, "share via"));
                            }catch (Exception e){

                            }

                        }
                    });

                }
                catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<SalaryResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "nhi hua", Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.products:
//                String msg="hello";
//                String to="4354";
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//
//                intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
//                intent.putExtra("address", to);
//                intent.putExtra("sms_bod",msg);
//                startActivity(intent);
                String no = t9.getText().toString();
                String msg = t10.getText()+",Booth No.:"+t1.getText()
                        +",Booth Name:"+t11.getText()
                        +",P-No.:"+t2.getText()+",S-No.: "+t3.getText()
                        +",Voter Id No.:"+t4.getText()+",H-No.:"+house.getText();


                sendSMS(no,msg);
//                String to="9982648660";
//                String msg=t1.getText().toString();
//                Intent smsIntent= new Intent(Intent.ACTION_VIEW);
//                smsIntent.setType("vnd.android-dir/mms-sms");
//                smsIntent.putExtra("address", to);
//                smsIntent.putExtra("sms_body",msg);
//                startActivity(smsIntent);
                try {


                    Bitmap bm = screenShot(getView());

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {

                    }


                    File file = saveBitmap(bm, "ss.png");
                    Log.i("chase", "filepath: " + file.getAbsolutePath());

                    Log.d("test", photoFile.getName().toString());
                    //Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));


                    Uri uri = FileProvider.getUriForFile(getApplicationContext(),"academy.learnprogramming.pooling.fileprovider", file);


                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out Your Voting Details.");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "share via"));
                }catch (Exception e){

                }
        }
        return super.onOptionsItemSelected(item);
        }
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "mantissa";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }
    private Bitmap screenShot(View v) {
//        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
//        return bitmap;
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }

    private static File saveBitmap(Bitmap bm, String fileName){
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
public void sendSMS(String phoneNo, String msg) {
    try {


        if (Build.VERSION.SDK_INT >= 28) {
            String[] permissions = {Manifest.permission.SEND_SMS};
            requestPermissions(permissions, 1);
        }
//        SmsManager sms = SmsManager.getDefault();
//        PendingIntent sentPI;
//        String SENT = "SMS_SENT";
//
//        sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
//
//        sms.sendTextMessage(phoneNo, null, msg, sentPI, null);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, msg, null, null);
        Toast.makeText(getApplicationContext(), "Message Sent",
                Toast.LENGTH_LONG).show();
    } catch (Exception ex) {
        Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                Toast.LENGTH_LONG).show();
        ex.printStackTrace();
    }
}


    public View getView() {

        View rootView = getWindow().getDecorView().getRootView();
        return rootView;
    }









}
