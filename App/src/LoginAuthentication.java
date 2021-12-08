package src;

import sun.nio.ch.Net;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class LoginAuthentication {
    //登录记录
    IServerIO serverIO = null;
    String user;
    String psw;
    String status = null;
    public final byte[] lock = new byte[0];
    public byte[] tLock;
    public LoginAuthentication(){
        tLock =  new byte[0];
        serverIO = new ServerIO();
    }



    public void StartStudentAuthentication(String user,String psw){
        this.user = user;
        this.psw = psw;
        StudentAuthThread t = new StudentAuthThread();
        t.authEntity = this;
        t.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        t.start();
        //System.out.println();
    }
    public void StartManagerAuthentication(String user,String psw){
        this.user = user;
        this.psw = psw;
        ManagerAuthThread t = new ManagerAuthThread();
        t.authEntity = this;
        t.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        t.start();
        //System.out.println();
    }

    public void LogOut(){
        LogOutThread t = new LogOutThread();
        t.authEntity = this;
        t.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        t.start();
    }


    public String GetStatus(){
        GetAuthStatusThread t = new GetAuthStatusThread ();
        t.authEntity = this;
        t.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return status;
    }
}

class StudentAuthThread extends LoginAuthThread{
    @Override
    public void run() {
        NetworkUtil util = null;
        try {
            util = new NetworkUtil((ServerIO) authEntity.serverIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (authEntity.lock) {
            authEntity.serverIO.EstablishConnection(util);
        }
        try {
            synchronized (authEntity.lock){
                authEntity.serverIO.SwitchToNetworkUtil(util);
                authEntity.serverIO.SendStudentLoginAuth(authEntity.user,authEntity.psw);
                ThreadTool.login_mutex.release();
            }
            Thread.yield();

        } catch (EOFException | SocketException e) {
            System.out.println("Port: "+ ((ServerIO) authEntity.serverIO).socket.getLocalPort());
            e.printStackTrace();
            throw new MyIOException();
        }catch (IOException e){
            e.printStackTrace();
        }
        synchronized (authEntity.lock){
            authEntity.serverIO.SwitchToNetworkUtil(util);
            authEntity.serverIO.ConnectionClosed();
        }
    }
}
class ManagerAuthThread extends LoginAuthThread{
    @Override
    public void run() {
        NetworkUtil util = null;
        try {
            util = new NetworkUtil((ServerIO) authEntity.serverIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (authEntity.lock) {
            authEntity.serverIO.EstablishConnection(util);
        }
        try {
            synchronized (authEntity.lock){
                authEntity.serverIO.SwitchToNetworkUtil(util);
                authEntity.serverIO.SendManagerLoginAuth(authEntity.user,authEntity.psw);
                ThreadTool.login_mutex.release();
            }
        } catch (EOFException | SocketException e) {
            System.out.println("Port: "+ ((ServerIO) authEntity.serverIO).socket.getLocalPort());
            e.printStackTrace();
            throw new MyIOException();
        }catch (IOException e){
            e.printStackTrace();
        }
        synchronized (authEntity.lock){
            authEntity.serverIO.SwitchToNetworkUtil(util);
            authEntity.serverIO.ConnectionClosed();

        }
    }
}
class GetAuthStatusThread extends LoginAuthThread{
    @Override
    public void run() {
        authEntity.status = "waiting";
        NetworkUtil util = null;
        try {
            util = new NetworkUtil((ServerIO) authEntity.serverIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (authEntity.lock) {
            authEntity.serverIO.EstablishConnection(util);
        }
        try {
            ThreadTool.login_mutex.acquire();
            synchronized (authEntity.lock){
                authEntity.serverIO.SwitchToNetworkUtil(util);
                authEntity.status = authEntity.serverIO.GetAuthStatus();
            }
            ThreadTool.login_mutex.release();

        } catch (EOFException | SocketException e) {
            System.out.println("Port: "+ ((ServerIO) authEntity.serverIO).socket.getLocalPort());
            e.printStackTrace();
            throw new MyIOException();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        synchronized (authEntity.lock){
            authEntity.serverIO.SwitchToNetworkUtil(util);
            authEntity.serverIO.ConnectionClosed();
        }
    }
}
class LogOutThread extends LoginAuthThread{
    @Override
    public void run() {
        NetworkUtil util = null;
        try {
            util = new NetworkUtil((ServerIO) authEntity.serverIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (authEntity.lock) {
            authEntity.serverIO.EstablishConnection(util);
        }
        try {
            synchronized (authEntity.lock){
                authEntity.serverIO.SwitchToNetworkUtil(util);
                authEntity.serverIO.SendLogout();
            }
        } catch (EOFException | SocketException e) {
            System.out.println("Port: "+ ((ServerIO) authEntity.serverIO).socket.getLocalPort());
            e.printStackTrace();
            throw new MyIOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (authEntity.lock){
            authEntity.serverIO.SwitchToNetworkUtil(util);
            authEntity.serverIO.ConnectionClosed();
        }
    }


}

class LoginAuthThread extends Thread {
    public LoginAuthentication authEntity = null;
    public final byte[] lock = new byte[0];
}