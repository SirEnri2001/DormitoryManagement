package src;

import java.io.*;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public class NetworkUtil extends Socket {
    public static UUID uuid = null;
    NetworkUtil(String ipaddr,int port) throws IOException {
        super(ipaddr,port);
        //this.setKeepAlive(false);
        dis = new DataInputStream(this.getInputStream());
        dos = new DataOutputStream(this.getOutputStream());
        if(uuid==null){
            uuid = UUID.randomUUID();
        }
    }
    NetworkUtil(ServerIO serverIO) throws IOException {
        super(serverIO.ServerIP,serverIO.ServerPort);
        dis = new DataInputStream(new BufferedInputStream(this.getInputStream()));
        dos = new DataOutputStream(new BufferedOutputStream(this.getOutputStream()));
        dos.flush();
        dis.read(new byte[dis.available()]);
        if(uuid==null){
            uuid = UUID.randomUUID();
        }
    }

    public DataInputStream dis;
    public DataOutputStream dos;
}

class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler{
    /*
     * Thread.UncaughtExceptionHandler.uncaughtException()会在线程因未捕获的异常而临近死亡时被调用
     */
    int retryTimes = 3;
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Caught "+e);
        e.printStackTrace();
        if(retryTimes-->0){
            Thread newThread = new Thread();
            if(t instanceof GetAuthStatusThread){
                newThread = new GetAuthStatusThread();
            }else if(t instanceof  ManagerAuthThread){
                newThread = new ManagerAuthThread();
            }else if(t instanceof StudentAuthThread){
                newThread = new StudentAuthThread();
            }else{
                newThread = new LogOutThread();
            }
            ((LoginAuthThread)newThread).authEntity = ((LoginAuthThread)t).authEntity;
            //((LoginAuthThread)newThread).lock = ((LoginAuthThread)t).lock;
            newThread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
            newThread.start();
        }
    }
}

class MyIOException extends RuntimeException{

}


