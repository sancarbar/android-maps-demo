package mapsdemo.gdg.co.android;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class MapsActivity
    extends FragmentActivity
    implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    private static final int ACCESS_LOCATION_PERMISSION_CODE = 10;

    private GoogleMap googleMap;

    public static boolean hasPermissions( Context context, String[] permissions )
    {
        for ( String permission : permissions )
        {
            if ( ContextCompat.checkSelfPermission( context, permission ) == PackageManager.PERMISSION_DENIED )
            {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions( Activity activity, String[] permissions, int requestCode )
    {
        ActivityCompat.requestPermissions( activity, permissions, requestCode );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady( GoogleMap googleMap )
    {
        this.googleMap = googleMap;
        showMyLocation();
    }

    @SuppressWarnings( "MissingPermission" )
    public void showMyLocation()
    {
        if ( googleMap != null )
        {
            String[] permissions = { android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION };
            if ( hasPermissions( this, permissions ) )
            {
                googleMap.setMyLocationEnabled( true );
            }
            else
            {
                requestPermissions( this, permissions, ACCESS_LOCATION_PERMISSION_CODE );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions,
                                            @NonNull int[] grantResults )
    {
        for ( int grantResult : grantResults )
        {
            if ( grantResult == -1 )
            {
                return;
            }
        }
        switch ( requestCode )
        {
            case ACCESS_LOCATION_PERMISSION_CODE:
                showMyLocation();
                break;
            default:
                super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        }
    }

    @Override
    public void onConnected( @Nullable Bundle bundle )
    {

    }

    @Override
    public void onConnectionSuspended( int i )
    {

    }

    @Override
    public void onConnectionFailed( @NonNull ConnectionResult connectionResult )
    {

    }

}
