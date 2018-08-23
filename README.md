# webp-support
Support Dynamic Webp Format

# Demo

终结方式获取drawable...可以获取每一帧画面

```
val stream = assets.open("sticker1.webp")
val supported = FrameSequenceHelper.isSupported(stream)
Log.d(TAG, "isSupported=" + supported)

mFrameSequence = FrameSequence.decodeStream(stream)
mFrameSequenceState = mFrameSequence.createState()
val width = mFrameSequence.getWidth()
val height = mFrameSequence.getHeight()
bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
val bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

val drawable = FrameSequenceDrawable(mFrameSequence)
drawable.isRunning
iv.setImageDrawable(drawable)
```

简单实用

```
<AnimatedImageView
    android:id="@+id/iv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```





# Usage

```
allprojects {
    repositories {
	    maven { url 'https://jitpack.io' }
    }
}
```

step2

```
dependencies {
	implementation 'com.github.xieyangxuejun:webp-support:0.0.1'
}
```