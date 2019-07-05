# PopupUtils
PopupWindow
Step 1. Add the JitPack repository to your build file

#### 使用

1. 导入,使用最新版

    [![](https://www.jitpack.io/v/KiWiLss/PopupUtils.svg)](https://www.jitpack.io/#KiWiLss/PopupUtils)


    maven { url 'https://www.jitpack.io' }

    implementation 'com.github.KiWiLss.PopupUtils:ximageselect:latest.release'

2，在界面中初始化,第二个参数代表是否裁剪图片

```
mXImgSelect = new XImgSelect(this, false, new XImgSelectCallback() {

            @Override
            public void noCamerPermission(boolean isShow) {//没有权限的监听
                if (isShow){
                    Toast.makeText(CameraTestActivity.this, "请设置权限", Toast.LENGTH_SHORT).show();
                }else {//点了不再询问,可用做成进入设置权限
                    Toast.makeText(CameraTestActivity.this, "在设置中授予权限", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void getPicPath(String path) {//结果的监听
                Log.e(TAG, "getPicPath: "+path);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                mIvPic.setImageBitmap(bitmap);
                //BitmapUtils.saveBpImageToGallery(CameraTestActivity.this,bitmap);
            }
        });
```
3. 权限处理
```
 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mXImgSelect.onRequestPermissionsResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
```
4. 调用onActivityResult()才能获取结果
```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果裁剪图片,在获取结果前设置图片尺寸
        //mXImgSelect.setClipSize(300,300);
        mXImgSelect.onActivityResult(requestCode,resultCode,data);
        Log.e("MMM", "onActivityResult: "+ requestCode+"--"+data);
        //mIvPic.setImageURI(mXImgSelect.uriClipUri);
    }
```

#### BitmapUtils

 这个工具类里有一些常用的对 bitmap 处理的方法。
 ```
  compressSize      ：压缩图片尺寸
  compressBitmap        ：图片质量压缩
  zoomImage     ：图片缩放
  getBitmapFromDrawable     ：drawable转bitmap
  bitmapTodrawable      ：bitmapTodrawable
  getByteFromBitmap     ：bitamp -> byte[]
  getBitmapFromByte     :byte[] -> bitmap
  createBitmapThumbnail
  getBitmapByUri        :Uri -> bitmap
  saveBpImageToGallery      :保存bitmap到本地
 ```


 #### github 地址
 [github](https://github.com/KiWiLss/PopupUtils)
