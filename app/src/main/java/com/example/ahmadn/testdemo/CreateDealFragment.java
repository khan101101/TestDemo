package com.example.ahmadn.testdemo;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;


public class CreateDealFragment extends Fragment implements LocationListener {

    public TextView artNameText, descriptionText, location_txt_view, priceText;
    public int pic_id;
    private Button uploadDealBtn;
    private ImageView artPictureImg;
    private FloatingActionButton takePictureBtn;
    private Spinner dropDown;
    private View product_view;
    private FirebaseFirestore db;
    private LocationManager locationManager;
    private Location currentLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        product_view = (ConstraintLayout) inflater.inflate(R.layout.fragment_product, container, false);
        iniView(savedInstanceState);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return product_view;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                10, this);

        return product_view;
    }

    public void iniView(Bundle savedInstanceState) {
        artNameText = product_view.findViewById(R.id.art_name_text_id);
        descriptionText = product_view.findViewById(R.id.desciption_text_id);
        priceText = product_view.findViewById(R.id.price_text_id);
        uploadDealBtn = product_view.findViewById(R.id.button_create);
        artPictureImg = product_view.findViewById(R.id.product_image_id);
        takePictureBtn = product_view.findViewById(R.id.pic_float);

        dropDown = product_view.findViewById(R.id.spinner_category_id);
        Resources res = getResources();
        String[] categoryTypes = res.getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categoryTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dropDown.setAdapter(adapter);
        db = db.getInstance();
        dropDown.setSelection(0);


        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO take picture
            }
        });

        uploadDealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!allFieldsFilledIn()) {
                    return;
                }

                if (currentLocation == null) {
                    return;
                }

                GeoPoint geo = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Map<String, Object> deal = new HashMap<>();
                deal.put("email", user.getEmail());
                deal.put("title", artNameText.getText().toString());
                deal.put("category", dropDown.getSelectedItem().toString());
                deal.put("description", descriptionText.getText().toString());
                deal.put("price", Double.parseDouble(priceText.getText().toString()));
                deal.put("location", geo);
                db.collection("deals").add(deal);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,new MapViewFragment()).commit();
            }
        });


    }


    private boolean allFieldsFilledIn() {
        if(artNameText.getText().toString().isEmpty()) {
            Toast.makeText(product_view.getContext(),"Bitte geben Sie einen Artikel Namen ein!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (descriptionText.getText().toString().isEmpty()) {
            Toast.makeText(product_view.getContext(),"Bitte geben Sie eine Beschreibung ein!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (priceText.getText().toString().isEmpty()){
            Toast.makeText(product_view.getContext(),"Legen Sie bitte einen Preis fest", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getContext(), "GPS is disabled", Toast.LENGTH_SHORT).show();
    }

}

