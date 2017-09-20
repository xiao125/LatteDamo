package com.latte.ui.refresh;

/**
 *
 * Created by Administrator on 2017/9/18 0018.
 */

public  final class PagingBean {
    //当前是第几页
    private int mPageIndex = 0;
    //总数据条数
    private int mTotal = 0;
    //一页显示几条数据
    private int mPageSize = 0;
    //当前已经显示了几条数据
    private int mCurrentCount = 0;
    //加载延迟
    private int mDelayed = 0;

    public int getmPageIndex(){

        return mPageIndex;
    }

    public PagingBean setPageIndex(int mPageIndex){

        this.mPageIndex = mPageIndex;
        return this;
    }

    public int getmTotal(){
        return mTotal;
    }

    public PagingBean setTotal(int mTotal){
        this.mTotal = mTotal;
        return this;
    }
    public int getPageSize() {
        return mPageSize;
    }

    public PagingBean setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this;
    }

    public int getDelayed() {
        return mDelayed;
    }

    public PagingBean setDelayed(int mDelayed) {
        this.mDelayed = mDelayed;
        return this;
    }

    PagingBean addIndex(){ //第一次加载后，就是下一页了
        mPageIndex++;
        return this;
    }

}
