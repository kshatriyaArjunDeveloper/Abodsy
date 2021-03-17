package com.abodsy.abodsy.Main_App;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abodsy.abodsy.LoginActivity;
import com.abodsy.abodsy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import static com.abodsy.abodsy.LoginActivity.userId;
import static com.abodsy.abodsy.LoginActivity.userName;
import static com.abodsy.abodsy.LoginActivity.userPhotoUrl;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    // Profile details
    ImageView imageView_profile;
    TextView textView_userName;
    TextView textView_userId;
    Button button_logout;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        // SET USER DETAILS INFO
        imageView_profile = rootView.findViewById(R.id.imageView_profile);
        textView_userName = rootView.findViewById(R.id.textView_userName);

        textView_userId = rootView.findViewById(R.id.textView_userId);
        textView_userName.setText(userName);
        textView_userId.setText(userId);

        button_logout = rootView.findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("LOGOUT", true);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
            }
        });

        Glide.with(this)
                .load(userPhotoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView_profile);

        return rootView;

    }

}