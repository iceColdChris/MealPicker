package com.cfahlin.mealpicker.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.cfahlin.mealpicker.di.Injector
import com.cfahlin.mealpicker.di.ScreenInjector
import com.cfahlin.mealpicker.ui.ScreenNavigator
import kotlinx.android.synthetic.main.activity_base.*
import java.util.*
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

//    @Inject lateinit var screenInjector: ScreenInjector
    @Inject lateinit var screenNavigator: ScreenNavigator

    var instanceID: String = ""
    var router: Router? = null

    companion object {
        private const val INSTANCE_ID_KEY = "instance_id"
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        instanceID = if(savedInstanceState != null)
            savedInstanceState.getString(INSTANCE_ID_KEY)
        else
            UUID.randomUUID().toString()

        Injector.inject(this)
        setContentView(layoutRes())

        val screenContainer: ViewGroup =
                screen_container ?: throw NullPointerException("Activity must have a view with id: screen_container")

        router = Conductor.attachRouter(this, screenContainer, savedInstanceState)
        screenNavigator.initWithRouter(router!!, initialScreen())

        monitorBackstack()
        super.onCreate(savedInstanceState)
    }


    abstract fun initialScreen(): Controller

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INSTANCE_ID_KEY, instanceID)
    }

    override fun onBackPressed() {
        if(!screenNavigator.pop())
            super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        screenNavigator.clear()
        if(isFinishing)
            Injector.clearComponent(this)
    }

    private fun monitorBackstack() {
        router?.addChangeListener(object : ControllerChangeHandler.ControllerChangeListener {
            override fun onChangeStarted(to: Controller?,
                                         from: Controller?,
                                         isPush: Boolean,
                                         container: ViewGroup,
                                         handler: ControllerChangeHandler) {

            }

            override fun onChangeCompleted(to: Controller?,
                                           from: Controller?,
                                           isPush: Boolean,
                                           container: ViewGroup,
                                           handler: ControllerChangeHandler) {
                if (!isPush && from != null)
                    Injector.clearComponent(from)

            }
        })
    }

}