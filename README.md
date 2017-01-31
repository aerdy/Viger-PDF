# Viger - View Pager PDFOpenSource

<p align="center">
  <img src="https://aeroyid.files.wordpress.com/2017/01/screenshot_1485855976.png" width="350"/>
</p>

### Library Support Open Source PDF and Connetion 
- Vudroid Library PDF
- Retrofit 2 Get Stream Connection

##Support Get PDF From URL
```
private void fromNetwork(String endpoint) {
        new VigerPDF(this, endpoint).initFromFile(new OnResultListener() {
            @Override
            public void resultData(ArrayList<Bitmap> data) {
                VigerAdapter adapter = new VigerAdapter(getApplicationContext(),data);
                viewPager.setAdapter(adapter);
            }
        });
    }
```

##Supprt Get PDF From File
```
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
```
