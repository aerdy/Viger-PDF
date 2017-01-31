package com.necistudio.pdfengineme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    private Button btnFromFile, btnFromNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        btnFromFile = (Button) findViewById(R.id.btnFile);
        btnFromNetwork = (Button) findViewById(R.id.btnNetwork);

        btnFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(100)
                        .withFilter(Pattern.compile(".*\\.pdf$")) // Filtering files and directories by file name using regexp
                        //withFilterDirectories(true) // Set directories filterable (false by default)
                        //.withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });

        btnFromNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromNetwork("http://www.pdf995.com/samples/pdf.pdf");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            fromFile(filePath);
        }
    }

    private void fromNetwork(String endpoint) {
        new VigerPDF(this, endpoint).initFromFile(new OnResultListener() {
            @Override
            public void resultData(ArrayList<Bitmap> data) {
                VigerAdapter adapter = new VigerAdapter(getApplicationContext(),data);
                viewPager.setAdapter(adapter);
            }
        });
    }

    private void fromFile(String path) {
        File file = new File(path);
        new VigerPDF(this, file).initFromFile(new OnResultListener() {
            @Override
            public void resultData(ArrayList<Bitmap> data) {
                VigerAdapter adapter = new VigerAdapter(getApplicationContext(),data);
                viewPager.setAdapter(adapter);
            }
        });
    }
}
