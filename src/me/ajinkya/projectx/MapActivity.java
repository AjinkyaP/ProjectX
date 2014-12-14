package me.ajinkya.projectx;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends ActionBarActivity implements LocationListener,
    OnInfoWindowClickListener, OnMarkerClickListener {

  // Google Map
  private GoogleMap googleMap;
  private LatLng userCurrentLocation = null;
  private Marker userMarker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    // Getting Google Play availability status
    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

    // Showing status
    if (status != ConnectionResult.SUCCESS) { // Google Play Services are
      // not available, show error, what else I can do????
      int requestCode = 10;
      Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
      dialog.show();

    } else { 
      // Google Play Services are available Hurrray!!!!
      initilizeMap();
      Location location = getUserLocation();
      addUserToMap(location);
      addAutosOnMap();
      Toast.makeText(getApplicationContext(), "Click on auto to get driver details.",
          Toast.LENGTH_LONG).show();
    }
  }

  /**
   * function to load map. If map is not created it will create it for you
   * */
  private void initilizeMap() {
    // Getting reference to the SupportMapFragment of activity_main.xml
    SupportMapFragment fm =
        (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

    // Getting GoogleMap object from the fragment
    googleMap = fm.getMap();
    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    // Remove some unwanted things from UI
    googleMap.setMyLocationEnabled(false);
    googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    googleMap.getUiSettings().setZoomControlsEnabled(true);
    
    // marker and InfoWindow click listeners
    googleMap.setOnMarkerClickListener(this);
    googleMap.setOnInfoWindowClickListener(this);
  }

  /**
   * Method to get users current location
   */
  private Location getUserLocation() {
    // Getting LocationManager object from System Service
    // LOCATION_SERVICE
    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    // using network for now, will add GPS later
    // Creating a criteria object to retrieve provider
    // Criteria criteria = new Criteria();

    // Getting the name of the best provider
    // String provider = locationManager.getBestProvider(criteria, true);

    // Getting Current Location
    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15, 0, this);
    return location;
  }

  /**
   * Method to add marker to show users current location
   * @param location
   */
  public void addUserToMap(Location location) {
    // Getting latitude of the current location
    double latitude = location.getLatitude();

    // Getting longitude of the current location
    double longitude = location.getLongitude();

    // Creating a LatLng object for the current location
    userCurrentLocation = new LatLng(latitude, longitude);

    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(userCurrentLocation);
    markerOptions.title("You are here.");
    userMarker = googleMap.addMarker(markerOptions);
    userMarker.showInfoWindow();
    userMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.user));

    // Showing the current location in Google Map
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(userCurrentLocation));

    // Zoom in the Google Map
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

  }

  /**
   * Method to add markers to indicate nearby autos
   */
  private void addAutosOnMap() {
    // Getting latitude of the current location
    double latitude = userCurrentLocation.latitude;

    // Getting longitude of the current location
    double longitude = userCurrentLocation.longitude;

    // All Hard coded for now
    addAutoToMap(latitude + 0.003, longitude);
    addAutoToMap(latitude, longitude + 0.009);
    addAutoToMap(latitude + 0.01, longitude + 0.01);
  }

  /**
   * method to add auto marker to given location
   * @param latitude
   * @param longitude
   */
  private void addAutoToMap(double latitude, double longitude) {
    // Creating a LatLng object for the current location
    LatLng auto = new LatLng(latitude, longitude);

    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(auto);
    markerOptions.title("Umesh, 9975176444");
    Marker marker = googleMap.addMarker(markerOptions);
    marker.showInfoWindow();
    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.auto));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.map, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Listener to update user location if location changes
   */
  @Override
  public void onLocationChanged(Location location) {
    // Getting latitude of the current location
    double latitude = location.getLatitude();

    // Getting longitude of the current location
    double longitude = location.getLongitude();

    // Creating a LatLng object for the current location
    userCurrentLocation = new LatLng(latitude, longitude);

    googleMap.addMarker(new MarkerOptions().position(userCurrentLocation));

    // Showing the current location in Google Map
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(userCurrentLocation));

    // Zoom in the Google Map
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

  }

  @Override
  public void onProviderDisabled(String provider) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onProviderEnabled(String provider) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }

  /**
   * Showing auto details on click of marker
   */
  @Override
  public boolean onMarkerClick(Marker marker) {
    if (marker != userMarker) {
      marker.showInfoWindow();
    }
    return false;
  }

  /**
   * Make a call on click of info window
   */
  @Override
  public void onInfoWindowClick(Marker marker) {
    if (marker != userMarker) {
      Intent callIntent = new Intent(Intent.ACTION_CALL);
      callIntent.setData(Uri.parse("tel:9975176444"));
      startActivity(callIntent);
    }
  }
}
