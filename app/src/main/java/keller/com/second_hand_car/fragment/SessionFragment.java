package keller.com.second_hand_car.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import keller.com.second_hand_car.R;


/*
 * @创建者     Administrator
 * @创建时间   2015/8/7 11:08
 * @描述	      会话的Fragment
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}$
 */
public class SessionFragment extends Fragment {


    public SessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_session, container, false);
    }

}
