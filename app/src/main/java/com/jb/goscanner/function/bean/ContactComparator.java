package com.jb.goscanner.function.bean;

import java.util.Comparator;

/**
 * Created by panruijie on 2017/9/10.
 * Email : zquprj@gmail.com
 */

public class ContactComparator<ContctBean> implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        com.jb.goscanner.function.bean.ContactBean bean1 = (com.jb.goscanner.function.bean.ContactBean) o1;
        com.jb.goscanner.function.bean.ContactBean bean2 = (com.jb.goscanner.function.bean.ContactBean) o2;
        int result = bean1.getFirstLetter().compareTo(bean2.getFirstLetter());
        if (result == 0) {
            return bean1.getName().compareTo(bean2.getName());
        } else {
            return result;
        }
    }
}
