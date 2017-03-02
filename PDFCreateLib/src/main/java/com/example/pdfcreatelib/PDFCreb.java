package com.example.pdfcreatelib;


import android.graphics.Bitmap;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Created by vim on 20/02/17.
 */

public class PDFCreb {

    public void create(Bitmap bitmap){
        try {

            Document doc = new Document();
            PdfWriter.getInstance(doc,new FileOutputStream("YourPDFHere.pdf"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            doc.add(image);
            doc.close();
        }catch (Exception e){

        }
    }
}
