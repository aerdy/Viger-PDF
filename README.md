# Viger - View Pager PDF OpenSource
![alt tag](https://camo.githubusercontent.com/0ad3a71058a9c743494a613898d9469798996e36/68747470733a2f2f6165726f7969642e66696c65732e776f726470726573732e636f6d2f323031372f30312f73637265656e73686f745f313438353835353937362e706e67)
#### Viger PDF How to Works is file pdf extract or convert to bitmap use Library Vudroid was write C Language, after that images include to adapter viewpager
## Download
Gradle:
```
repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
}
```
```
dependencies {
    compile 'com.github.aerdy:Android-Viger_View_Pager_PDF_OpenSource:-SNAPSHOT'
}

```

### Support Android Version
```
        minSdkVersion 15
```
### Library Support Open Source PDF and Connection 
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

## Supprt Get PDF From File
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

## License
```
Copyright 2017 Necis Studio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
