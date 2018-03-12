package com.cfahlin.mealpicker.ui

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import javax.inject.Inject


class DefaultScreenNavigator @Inject constructor(): ScreenNavigator{

    var router: Router? = null

    override fun initWithRouter(router: Router, controller: Controller) {
        this.router = router
        if(!router.hasRootController())
            router.setRoot(RouterTransaction.with(controller))
    }

    override fun pop(): Boolean {
        return router != null && router!!.handleBack()
    }

    override fun clear() {
        router = null
    }
}