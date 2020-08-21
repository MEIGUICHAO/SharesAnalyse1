package com.mgc.sharesanalyse.utils

import com.mgc.sharesanalyse.base.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ArrayBlockingQueue

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
object FileLogUtil {
    private const val isDebug = true
    val FilePath = "/data/data/" + App.getContext().getPackageName() + "/mgc"
    private val mLogQueue = ArrayBlockingQueue<Triple<Long,String, String>>(100000)

    init {
        GlobalScope.launch(Dispatchers.IO) {
            while (isActive) {
                val take = mLogQueue.take()
                if (isDebug) {
                    try {
                        val path = File(FilePath)
                        if (!path.exists()) path.mkdir()
                        val file = File("$FilePath/${take.second}.txt")
                        FileUtil.createDir(file.parentFile!!.absolutePath)
                        if (!file.exists())file.createNewFile()
                        val out = FileOutputStream(file, true)
                        val newLine = System.getProperty("line.separator")
                        out.write(newLine?.toByteArray())
//                        out.write((DateUtils.format(take.first,FormatterEnum.YYYYMMDD__HH_MM_SS) + " ").toByteArray())
                        out.write(take.third.toByteArray())
                        out.flush()
                        out.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }

    fun v(tag: String, msg: String) {
        if (isDebug) {
            mLogQueue.add(Triple(System.currentTimeMillis(),tag, msg))
        }
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        if (isDebug) {
            mLogQueue.add(Triple(System.currentTimeMillis(),tag, msg))
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        if (isDebug) {
            mLogQueue.add(Triple(System.currentTimeMillis(),tag, msg))
        }
    }

    fun w(tag: String, msg: String) {
        if (isDebug) {
            mLogQueue.add(Triple(System.currentTimeMillis(),tag, msg))
        }
    }

    fun e(tag: String, msg: String) {
        if (isDebug) {
            mLogQueue.add(Triple(System.currentTimeMillis(),tag, msg))
        }
    }
}