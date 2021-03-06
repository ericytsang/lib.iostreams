package com.github.ericytsang.androidlib.core.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ericytsang.androidlib.core.context.wrap
import com.github.ericytsang.androidlib.core.intent.StartableIntent.StartableForResultIntent
import java.io.Serializable

class IntentLauncherForResultActivity:AppCompatActivity()
{
    companion object:ActivityWithResultCompanion<IntentLauncherForResultActivity,Params,Serializable>()
    {
        private const val THE_REQUEST_CODE = 2782
        override val contextClass:Class<IntentLauncherForResultActivity> get() = IntentLauncherForResultActivity::class.java
        override fun getFlagsForIntent(params:Params):Set<Int>
        {
            return setOf(
                    Intent.FLAG_ACTIVITY_NO_HISTORY,
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        }
    }

    class Params
    constructor(
            val intent:StartableForResultIntent)
        :Serializable

    override fun onCreate(savedInstanceState:Bundle?)
    {
        // parse parameters
        val params = fromIntent(intent)

        // call super
        super.onCreate(savedInstanceState)

        // start the intents
        params.intent.start(wrap(THE_REQUEST_CODE))
    }

    override fun onActivityResult(requestCode:Int,resultCode:Int,data:Intent?)
    {
        if (requestCode == THE_REQUEST_CODE)
        {
            setResult(resultCode,data)
        }
        else
        {
            super.onActivityResult(requestCode,resultCode,data)
        }
        finish()
    }
}
