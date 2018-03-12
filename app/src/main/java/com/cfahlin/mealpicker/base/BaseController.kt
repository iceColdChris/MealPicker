package com.cfahlin.mealpicker.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.Unbinder
import com.bluelinelabs.conductor.Controller
import com.cfahlin.mealpicker.di.Injector
import io.reactivex.disposables.CompositeDisposable
import android.support.annotation.LayoutRes
import butterknife.ButterKnife
import io.reactivex.disposables.Disposable


abstract class BaseController(bundle: Bundle) : Controller(bundle) {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private var injected: Boolean = false
    private var unbinder: Unbinder? = null

    override fun onContextAvailable(context: Context) {
        if(!injected) {
            Injector.inject(this)
            injected = true
        }
        super.onContextAvailable(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view: View = inflater.inflate(layoutRes(), container,false)
        unbinder = ButterKnife.bind(this, view)
        onViewBound(view)
        disposables.addAll(*subscriptions())
        return view
    }

    @LayoutRes
    protected abstract fun layoutRes(): Int

    abstract fun onViewBound(view: View)

    protected fun subscriptions(): Array<Disposable> {
        return emptyArray()
    }
}