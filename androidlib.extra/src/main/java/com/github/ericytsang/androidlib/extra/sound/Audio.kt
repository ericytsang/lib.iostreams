package com.github.ericytsang.androidlib.extra.sound

import android.content.Context
import android.media.MediaPlayer
import com.github.ericytsang.androidlib.extra.resource.NormalizedUri
import com.github.ericytsang.androidlib.extra.resource.RawResId
import com.github.ericytsang.lib.domainobjects.DataObject
import java.io.Closeable

object Audio
{
    class Player
    private constructor(
            val data:Data,
            private val mediaPlayer:MediaPlayer)
        :
            Closeable
    {
        companion object
        {
            fun create(data:Data,context:Context):Player?
            {
                return when(data)
                {
                    is Data.Uri -> Player(
                            data,
                            MediaPlayer.create(context,data.uri.uri)
                                    ?: return null)
                    is Data.ResId -> Player(
                            data,
                            MediaPlayer.create(context,data.rawResId.id)
                                    ?: return null)
                }
            }
        }

        fun play()
        {
            mediaPlayer.start()
        }

        override fun close()
        {
            mediaPlayer.release()
        }
    }

    sealed class Data:DataObject
    {

        abstract val name:String

        data class Uri(
                val uri:NormalizedUri)
            :Data()
        {
            override val name:String get()
            {
                return uri.uri.let {it.lastPathSegment ?: it.toString()}
            }
        }

        data class ResId(
                override val name:String,
                val rawResId:RawResId)
            :Data()
    }
}
