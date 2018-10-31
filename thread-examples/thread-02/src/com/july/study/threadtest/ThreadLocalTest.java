package com.july.study.threadtest;

/**
 * ThreadLocal 顾名思义也就是线程的局部变量，每个线程都有一个自己的局部变量
 * 内部使用的是一个map集合，就是每个线程都有一个自己的map集合，key是线程的名称，value
 * 是变量的值
 *  接口中有4个方法，分别是
 *              void set(Object value); 设置值
 *              public Object get();    获取值
 *              public void remove();   删除值
 *              protected Object initialValue();    初始化方法，只执行一次
 *  threadlocal 内存泄漏
 *  ThreadLocal为什么会内存泄漏
 *
 *
 * ThreadLocal在ThreadLocalMap中是以一个弱引用身份被Entry中的Key引用的，
 * 因此如果ThreadLocal没有外部强引用来引用它，
 * 那么ThreadLocal会在下次JVM垃圾收集时被回收。
 * 这个时候就会出现Entry中Key已经被回收，出现一个null Key的情况，
 * 外部读取ThreadLocalMap中的元素是无法通过null Key来找到Value的。
 * 因此如果当前线程的生命周期很长，一直存在，
 * 那么其内部的ThreadLocalMap对象也一直生存下来，
 * 这些null key就存在一条强引用链的关系一直存在：
 * Thread --> ThreadLocalMap-->Entry-->Value，
 * 这条强引用链会导致Entry不会回收，Value也不会回收，
 * 但Entry中的Key却已经被回收的情况，造成内存泄漏。
 *
 * 但是JVM团队已经考虑到这样的情况，
 * 并做了一些措施来保证ThreadLocal尽量不会内存泄漏：
 * 在ThreadLocal的get()、set()、remove()方法调用的时候会清除掉
 * 线程ThreadLocalMap中所有Entry中Key为null的Value，
 * 并将整个Entry设置为null，利于下次内存回收。
 *
 *
 *
 * 总结
 * 综合上面的分析，我们可以理解ThreadLocal内存泄漏的前因后果，那么怎么避免内存泄漏呢？
 *
 * 每次使用完ThreadLocal，都调用它的remove()方法，清除数据。
 * 在使用线程池的情况下，没有及时清理ThreadLocal，不仅是内存泄漏的问题，更严重的是可能导致业务逻辑出现问题。所以，使用ThreadLocal就跟加锁完要解锁一样，用完就清理。
 *
 * 作者：Misout
 * 链接：https://www.jianshu.com/p/a1cd61fa22da
 * 來源：简书
 * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
 */
public class ThreadLocalTest {


    public static Integer count = 0;

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        };
    };

    public Integer getNumber() {
        int count = threadLocal.get() + 1;
        threadLocal.set(count);
        return count;

    }

    public static void main(String[] args) {
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        Test005 t1 = new Test005(threadLocalTest);
        Test005 t2 = new Test005(threadLocalTest);

        //每个线程都维护了自己的局部变量列表
        t1.start();
        t2.start();

    }

}

class Test005 extends Thread{

    ThreadLocalTest threadLocalTest;

    public Test005(ThreadLocalTest threadLocalTest){
        this.threadLocalTest = threadLocalTest;
    }

    @Override
    public void run() {
        for(int i=0;i<3 ;i++){
            System.out.println(Thread.currentThread().getName()+","+threadLocalTest.getNumber());
        }
    }
}
