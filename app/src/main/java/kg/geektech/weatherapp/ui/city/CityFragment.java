package kg.geektech.weatherapp.ui.city;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geektech.weatherapp.R;
import kg.geektech.weatherapp.base.BaseFragment;
import kg.geektech.weatherapp.databinding.FragmentCityBinding;
import kg.geektech.weatherapp.ui.weather.WeatherFragmentDirections;


@AndroidEntryPoint
public class CityFragment extends BaseFragment<FragmentCityBinding> implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private NavController controller;
    private GoogleMap googleMap;
    private MarkerOptions options;



    @Override
    protected FragmentCityBinding bind() {
        return FragmentCityBinding.inflate(getLayoutInflater());
    }



    @Override
    protected void callRequest() {
    }

    @Override
    protected void setupObservers() {
    }

    @Override
    protected void setupListeners() {
    }

    @Override
    protected void setupViews() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                options = new MarkerOptions();
                options.position(latLng);
                googleMap.clear();
                googleMap.addMarker(options);

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder()
                        .target(latLng)
                        .zoom(10F)
                        .bearing(23)
                        .build()));

            }
        });
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        controller = Navigation.findNavController(requireActivity(), R.id.nav_host);
        String optionsLatlng = String.valueOf(options.getPosition());
        controller.navigate((NavDirections) CityFragmentDirections.actionCityFragmentToWeatherFragment(optionsLatlng));
        return true;
    }
}