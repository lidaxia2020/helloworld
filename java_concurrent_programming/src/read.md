### 第一章、简介
#### 1.1、并发简史
- 不同进程之间可以通过一些粗粒度的通信机制来交换数据，包括：套接字、信号处理器、共享内存、信号量以及文件等。
#### 1.2、线程的优势
- 发挥多处理器的强大能力
- 建模的简单性
- 异步事件的简化处理
- 响应更灵敏的用户界面

### 第二章、线程安全性
#### 2.1、什么是线程安全性
- 无状态对象一定是线程安全的(无状态对象就是一次操作的对象，不能保存数据的对象。)
#### 2.2、原子性
- 竞态条件
#### 2.3、加锁机制
- 内置锁(同步代码块 Synchronized Block)
- 重入
    - 重入的一种实现方法是，为每个锁关联一个获取计算值和一个所有者线程的锁，并且将获取计算值置为1.如果同一个线程再去获取这个锁计数值将递增，
    而当线程退出同步代码块时，计数器会相应的递减。当计数值为0时，这个锁将被释放。
#### 2.4、用锁来保护状态
#### 2.5、活跃性与性能

### 第三章、对象的共享
#### 3.1、可见性
