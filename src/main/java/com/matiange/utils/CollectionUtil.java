package com.matiange.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author MaTianGe
 * @version 0.1
 * @Description: 集合工具类
 * @date 2020/8/10 14:00
 */
public class CollectionUtil {

    /**
     * 合并N个集合为一个ArrayList
     * @param collections 参与合并的集合类型
     * @return 合并好的ArrayList
     */
    @SafeVarargs// 本方法不会产生额外的元素并添加到参数/返回list中,所以这里并不存在类型安全问题
    public static <T> ArrayList<T> mergeCollections(Collection<T>... collections) {
        ArrayList<T> result = null;
        if (collections != null && collections.length > 0) {
            int capacity = 0;
            for (Collection<T> collection : collections) {
                capacity += (collection != null ? collection.size() : 0);
            }
            if(capacity!=0){
                result=new ArrayList<>(capacity);
                for (Collection<T> collection : collections) {
                    if(collection!=null && collection.size()>0){
                        result.addAll(collection);
                    }
                }
            }
        }

        return result;
    }
}
