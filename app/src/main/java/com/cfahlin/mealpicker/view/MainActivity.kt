package com.cfahlin.mealpicker.view

import android.Manifest
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.cfahlin.mealpicker.R
import dagger.android.DaggerActivity
import io.nlopez.smartlocation.SmartLocation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import permissions.dispatcher.*
import timber.log.Timber
import java.security.SecureRandom
import javax.inject.Inject

@RuntimePermissions
class MainActivity: DaggerActivity() {

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    @BindView(R.id.selectedFoodText) lateinit var selectedFood : TextView

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val random: SecureRandom = SecureRandom()
    private var unbinder: Unbinder? = null

    private var placesList: MutableList<String> = arrayListOf("Chinese", "Fast Food", "Mexican")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationsWithPermissionCheck()
        setContentView(R.layout.activity_main)

        SmartLocation.with(this).location().start({
            val latlong = "${it.latitude},${it.longitude}"
            compositeDisposable.add(mainActivityViewModel.showDataFromApi(latlong, "8047")
                    .subscribeBy(onSuccess = {
                        placesList.clear()
                        it.results
                                .filter { it.opening_hours.open_now }
                                .forEach { placesList.add(it.name) }
                    }, onError = {

                        Timber.e(it, "Failed to load nearby places!")
                    }))
        })

        unbinder = ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        compositeDisposable.dispose()

        if(unbinder != null)
            unbinder!!.unbind()
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
    fun getLocations() {}


    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onLocationDenied() {
        Toast.makeText(this, R.string.permission_location_denied, Toast.LENGTH_SHORT).show()
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
    fun showRationaleForLocation(request: PermissionRequest) {
        showRationaleDialog(R.string.permission_location_rationale, request)
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onLocationNeverAskAgain() {
        Toast.makeText(this, R.string.permission_location_never_ask_again, Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.decideButton)
    fun decidePlace() {
        selectedFood.text = placesList[random.nextInt(placesList.size)]
    }


    private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow) { _, _ -> request.proceed() }
                .setNegativeButton(R.string.button_deny) { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage(messageResId)
                .show()
    }

}