package com.necistudio.pdfengineme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.StackTransformer;
import com.eftimoff.viewpagertransformers.TabletTransformer;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.necistudio.vigerpdf.adapter.VigerAdapter;
import com.necistudio.vigerpdf.manage.OnResultListener;
import com.necistudio.vigerpdf.VigerPDF;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<Bitmap> itemData;
    private VigerAdapter adapter;
    private Button btnFromFile, btnFromNetwork,btnCancle;
    private VigerPDF vigerPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btnFromFile = (Button) findViewById(R.id.btnFile);
        btnFromNetwork = (Button) findViewById(R.id.btnNetwork);
        btnCancle = (Button)findViewById(R.id.btnCancle);
        vigerPDF = new VigerPDF(this);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vigerPDF.cancle();
            }
        });
        btnFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(100)
                        .withFilter(Pattern.compile(".*\\.pdf$"))
                        .start();
            }
        });

        btnFromNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemData.clear();
                adapter.notifyDataSetChanged();
                fromNetwork("http://warungkepo.com/unduh/tempat_unduhan/9/p18hsr4kq2fce1gv3fpcmia1jd65.pdf");
            }
        });
        itemData = new ArrayList<>();
        adapter = new VigerAdapter(getApplicationContext(), itemData);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new StackTransformer());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            itemData.clear();
            adapter.notifyDataSetChanged();
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            fromFile(filePath);
        }
    }

    private void fromNetwork(String endpoint) {
        itemData.clear();
        adapter.notifyDataSetChanged();
        vigerPDF.cancle();
        vigerPDF.initFromNetwork(endpoint, new OnResultListener() {
            @Override
            public void resultData(Bitmap data) {
                Log.e("data", "run");
                itemData.add(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void progressData(int progress) {
                Log.e("data", "" + progress);
            }

            @Override
            public void failed(Throwable t) {

            }

        });
    }

    private void fromFile(String path) {
        itemData.clear();
        adapter.notifyDataSetChanged();
        File file = new File(path);
        vigerPDF.cancle();
        vigerPDF.initFromFile(file, new OnResultListener() {
            @Override
            public void resultData(Bitmap data) {
                itemData.add(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void progressData(int progress) {
                Log.e("data", "" + progress);

            }

            @Override
            public void failed(Throwable t) {

            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vigerPDF != null) vigerPDF.cancle();
    }
}
