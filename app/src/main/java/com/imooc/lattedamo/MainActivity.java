package com.imooc.lattedamo;

import com.imooc.core.activities.ProxyActivity;
import com.imooc.core.delegates.LatteDelegate;

/**
 * 主Activity+多Fragment
 */
public class MainActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }


}
