package com.imooc.lattedamo;

import com.imooc.core.activities.ProxyActivity;
import com.imooc.core.delegates.LatteDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }


}
