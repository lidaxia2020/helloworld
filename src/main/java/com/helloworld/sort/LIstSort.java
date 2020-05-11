package com.helloworld.sort;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LIstSort {
    public static void main(String[] args) {
        Long a = 0L;
        System.out.println(a == 0L);
        System.out.println(a.equals(0L));
        List<Data> dataList = new ArrayList<>();
        dataList.add(new Data(1137L, 0L));
//        dataList.add(new Data(1137L, 0L));
//        dataList.add(new Data(1134L, 0L));
        dataList.add(new Data(1134L, 0L));
        dataList.add(new Data(1139L, 1137L));
        dataList.add(new Data(1138L, 1137L));
        dataList.add(new Data(1141L, 1134L));
        dataList.add(new Data(1140L, 1137L));
        dataList.add(new Data(1157L, 0L));
//        dataList.add(new Data(18L, 19L));

//        dataList.sort(Comparator.comparing(Data::getPid).thenComparing(Data::getId));
//        //组--list Child data
//        Map<Long, List<Data>> group = dataList.stream().collect(Collectors.groupingBy(Data::getPid));
//        group.forEach((k, v) -> {
////            System.out.println(k + "=" + v.stream().map(t -> String.valueOf(t.getId())).collect(Collectors.joining(",")));
//        });
//        //id --data
//        Map<Long, Data> collect = dataList.stream().collect(Collectors.toMap(Data::getId, Function.identity()));
//        LinkedHashSet<Long> sortedList = new LinkedHashSet<>();
//        Set<Long> longs = group.keySet();
//        longs.stream().sorted().forEach(aLong -> {
//            Data x = collect.get(aLong);
//            if (x != null) {
//                printGroup(group, x, sortedList);
//            } else {
//                System.out.println("====\t" + aLong);
//            }
//        });
//        System.out.println(sortedList);
//    }
//    private static void printGroup(Map<Long, List<Data>> group, Data data, LinkedHashSet<Long> result) {
//        Long id = data.getId();
//        List<Data> childList = group.get(id);
//        result.add(id);
//        if (childList != null) {
//            childList.sort(Comparator.comparing(Data::getId));
//            for (Data child : childList) {
//                printGroup(group, child, result);
//            }
//        }
//    }
        JSONObject jsonObject = new JSONObject();

        Integer integer = new Integer(1);

        test(integer);

        Map map = dataList.stream().collect(Collectors.toMap(data -> {

            return data.getId();
        }, Function.identity()));


        dataList.sort(Comparator.comparing(Data::getPid).thenComparing(Data::getId));
        Map<Long, List<Data>> group = dataList.stream().collect(Collectors.groupingBy(Data::getPid));
        LinkedHashSet<Long> sortedList = new LinkedHashSet<>();
        Set<Long> longs = group.keySet();
        longs.stream().sorted().forEach(aLong -> {
            printChild(group, aLong, sortedList);
        });
        System.out.println(sortedList);
    }

    private static void printChild(Map<Long, List<Data>> group, Long pid, LinkedHashSet<Long> result) {
        if (pid != null && pid != 0L) {
            result.add(pid);
        }
        List<Data> child = group.get(pid);
        if (child != null && !child.isEmpty()) {
            child.sort(Comparator.comparing(Data::getId));
            for (Data data1 : child) {
                printChild(group, data1.getId(), result);
            }
        }
    }

    static void test(Integer integer){
        //jsonObject.put("aaa", 1);
        integer = 3;
    }
}


class Data {
    private Long id;
    private Long pid;

    public Data(Long id, Long pid) {
        this.id = id;
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", pid=" + pid +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        if (pid == null)
        {
            return 0L;
        }

        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

}