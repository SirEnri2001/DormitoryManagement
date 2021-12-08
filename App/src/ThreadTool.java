package src;

import java.util.concurrent.Semaphore;

public class ThreadTool {
    //多线程控制
    //创建一个用于记录正在读的读进程数
    public static  int readCount=0;
    //创建一个读写线程共享的临界资源
    public static  int sse=0;
    //创建一个读写互斥的信号量
    public static final Semaphore wmutex=new Semaphore(1,true);

    //创建一个用于读进程间的修改临界资源readCount的互斥信号量
    public static final Semaphore rmutex=new Semaphore(1,true);

    //防止写饥饿，创建读写互斥信号量
    public static final Semaphore rwmutex=new Semaphore(1,true);

    //创建一个读写互斥的信号量
    public static final Semaphore auth_wmutex=new Semaphore(1,true);

    //创建一个用于读进程间的修改临界资源readCount的互斥信号量
    public static final Semaphore auth_rmutex=new Semaphore(1,true);

    //防止写饥饿，创建读写互斥信号量
    public static final Semaphore auth_rwmutex=new Semaphore(1,true);

    //command mutex
    public static final Semaphore cmd_mutex=new Semaphore(1,true);

    //login mutex
    public static final Semaphore login_mutex=new Semaphore(0,true);
}

class ThreadLock{
    //多线程控制
    //创建一个用于记录正在读的读进程数
    private int readCount=0;
    //创建一个读写互斥的信号量
    private final Semaphore wmutex=new Semaphore(1,true);

    //创建一个用于读进程间的修改临界资源readCount的互斥信号量
    private final Semaphore rmutex=new Semaphore(1,true);

    //防止写饥饿，创建读写互斥信号量
    private final Semaphore rwmutex=new Semaphore(1,true);

    public void acquireReaderLock() throws InterruptedException{
        System.out.println("ReaderLock acquiring");
        ThreadTool.rwmutex.acquire();
        ThreadTool.rmutex.acquire();
        if(readCount==0){
            ThreadTool.wmutex.acquire();
        }
        readCount++;
        ThreadTool.rmutex.release();
        ThreadTool.rwmutex.release();
        System.out.println("ReaderLock acquired");
    }

    public void releaseReaderLock() throws InterruptedException{
        System.out.println("ReaderLock releasing");
        ThreadTool.rmutex.acquire();
        readCount--;
        ThreadTool.rmutex.release();
        if(readCount==0)
            ThreadTool.wmutex.release();
        Thread.yield();
        System.out.println("ReaderLock released");
    }

    public void acquireWriterLock() throws InterruptedException{
        System.out.println("WriterLock acquiring");
        ThreadTool.rwmutex.acquire();
        ThreadTool.rmutex.acquire();
        if(readCount==0){
            ThreadTool.wmutex.acquire();
        }
        readCount++;
        ThreadTool.rmutex.release();
        ThreadTool.rwmutex.release();
        System.out.println("WriterLock acquired");
    }

    public void releaseWriterLock() throws InterruptedException{
        System.out.println("Writerlock releasing");
        ThreadTool.rmutex.acquire();
        readCount--;
        ThreadTool.rmutex.release();
        if(readCount==0)
            ThreadTool.wmutex.release();
        Thread.yield();
        System.out.println("WriterLock released");
    }
}



