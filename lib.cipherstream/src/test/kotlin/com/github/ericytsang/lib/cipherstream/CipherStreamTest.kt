package com.github.ericytsang.lib.cipherstream

import com.github.ericytsang.lib.simplepipestream.SimplePipedInputStream
import com.github.ericytsang.lib.simplepipestream.SimplePipedOutputStream
import com.github.ericytsang.lib.streamtest.StreamTest
import org.junit.Test
import java.io.DataInputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.Arrays
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.thread

class CipherStreamTest:StreamTest()
{
    override val sink:InputStream
    override val src:OutputStream

    val pipedO = SimplePipedOutputStream()
    val pipedI = SimplePipedInputStream(pipedO)

    init
    {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            SecretKeySpec("1234567890123456".toByteArray(),"AES"),
            IvParameterSpec("1234567890123456".toByteArray()))
        src = CipherOutputStream(pipedO,cipher)
    }

    init
    {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            SecretKeySpec("1234567890123456".toByteArray(),"AES"),
            IvParameterSpec("1234567890123456".toByteArray()))
        sink = CipherInputStream(pipedI,cipher)
    }

    @Test
    fun flush_test_single_block()
    {
        val sentData = "hey friend".toByteArray()
        val receivedData = ByteArray(sentData.size)
        src.write(sentData)
        val recvThread = thread {
            DataInputStream(sink).readFully(receivedData)
        }
        src.flush()
        recvThread.join()
        assert(Arrays.equals(receivedData,sentData))
    }

    @Test
    fun flush_test_multi_block()
    {
        val sentData = "hey friend long long long long long long long long long long long long long long long long long long long long long long long long long long string".toByteArray()
        val receivedData = ByteArray(sentData.size)
        src.write(sentData)
        val recvThread = thread {
            DataInputStream(sink).readFully(receivedData)
        }
        src.flush()
        recvThread.join()
        assert(Arrays.equals(receivedData,sentData))
    }
}
