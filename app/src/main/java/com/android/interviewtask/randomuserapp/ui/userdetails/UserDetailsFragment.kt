package com.android.interviewtask.randomuserapp.ui.userdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.interviewtask.randomuserapp.R
import com.android.interviewtask.randomuserapp.databinding.FragmentUserDetailsBinding
import com.android.interviewtask.randomuserapp.model.UserItem
import com.android.interviewtask.randomuserapp.utils.loadImagefromUrl
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

const val ARG_USERITEM = "useritem"

class UserDetailsFragment : Fragment(), OnMapReadyCallback {

    private var userItem: UserItem? = null
    private val binding by lazy{
        FragmentUserDetailsBinding.inflate(layoutInflater)
    }
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userItem = it.getSerializable(ARG_USERITEM) as UserItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userItem?.let {userItem ->
            binding.userName.text="${userItem.name.title}. ${userItem.name.first} ${userItem.name.last}"
            binding.userImage.loadImagefromUrl(userItem.picture.medium)
            binding.userEmail.text=userItem.email
            binding.userMobile.text=userItem.cell
            binding.userTelephone.text=userItem.phone
            binding.userId.text="User ID: "+userItem.id.value?: ""
            binding.userDob.text= "Age: ${userItem.dob.age} years"
            binding.userMember.text="Member Since: ${userItem.registered.age} years"
            binding.userUsername.text="Username: ${userItem.login.username}"
            binding.userAddress1.text="${userItem.location.street.number}, ${userItem.location.street.name}"
            binding.userAddress2.text="${userItem.location.city}, ${userItem.location.state}"
            binding.userAddress3.text="${userItem.location.country}, ${userItem.location.postcode}"
            binding.userAddress4.text="${userItem.location.timezone.description}"
        }
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled=true
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        userItem?.let{ item->
            val userlatlong = LatLng(item.location.coordinates.latitude.toDouble(), item.location.coordinates.longitude.toDouble())
            mMap.addMarker(MarkerOptions()
                .position(userlatlong)
                .title(item.location.city))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userlatlong,3f))
        }
    }

    override fun onResume() {
        super.onResume()
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}