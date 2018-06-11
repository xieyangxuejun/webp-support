package com.foretree.example

import android.graphics.Bitmap
import android.os.Bundle
import android.support.rastermill.FrameSequence
import android.support.rastermill.FrameSequenceHelper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.toString()
    lateinit var mFrameSequenceState: FrameSequence.State
    lateinit var mFrameSequence: FrameSequence
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
    }

    fun getFrame(index: Int): Bitmap? {
        val width = mFrameSequence.getWidth()
        val height = mFrameSequence.getHeight()
        try {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            mFrameSequenceState.getFrame(index, bitmap, -1)
            return bitmap
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        return null
    }
}
