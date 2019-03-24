package com.github.ericytsang.androidlib.cannotopenlinkdialog

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.github.ericytsang.androidlib.core.activity.ActivityIntent
import com.github.ericytsang.androidlib.core.activity.ContextCompanionWithStart
import com.github.ericytsang.androidlib.core.clipboardManager
import com.github.ericytsang.androidlib.core.getStringCompat
import com.github.ericytsang.androidlib.core.layoutInflater
import com.github.ericytsang.lib.optional.Opt
import com.github.ericytsang.lib.prop.RaiiProp
import com.github.ericytsang.lib.prop.value
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity__confirm_dialog.*
import kotlinx.android.synthetic.main.notification_media_action.action0
import java.io.Closeable
import java.io.Serializable

class CannotOpenLinkActivity:AppCompatActivity()
{
    companion object:ContextCompanionWithStart<CannotOpenLinkActivity,Params>(ActivityIntent.FACTORY)
    {
        override val contextClass get() = CannotOpenLinkActivity::class.java

        fun tryOpenLink(
                context:Context,
                link:String,
                extraFlags:Int = 0,
                title:String? = null,
                linkLabel:String? = null,
                exceptionHandler:(Throwable)->Unit = {})
        {
            try
            {
                context.startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(link)))
            }
            catch (e:Throwable)
            {
                exceptionHandler(e)
                CannotOpenLinkActivity.start(
                        context,
                        CannotOpenLinkActivity.Params(
                                title,
                                linkLabel,
                                link,
                                exceptionHandler),
                        extraFlags)
            }
        }
    }

    // Params

    data class Params(
            val title:String?,
            val linkLabel:String?,
            val link:String,
            val exceptionHandler:(Throwable)->Unit = {})
        :Serializable

    // Created

    private val created = RaiiProp(Opt.of<Created>())

    override fun onCreate(savedInstanceState:Bundle?)
    {
        super.onCreate(savedInstanceState)
        created.value = {Opt.of(Created(this,fromIntent(intent)))}
    }

    override fun onDestroy()
    {
        created.close()
        super.onDestroy()
    }

    class Created(
            val activity:CannotOpenLinkActivity,
            val params:Params)
        :Closeable
    {
        val layout = Layout(activity.findViewById(android.R.id.content))
                .apply {activity.setContentView(containerView)}

        init
        {
            activity.setContentView(layout.containerView)
            activity.title = params.title?:activity.getStringCompat(R.string.activity__cannot_open_link__title)
            layout.textview.text = params.link
            val linkLabel = params.linkLabel?:activity.getStringCompat(R.string.activity__cannot_open_link__clipboard_label)
            layout.button__retry.setOnClickListener()
            {
                try
                {
                    activity.startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(params.link)))
                    activity.finish()
                }
                catch (e:Throwable)
                {
                    params.exceptionHandler(e)
                }
            }
            layout.button__copy.setOnClickListener()
            {
                activity.clipboardManager.primaryClip = ClipData.newPlainText(
                        linkLabel,
                        params.link)
                Toast.makeText(
                        activity,
                        activity.getStringCompat(
                                R.string.activity__cannot_open_link__clipboard_copied,
                                params.link),
                        Toast.LENGTH_LONG).show()
            }
            layout.button__share.setOnClickListener()
            {
                ShareCompat.IntentBuilder.from(activity)
                        .setType("text/plain")
                        .setSubject(linkLabel)
                        .setText(params.link)
                        .startChooser()
            }
        }

        override fun close() = Unit
    }

    // Resumed

    override fun onPause()
    {
        super.onPause()
        finish()
    }

    // Layout

    class Layout(val parent:ViewGroup):LayoutContainer
    {
        override val containerView = parent.context.layoutInflater.inflate(R.layout.activity__confirm_dialog,parent,false)
    }
}
