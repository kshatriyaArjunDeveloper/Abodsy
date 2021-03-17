package com.abodsy.abodsy.Main_App;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abodsy.abodsy.Booking;
import com.abodsy.abodsy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import static com.abodsy.abodsy.LoginActivity.userId;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookingsFragment extends Fragment {

    RecyclerView rv_bookings;
    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter recyclerAdapter;




    public MyBookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        rv_bookings = rootView.findViewById(R.id.rv_bookings);


        /* SHOWING BOOKINGS*/

        init_bookingsAdapter();
        getBookings();

        return rootView;
    }

    private void init_bookingsAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        rv_bookings.setLayoutManager(linearLayoutManager);

    }


    private void getBookings() {



        Query query = FirebaseFirestore.getInstance().collection("USERS/" + userId + "/BOOKINGS")
                .orderBy("BOOKING_TIMESTAMP", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Booking> response = new FirestoreRecyclerOptions.Builder<Booking>()
                .setQuery(query, Booking.class)
                .build();

        recyclerAdapter = new FirestoreRecyclerAdapter<Booking, BookingsHolder>(response) {
            @Override
            public void onBindViewHolder(BookingsHolder holder, int position, Booking booking) {
                holder.typeOfService.setText(booking.getTYPE_OF_SERVICE());
                holder.serviceProviderName.setText(booking.getSERVICE_PROVIDER_NAME());
                holder.serviceProviderNumber.setText(booking.getSERVICE_PROVIDER_MOBILE());
                holder.bookingDate.setText(booking.getDATE());
                holder.bookingTime.setText(booking.getTIME());
                holder.bookingAddress.setText(booking.getADDRESS());
                holder.bookingMobileNo.setText(booking.getMOBILE());
                holder.serviceProviderRating.setText(booking.getSERVICE_PROVIDER_RATING());


            }

            @Override
            public BookingsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.booking_item, group, false);

                return new BookingsHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                rv_bookings.scrollToPosition(0);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        recyclerAdapter.notifyDataSetChanged();
        rv_bookings.setAdapter(recyclerAdapter);

    }

    public class BookingsHolder extends RecyclerView.ViewHolder {

        TextView typeOfService;
        TextView serviceProviderName;
        TextView serviceProviderNumber;
        TextView serviceProviderRating;
        TextView bookingDate;
        TextView bookingTime;
        TextView bookingAddress;
        TextView bookingMobileNo;


        public BookingsHolder(View itemView) {
            super(itemView);

            typeOfService = itemView.findViewById(R.id.TYPE_OF_SERVICE);
            serviceProviderName = itemView.findViewById(R.id.SERVICE_PROVIDER_NAME);
            serviceProviderNumber = itemView.findViewById(R.id.SERVICE_PROVIDER_MOBILE);
            serviceProviderRating = itemView.findViewById(R.id.SERVICE_PROVIDER_RATING);
            bookingDate = itemView.findViewById(R.id.DATE);
            bookingTime = itemView.findViewById(R.id.TIME);
            bookingAddress = itemView.findViewById(R.id.ADDRESS);
            bookingMobileNo = itemView.findViewById(R.id.MOBILE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

}
