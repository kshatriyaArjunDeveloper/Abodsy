package com.abodsy.abodsy.Main_App;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.abodsy.abodsy.book_maid_BottomSheetFragment;
import com.abodsy.abodsy.R;
import com.abodsy.abodsy.bottomSheets_book.book_cook_BottomSheetFragment;
import com.abodsy.abodsy.bottomSheets_book.book_cook_BottomSheetFragment_mt;
import com.abodsy.abodsy.bottomSheets_book.book_maid_BottomSheetFragmentbook_mt;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button button_bookMaid_ot;
    Button button_bookCook_ot;
    Button button_bookMaid_mt;
    Button button_bookCook_mt;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // ASSIGNING BOOK BUTTON
        button_bookMaid_ot = rootView.findViewById(R.id.button_maid_ot);
        button_bookCook_ot = rootView.findViewById(R.id.button_cook_ot);
        button_bookMaid_mt = rootView.findViewById(R.id.button_maid_mt);
        button_bookCook_mt = rootView.findViewById(R.id.button_cook_mt);
        // ON CLICK HANDLER
        button_bookMaid_ot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_bookMaid();
            }
        });
        button_bookCook_ot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_bookCook();
            }
        });button_bookMaid_mt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_bookMaid_mt();
            }
        });
        button_bookCook_mt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_bookCook_mt();
            }
        });


        /* LAYOUT FOR FRAGMENT */
        return rootView;
    }

    private void button_bookCook_mt() {

        book_cook_BottomSheetFragment_mt book_cook_BottomSheetFragment = new book_cook_BottomSheetFragment_mt();
        book_cook_BottomSheetFragment.show(getFragmentManager(), book_cook_BottomSheetFragment.getTag());
    }

    private void button_bookMaid_mt() {
        book_maid_BottomSheetFragmentbook_mt bookmaidBottomSheetFragment = new book_maid_BottomSheetFragmentbook_mt();
        bookmaidBottomSheetFragment.show(getFragmentManager(), bookmaidBottomSheetFragment.getTag());
    }

    private void button_bookMaid(){

        book_maid_BottomSheetFragment bookmaidBottomSheetFragment = new book_maid_BottomSheetFragment();
        bookmaidBottomSheetFragment.show(getFragmentManager(), bookmaidBottomSheetFragment.getTag());
    }

    private void button_bookCook(){

        book_cook_BottomSheetFragment book_cook_BottomSheetFragment = new book_cook_BottomSheetFragment();
        book_cook_BottomSheetFragment.show(getFragmentManager(), book_cook_BottomSheetFragment.getTag());
    }

}
