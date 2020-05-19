package think.stream;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Auther lidaxia
 * @Date 2020-05-16 21:46
 */
public class StreamDemo {

    /**
     * 概念
     */
    public static void main(String[] args) {

        int[] nums = {1, 2, 3};
        // 外部迭代
        int sum1 = 0;
        for (int i : nums) {
            sum1 += i;
        }
        System.out.println("sum1 = " + sum1);

        //内部迭代
        int sum2 = IntStream.of(nums).map(i -> i * 2).sum();
        System.out.println("sum2 = " + sum2);

        /**
         * 1、创建流
         *
         * 集合                   Collection.stream/parallelStream
         * 数组                   Arrays.stream
         * 数字Stream             IntStream/LongStream.range/rangeClosed
         * 自己创建                Stream.generate/iterate
         */
        List<String> list = new ArrayList<>();
        list.stream();
        list.parallelStream();

        Arrays.stream(new int[]{3, 4, 56});

        IntStream.of(1, 2, 3);
        IntStream.rangeClosed(1, 30);
        // 使用random创建无线流
        new Random().ints().limit(10);

        Random random = new Random();
        Stream.generate(() -> random.nextInt()).limit(10);


        /**
         * 2、流中间操作
         * 无状态操作
         *      map/maptoXxx
         *      flatMap/flatMapToXxx
         *      filter
         *      peek
         *      unordered
         * 有状态操作
         *      distinct
         *      sorted
         *      limit/skip
         */
        String str = "my name is 007";

        // 把每个单词的藏毒调用出来
        Stream.of(str.split(" ")).filter(s -> s.length() > 2).forEach(System.out::println);

        // flatMap A->B属性（B属性是一个集合），最终得到所有A元素的所有B的属性集合
        // intStream/longStream 并不是Stream子类，所以要进行装箱boxed
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed()).forEach(
                i -> System.out.println((char) i.intValue())
        );

        // peek 用于debug
        Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);

        // limit 使用，主要用于无线流

        /**
         * 3、终止操作(带order的都和并行相关)
         *  非短路操作
         *      forEach/forEachOrdered
         *      collect/toArry
         *      reduce           把流合并成一个数据
         *      min/max/count
         *   短路操作
         *      findFirst/findAny
         *      allMatch/anyMatch/noneMatch
         */
        // 使用并行流
        str.chars().parallel().forEach(i -> System.out.print((char) i));
        System.out.println();
        // 使用forEachOrdered保证顺序
        str.chars().parallel().forEachOrdered(i -> System.out.print((char) i));

        // 收集到list
        List<String> list1 = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(list1);

        // 使用reduce 拼接字符串
        Optional<String> optional = Stream.of(str.split(" ")).reduce((s1, s2) -> s1 + "|" + s2);
        String s = Stream.of(str.split(" ")).reduce("", (s1, s2) -> s1 + "|" + s2);
        System.out.println(optional.orElse(""));

        // 求所有单词长度
        Integer len = Stream.of(str.split(" ")).map(s1 -> s1.length()).reduce(0, (s1, s2) -> s1 + s2);
        System.out.println(len);

        Optional<String> max = Stream.of(str.split(" ")).max((s1, s2) -> s1.length() - s2.length());
        System.out.println(max.get());

        // 使用findFirst终止无线流
        OptionalInt findFirst = new Random().ints().findAny();

        /**
         * 并行流
         */
        // 多次调用串行并行函数，以最后调用为准
//        IntStream.range(1, 100)
//                // 调用parallel产生一个并行流
//                .parallel().peek(StreamDemo::debug)
//                // 调用sequential产生一个串行流
//                .sequential().peek(StreamDemo::debug1)
//                .count();

        // 并行流使用的线程池：ForkJoinPool.commonPool
        // 默认的线程数是当前机器的cpu个数
        //使用这个属性修改默认线程数
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
        IntStream.range(1, 100)
                // 调用parallel产生一个并行流
                .parallel().peek(StreamDemo::debug)
                .count();

        //使用自己的线程池，防止任务被阻塞
        ForkJoinPool pool = new ForkJoinPool(20);
        pool.submit(() -> IntStream.range(1, 100)
                // 调用parallel产生一个并行流
                .parallel().peek(StreamDemo::debug)
                .count());
        pool.shutdown();

        synchronized (pool) {
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        /**
         * 收集器
         */
        List<Student> students = Arrays.asList(
                new Student("大白", 3, Gender.MAN, 3),
                new Student("小明", 30, Gender.WOWAN, 3),
                new Student("张三", 40, Gender.MAN, 3),
                new Student("李四", 31, Gender.WOWAN, 3),
                new Student("王五", 23, Gender.MAN, 3),
                new Student("赵六", 54, Gender.MAN, 3),
                new Student("理器", 37, Gender.WOWAN, 3)
        );
        // 得到所有学生年龄
        // s->s.getAge --> Student::getAge  不会多生成一个类似lambda$0这样的函数
        Set<Integer> age = students.stream().map(Student::getAge)
                //.collect(Collectors.toList());
                .collect(Collectors.toCollection(TreeSet::new));

        // 统计汇总信息
        IntSummaryStatistics summaryStatistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));

        //分块
        Map<Boolean, List<Student>> sex = students.stream().collect(Collectors.partitioningBy(s1 -> s1.getSex() == Gender.MAN));

        // 分组
        Map<Integer, List<Student>> grades = students.stream().collect(Collectors.groupingBy(Student::getGrade));

        // 得到所有班级的学生个数
        Map<Integer, Long> gradesCount = students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));

    }

    public static void debug(int i) {
        System.out.println(Thread.currentThread().getName() + "debug = " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void debug1(int i) {
        System.err.println("debug = " + i);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


class Student {

    private String name;

    private Integer age;

    private Gender sex;

    private Integer grade;

    public Student() {
    }

    public Student(String name, int age, Gender sex, int grade) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}

enum Gender {
    MAN(1),
    WOWAN(2);
    private Integer sex;

    Gender(Integer sex) {
        this.sex = sex;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}