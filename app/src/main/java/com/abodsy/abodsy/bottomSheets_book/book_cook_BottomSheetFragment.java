package com.abodsy.abodsy.bottomSheets_book;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.abodsy.abodsy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.abodsy.abodsy.LoginActivity.userId;
import static com.abodsy.abodsy.Main_App.MainActivity.viewPager;
import static com.abodsy.abodsy.R.color.colorAccent;


/**
 * A simple {@link Fragment} subclass.
 */
public class book_cook_BottomSheetFragment extends BottomSheetDialogFragment {

    // FIREBASE RELATED VARIABLES
    DocumentReference documentReference;

    // LAYOUTS FOR ORDER DETAILS
    Button button_bookMaid;
    EditText editText_mobile;
    EditText editText_address;
    EditText editText_date;
    EditText editText_time;
    ProgressBar progressBar;
    Spinner spinner_foodCategory;
    Spinner persons;
    List<String> categories;
    List<Integer> persons_list;

    // FOR DATE AND TIME
    private int mYear, mMonth, mDay, mHour, mMinute;


    public book_cook_BottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_bottom_sheet_book_cook, container, false);


        // ASSIGNING ID'S TO LAYOUTS
        button_bookMaid = rootView.findViewById(R.id.button_bookMaid_c);
        editText_mobile = rootView.findViewById(R.id.editText_mobile_c);
        editText_address = rootView.findViewById(R.id.editText_address_c);
        editText_date = rootView.findViewById(R.id.editText_date_c);
        editText_time = rootView.findViewById(R.id.editText_time_c);
        progressBar = rootView.findViewById(R.id.progressBar_c);
        spinner_foodCategory = rootView.findViewById(R.id.spinner_foodCategory);
        persons = rootView.findViewById(R.id.spinner_persons);

        /*  SELECTING DATE AND TIME */
        editText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editText_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), colorAccent, null));
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ResourcesCompat.getColor(getResources(), colorAccent, null));
            }
        });

        editText_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String format;

                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }


                                editText_time.setText(hourOfDay + ":" + minute + "  " + format);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), colorAccent, null));
                timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ResourcesCompat.getColor(getResources(), colorAccent, null));
            }
        });


        // Spinner Drop down elements
        categories = new ArrayList<>();
        categories.add("Item 1");
        categories.add("Item 2");
        categories.add("Item 3");
        categories.add("Item 4");
        categories.add("Item 5");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_foodCategory.setAdapter(dataAdapter);

        /* FOR PERSONS*/

        // Spinner Drop down elements
        persons_list = new ArrayList<>();
        persons_list.add(1);
        persons_list.add(2);
        persons_list.add(3);
        persons_list.add(4);
        persons_list.add(5);
        persons_list.add(6);

        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter_person = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, persons_list);

        // Drop down layout style - list view with radio button
        dataAdapter_person.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        persons.setAdapter(dataAdapter_person);

        button_bookMaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book_cook();
            }
        });

        return rootView;
    }

    public void book_cook() {

        /* IF ALL DATA IS ENTERED CORRECTLY */
        if (checkData()) {

            progressBar.setVisibility(View.VISIBLE);

            FirebaseFirestore.getInstance().collection("USERS").document(userId)
                    .get().addOnCompleteListener(getActivity(), new OnCompleteListener<DocumentSnapshot>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        final DocumentSnapshot d = task.getResult();
                        if (d.exists()) {

                            if (d.contains("COOK_ORDER_NO")) {

                                // ADD ACCORDING TO ORDER NO.
                                /* ADDING ORDER */
                                final Map<String, Object> COOK_ORDER = new HashMap<>();
                                COOK_ORDER.put("MOBILE", editText_mobile.getText().toString());
                                COOK_ORDER.put("ADDRESS", editText_address.getText().toString());
                                COOK_ORDER.put("FOOD",spinner_foodCategory.getSelectedItem().toString());
                                COOK_ORDER.put("TOTAL_PERSON",persons.getSelectedItem());
                                COOK_ORDER.put("DATE", editText_date.getText().toString());
                                COOK_ORDER.put("TIME", editText_time.getText().toString());
                                COOK_ORDER.put("TYPE_OF_SERVICE", "COOK");

                                documentReference = FirebaseFirestore.getInstance().document
                                        ("USERS/" + userId + "/BOOKINGS/COOK_" + d.getLong("COOK_ORDER_NO"));
                                documentReference.set(COOK_ORDER).addOnFailureListener(getActivity(), new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(),
                                                "PROBLEM OCCURRED ", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                /* WHEN BOOKED SUCCESSFULLY*/

                                                // INCREASING ORDER NO. OF MAID_ORDER

                                                // CONNECT TO A MAID == ADD BOOKED MAID DATA

                                                FirebaseFirestore.getInstance().document("COOKS/COOK_INFORMATION").get()
                                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    DocumentSnapshot document = task.getResult();
                                                                    if (document.exists()) {

                                                                        /* SELECTING RANDOM MAID */
                                                                        int random_maid = new Random().nextInt(document.getLong("TOTAL_COOKS").intValue()) + 1;
                                                                        Toast.makeText(getActivity(), " " + random_maid, Toast.LENGTH_SHORT).show();

                                                                        /* READING DATA OF SELECTED MAID */
                                                                        FirebaseFirestore.getInstance().collection("COOKS")
                                                                                .whereEqualTo("COOK_NO", random_maid)
                                                                                .get()
                                                                                .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            for (QueryDocumentSnapshot document : task.getResult()) {


                                                                                                /* DATA OF SELECTED COOK */
                                                                                                Map<String, Object> BOOKED_COOK = new HashMap<>();
                                                                                                BOOKED_COOK.put("SERVICE_PROVIDER_MOBILE", document.get("MOBILE"));
                                                                                                BOOKED_COOK.put("SERVICE_PROVIDER_NAME", document.get("NAME"));
                                                                                                BOOKED_COOK.put("SERVICE_PROVIDER_RATING", document.get("RATING"));
                                                                                                BOOKED_COOK.put("BOOKING_TIMESTAMP", FieldValue.serverTimestamp());

                                                                                                /* ADDING MAID DATA TO BOOKING DATA */
                                                                                                FirebaseFirestore.getInstance().document("USERS/" + userId + "/BOOKINGS/COOK_" + d.getLong("COOK_ORDER_NO"))
                                                                                                        .set(BOOKED_COOK, SetOptions.merge())
                                                                                                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(Void aVoid) {

                                                                                                                /* BOOKING COMPLETED*/
                                                                                                                FirebaseFirestore.getInstance().collection("USERS").document(userId)
                                                                                                                        .update("COOK_ORDER_NO", FieldValue.increment(1));
                                                                                                                Toast.makeText(getActivity(), "BOOKED", Toast.LENGTH_SHORT).show();
                                                                                                                progressBar.setVisibility(View.GONE);
                                                                                                                viewPager.setCurrentItem(1);
                                                                                                                dismiss();


                                                                                                                Log.d("GET", "DocumentSnapshot successfully written!");
                                                                                                            }
                                                                                                        })
                                                                                                        .addOnFailureListener(getActivity(), new OnFailureListener() {
                                                                                                            @Override
                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                Log.w("GET", "Error writing document", e);
                                                                                                            }
                                                                                                        });

                                                                                                Log.d("GET", document.getId() + " => " + document.getData());
                                                                                            }
                                                                                        } else {
                                                                                            Log.d("GET", "Error getting documents: ", task.getException());
                                                                                        }
                                                                                    }
                                                                                });


                                                                        Log.d("GET", "DocumentSnapshot data: " + document.getData());
                                                                    } else {
                                                                        Log.d("GET", "No such document");
                                                                    }
                                                                } else {
                                                                    Log.d("GET", "get failed with ", task.getException());
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                            }

                        } else {

                            Map<String, Object> USER_ID = new HashMap<>();
                            USER_ID.put("COOK_ORDER_NO", 0);
                            USER_ID.put("MAID_ORDER_NO", 0);


                            FirebaseFirestore.getInstance().collection("USERS").document(userId)
                                    .set(USER_ID)
                                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            // ADD ACCORDING TO ORDER NO.
                                            /* ADDING ORDER */
                                            final Map<String, Object> COOK_ORDER = new HashMap<>();
                                            COOK_ORDER.put("MOBILE", editText_mobile.getText().toString());
                                            COOK_ORDER.put("ADDRESS", editText_address.getText().toString());
                                            COOK_ORDER.put("FOOD",spinner_foodCategory.getSelectedItem().toString());
                                            COOK_ORDER.put("TOTAL_PERSON",persons.getSelectedItem());
                                            COOK_ORDER.put("DATE", editText_date.getText().toString());
                                            COOK_ORDER.put("TIME", editText_time.getText().toString());
                                            COOK_ORDER.put("TYPE_OF_SERVICE", "COOK");

                                            documentReference = FirebaseFirestore.getInstance().document
                                                    ("USERS/" + userId + "/BOOKINGS/COOK_0");
                                            documentReference.set(COOK_ORDER).addOnFailureListener(getActivity(), new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getActivity(),
                                                            "PROBLEM OCCURRED ADDING HCM", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    /* WHEN BOOKED SUCCESSFULLY*/

                                                    // INCREASING ORDER NO. OF COOK_ORDER

                                                    // CONNECT TO A COOK == ADD BOOKED COOK DATA

                                                    FirebaseFirestore.getInstance().document("COOKS/COOK_INFORMATION").get()
                                                            .addOnCompleteListener(getActivity(), new OnCompleteListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        DocumentSnapshot document = task.getResult();
                                                                        if (document.exists()) {

                                                                            /* SELECTING RANDOM MAID */
                                                                            int random_COOK = new Random().nextInt(document.getLong("TOTAL_COOKS").intValue()) + 1;
                                                                            Toast.makeText(getActivity(), " " + random_COOK, Toast.LENGTH_SHORT).show();

                                                                            /* READING DATA OF SELECTED COOK */
                                                                            FirebaseFirestore.getInstance().collection("COOKS")
                                                                                    .whereEqualTo("COOK_NO", random_COOK)
                                                                                    .get()
                                                                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                for (QueryDocumentSnapshot document : task.getResult()) {


                                                                                                    /* DATA OF SELECTED MAID */
                                                                                                    Map<String, Object> BOOKED_COOK = new HashMap<>();
                                                                                                    BOOKED_COOK.put("SERVICE_PROVIDER_MOBILE", document.get("MOBILE"));
                                                                                                    BOOKED_COOK.put("SERVICE_PROVIDER_NAME", document.get("NAME"));
                                                                                                    BOOKED_COOK.put("SERVICE_PROVIDER_RATING", document.get("RATING"));
                                                                                                    BOOKED_COOK.put("BOOKING_TIMESTAMP", FieldValue.serverTimestamp());

                                                                                                    /* ADDING MAID DATA TO BOOKING DATA */
                                                                                                    FirebaseFirestore.getInstance().document("USERS/" + userId + "/BOOKINGS/COOK_0")
                                                                                                            .set(BOOKED_COOK, SetOptions.merge())
                                                                                                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(Void aVoid) {

                                                                                                                    /* BOOKING COMPLETED*/
                                                                                                                    FirebaseFirestore.getInstance().collection("USERS").document(userId)
                                                                                                                            .update("COOK_ORDER_NO", FieldValue.increment(1));

                                                                                                                    Toast.makeText(getActivity(), "BOOKED", Toast.LENGTH_SHORT).show();
                                                                                                                    progressBar.setVisibility(View.GONE);
                                                                                                                    viewPager.setCurrentItem(1);
                                                                                                                    dismiss();


                                                                                                                    Log.d("GET", "DocumentSnapshot successfully written!");
                                                                                                                }
                                                                                                            })
                                                                                                            .addOnFailureListener(getActivity(), new OnFailureListener() {
                                                                                                                @Override
                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                    Log.w("GET", "Error writing document", e);
                                                                                                                }
                                                                                                            });

                                                                                                    Log.d("GET", document.getId() + " => " + document.getData());
                                                                                                }
                                                                                            } else {
                                                                                                Log.d("GET", "Error getting documents: ", task.getException());
                                                                                            }
                                                                                        }
                                                                                    });


                                                                            Log.d("GET", "DocumentSnapshot data: " + document.getData());
                                                                        } else {
                                                                            Log.d("GET", "No such document");
                                                                        }
                                                                    } else {
                                                                        Log.d("GET", "get failed with ", task.getException());
                                                                    }
                                                                }
                                                            });
                                                }
                                            });
                                            Log.d("GET", "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(getActivity(), new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("GET", "Error writing document", e);
                                        }
                                    });
                        }

                    } else {
                        Log.d("GET", "get failed with ", task.getException());
                    }
                }
            });
        }

    }

    public boolean checkData() {

        /* RETURN FALSE WHEN ANY FIELD IS ENTERED WRONG*/
        if (spinner_foodCategory == null || spinner_foodCategory.getSelectedItem() == null) {

            Toast.makeText(getActivity(), "SELECT FOOD CATEGORY", Toast.LENGTH_SHORT).show();
            return false;
        } else if (persons == null || persons.getSelectedItem() == null) {

            Toast.makeText(getActivity(), "SELECT NO. OF PERSONS", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editText_date.getText().toString().isEmpty() || (editText_date.getText().toString() == null)) {

            Toast.makeText(getActivity(), "SELECT DATE", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editText_time.getText().toString().isEmpty() || (editText_time.getText().toString() == null)) {

            Toast.makeText(getActivity(), "SELECT TIME", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editText_mobile.getText().toString().isEmpty() || (editText_mobile.getText().toString() == null)) {

            Toast.makeText(getActivity(), "ENTER MOBILE NUMBER", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editText_address.getText().toString().isEmpty() || (editText_address.getText().toString() == null)) {

            Toast.makeText(getActivity(), "ENTER ADDRESS", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}
