package com.helloworld.sort;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LIstSort {
    public static void main(String[] args) {
        List<Data> dataList = new ArrayList<>();
        dataList.add(new Data(3L, 1L));
        dataList.add(new Data(1L, null));
        dataList.add(new Data(2L, 1L));
        dataList.add(new Data(5L, 4L));
        dataList.add(new Data(4L, 0L));
        dataList.add(new Data(6L, 5L));

        dataList.sort(Comparator.comparing(Data::getPid).thenComparing(Data::getId));
        //组--list Child data
        Map<Long, List<Data>> group = dataList.stream().collect(Collectors.groupingBy(Data::getPid));
        System.out.println(group);
        //id --data
        Map<Long, Data> collect = dataList.stream().collect(Collectors.toMap(Data::getId, Function.identity()));
        //已经处理过的group id
        Set<Long> printed = new HashSet<>();
        List<Data> sortedList = new ArrayList<>();
        for (Long aLong : group.keySet()) {
            Data x = collect.get(aLong);
            if (x != null) {
                printGroup(group, x, printed,sortedList);
            }
        }
        System.out.println(printed);
        System.out.println(sortedList);
    }

    private static void printGroup(Map<Long, List<Data>> group, Data data, Set<Long> printed,List<Data> result) {
        Long pid = data.getPid();
        List<Data> childList = group.get(data.getId());
        System.out.println(data);
        result.add(data);
        if (!printed.contains(pid)) {
            printed.add(data.getPid());
            if (childList != null) {
                childList.sort(Comparator.comparing(Data::getId));
                for (Data child : childList) {
                    printGroup(group, child, printed,result);
                }
            }
        }
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