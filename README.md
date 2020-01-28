# Viger - View Pager PDF OpenSource
![alt tag](https://camo.githubusercontent.com/0ad3a71058a9c743494a613898d9469798996e36/68747470733a2f2f6165726f7969642e66696c65732e776f726470726573732e636f6d2f323031372f30312f73637265656e73686f745f313438353835353937362e706e67)
#### Viger PDF How to Works is file pdf extract or convert to bitmap use Library Vudroid was write C Language, after that images include to adapter viewpager
## Download
Gradle:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
dependencies {
	        implementation 'com.github.aerdy:Viger-PDF:1.2'
	}

```

### Support Android Version
```
        minSdkVersion 15
```
### Library Support Open Source PDF and Connection 
- Vudroid Library PDF
- Retrofit 2 Get Stream Connection
### Support Vertical Swap and Horizontal Swap
```
    <com.necistudio.vigerpdf.utils.ViewPagerZoomHorizontal
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.necistudio.vigerpdf.utils.ViewPagerZoomVertical
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

```
### Support Get PDF From URL
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
    Copyright (C) 2020  Arthdi putra anna

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
```
