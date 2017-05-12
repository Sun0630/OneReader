package com.sx.onereader.todayofhistory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sx.onereader.R;
import com.sx.onereader.todayofhistory.adapter.TodayOfHistoryAdapter;
import com.sx.onereader.todayofhistory.bean.TodayOfHistoryBean;
import com.sx.onereader.todayofhistory.contract.TodayOfHistoryContract;
import com.sx.onereader.utils.ThemeUtils;
import com.sx.onereader.utils.interfaze.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by sunxin on 2017/3/14.
 */
public class TodayOfHistoryFragment extends Fragment implements TodayOfHistoryContract.View{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TodayOfHistoryContract.Presenter presenter;
    private TodayOfHistoryAdapter todayOfHistoryAdapter;

    public static TodayOfHistoryFragment newInstance(){
        return new TodayOfHistoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ThemeUtils.changeTheme(getActivity());
        View view = inflater.inflate(R.layout.fragment_content,container,false);
        initView(view);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        presenter.start();
        return view;
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showError() {
        Snackbar.make(mSwipeRefreshLayout, "加载失败",Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.refresh();
                    }
                })
                .show();
    }

    @Override
    public void showResult(List<TodayOfHistoryBean.ResultBean> list) {
        if(todayOfHistoryAdapter==null){
                todayOfHistoryAdapter = new TodayOfHistoryAdapter(getContext(),list);

                todayOfHistoryAdapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        presenter.loadDetail(position);
                    }
                });
            mRecyclerView.setAdapter(todayOfHistoryAdapter);
        }else{
            todayOfHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(TodayOfHistoryContract.Presenter presenter) {
        if(presenter!=null){
            this.presenter = presenter;
        }
    }

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_content_swiperefreshlayout);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_content_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(
//                getActivity()
//        ));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }
}
