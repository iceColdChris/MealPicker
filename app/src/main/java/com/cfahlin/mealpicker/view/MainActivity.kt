package com.cfahlin.mealpicker.view

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.cfahlin.mealpicker.R
import com.cfahlin.mealpicker.R.id.action_map
import com.cfahlin.mealpicker.R.id.action_settings
import com.cfahlin.mealpicker.R.menu.top_menu
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.*
import java.security.SecureRandom


@RuntimePermissions
class MainActivity : AppCompatActivity() {

    private var foodList = emptyList<String>()
    private val random = SecureRandom()
    private var longitude: Double = 0.toDouble()
    private var latitude: Double = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        my_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(my_toolbar)


        decideButton.setOnClickListener {
            selectedFoodText.text = foodList[random.nextInt(foodList.count())]
        }

        registerLocationWithPermissionCheck()

    }

    override fun onStop() {
        super.onStop()
        SmartLocation.with(this).location().stop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null) {
            return when(item.itemId) {
                action_map -> {
                    val mapIntent = Intent(this, MapsActivity::class.java)
                    startActivity(mapIntent)
                    true
                }

                action_settings->{

                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }
        }
        return false
    }


    @NeedsPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET)
    fun registerLocation() {

        SmartLocation.with(this).location()
                .start({
                    longitude = it.longitude
                    latitude = it.latitude

                    Log.v("@@@@", latitude.toString())
                    Log.v("@@@@", longitude.toString())
                })

    }


    @OnPermissionDenied(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET)
    fun onLocationDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(this, R.string.permission_location_denied, Toast.LENGTH_SHORT).show()
        foodList = arrayListOf("Chinese", "Burgers", "Pizza", "Fast Food")
        SmartLocation.with(this).location().stop()
    }

    @OnShowRationale(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET)
    fun showRationaleForLocation(request: PermissionRequest) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        showRationaleDialog(R.string.permission_location_rationale, request)
    }

    @OnNeverAskAgain(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET)
    fun onLocationNeverAskAgain() {
        Toast.makeText(this, R.string.permission_location_never_ask_again, Toast.LENGTH_SHORT).show()
        foodList = arrayListOf("Chinese", "Burgers", "Pizza", "Fast Food")
        SmartLocation.with(this).location().stop()
    }

    private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow, DialogInterface.OnClickListener { _, _ -> request.proceed() })
                .setNegativeButton(R.string.button_deny, DialogInterface.OnClickListener { _, _ -> request.cancel() })
                .setCancelable(false)
                .setMessage(messageResId)
                .show()
    }
}