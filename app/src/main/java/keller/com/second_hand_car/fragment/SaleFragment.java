package keller.com.second_hand_car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import keller.com.second_hand_car.R;
import keller.com.second_hand_car.activity.SaleCarActivity;


/*
 * @创建者     Administrator
 * @创建时间   2015/8/7 11:08
 * @描述	      联系人的Fragment
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}$
 */
public class SaleFragment extends Fragment {

	private ListView		mListView;
	private CursorAdapter	mAdapter;
    private ImageView Msale;

    public SaleFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initView();
        initData();
        initListener();
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        Msale= (ImageView) getActivity().findViewById(R.id.Msale);

//        ImageUtil.loadIntoUseFitWidth(getActivity(),"",R.drawable.slacar,Msale);
//        Glide
//                .with(getActivity())
//                .load(R.drawable.slacar)
//                .into(Msale);

    }

    private void initListener() {
        Msale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SaleCarActivity.class);
                getActivity().startActivityForResult(intent,0);
            }
        });
    }

    private void initData() {
        //需要获取connection
        //得到花名册对象
       
    }
	

	
}
