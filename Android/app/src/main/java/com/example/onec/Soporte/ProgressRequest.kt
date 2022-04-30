package com.example.onec.Soporte

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class ProgressRequestBody(private val mFile: File, /*private val mListener: UploadCallbacks*/) :
    RequestBody() {
    interface UploadCallbacks {
        fun onProgressUpdate(percentage: Int)
        fun onError()
        fun onFinish()
        fun uploadStart()
    }

    override fun contentType(): MediaType? {
        // i want to upload only images
        return "image/*".toMediaTypeOrNull()
    }

    // @kotlin.Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile.length()
    }

    //  @kotlin.Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val fileLength = mFile.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val `in` = FileInputStream(mFile)
        var uploaded: Long = 0
        try {
            var read: Int = 0
            val handler = Handler(Looper.getMainLooper())
            while (`in`.read(buffer).also { read = it } != -1) {
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
                //handler.post(ProgressUpdater(uploaded, fileLength))
            }
        } finally {
            `in`.close()
        }
    }
}