package keller.com.second_hand_car.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import keller.com.second_hand_car.R;


/*
 * @创建者     Administrator
 * @创建时间   2015/8/7 11:08
 * @描述	      联系人的Fragment
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}$
 */
public class ContactsFragment extends Fragment {

	private ListView		mListView;
	private CursorAdapter	mAdapter;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
    }

    private void init() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initData();
        initListener();
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {

    }

    private void initListener() {

    }

    private void initData() {
        //需要获取connection
        //得到花名册对象
       
    }
	

	
}
