package com.foretree.example

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.support.rastermill.FrameSequence
import android.support.rastermill.FrameSequenceHelper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.toString()
    lateinit var mFrameSequenceState: FrameSequence.State
    lateinit var mFrameSequence: FrameSequence
    lateinit var bitmap:Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stream = assets.open("sticker1.webp")
        val supported = FrameSequenceHelper.isSupported(stream)
        Log.d(TAG, "isSupported=" + supported)

        mFrameSequence = FrameSequence.decodeStream(stream)
        mFrameSequenceState = mFrameSequence.createState()
        sb.max = mFrameSequence.frameCount-1
        sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                iv.setImageBitmap(getFrame(progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        val width = mFrameSequence.getWidth()
        val height = mFrameSequence.getHeight()
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

//        val drawable = FrameSequenceDrawable(mFrameSequence)
//        drawable.isRunning
//        iv.setImageDrawable(drawable)
//        mFrameSequenceState.getFrame(0, bitmap2, -1)
//        iv.setImageBitmap(bitmap2)
        for (i in 0..mFrameSequence.frameCount) {
            getFrame(i)
            FileUtil.bitmap2File(bitmap, File(Environment.getExternalStorageDirectory().absolutePath +
                    "/DCIM/寄意/images/$i.png"))
        }
    }

    fun getFrame(index: Int): Bitmap? {
        try {
            mFrameSequenceState.getFrame(index, bitmap, -1)
            return bitmap
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        return null
    }
}
