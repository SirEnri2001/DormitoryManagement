package src;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import src.ThreadTool.*;

public class ServerProcess {
    public static void main(String[] args) {
        new NwServer(4321, new ThreadPoolSupport(new ServerProtocol()));
    }
}
interface IOStrategy {
    public void service(java.net.Socket socket);  //对传入的socket对象进行处理
}

class NwServer { // NwServer.java，负责接受连接请求，并将创建的Socket对象
    // 通过IOStrategy接口传递给ThreadSupport对象
    public NwServer(int port, IOStrategy ios) { // 这个方法将在主线程中执行
        try {
            ServerSocket ss = new ServerSocket(port);
            DatabaseAccess.init();
            //Thread t = new Thread(new AutomaticTask());
            //t.setDaemon(true);
            System.out.println("server is ready");
            while (true) {
                Socket socket = ss.accept(); // 负责接受连接请求，
                ios.service(socket); // 将服务器端的socket对象传递给
            } // ThreadSupport对象
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}


class IOThread2 extends Thread {
    private Socket socket = null;
    private IOStrategy ios = null;

    public IOThread2(IOStrategy ios) { // 请比较上一节中的IOThread类的构造方法
        this.ios = ios; // 有何不同？
    }

    public boolean isIdle() { // 如果socket变量为空，那么这个线程当然是空闲的
        return socket == null;
    }

    public synchronized void setSocket(Socket socket) {
        this.socket = socket; // 传递给这个阻塞的线程一个“任务”，并唤醒它。
        notify();
    }

    public synchronized void run() { // 这个同步方法并不是保护什么共享数据，
        while (true) { // 仅仅因为wait方法调用必须拥有对象锁
            try {
                wait(); // 进入线程体后，立刻进入阻塞，等待状态
                ios.service(socket); // 被唤醒后，立刻开始执行服务协议
                socket = null; // 服务结束后，立刻返回到空闲状态
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadPoolSupport implements IOStrategy {
    private ExecutorService service = Executors.newFixedThreadPool(50);
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private final int INIT_THREADS = 50;
    private final int MAX_THREADS = 100;
    private IOStrategy ios = null;

    public ThreadPoolSupport(IOStrategy ios) { // 创建线程池
        this.ios = ios;
        service.submit(IOThread2.currentThread());
        /*for (int i = 0; i < INIT_THREADS; i++) {
            IOThread2 t = new IOThread2(ios); // 传递协议对象，但是还没有socket
            t.start(); // 启动线程，进入线程体后都是wait
            threads.add(t);
        }*/
        /*try {
            Thread.sleep(300);
        } catch (Exception e) {
        } // 等待线程池的线程都“运行”
        */
    }

    public void service(Socket socket) { // 遍历线程池，找到一个空闲的线程，
        IOThread2 t = null; // 把客户端交给“它”处理
        boolean found = false;
        for (int i = 0; i < threads.size(); i++) {
            t = (IOThread2) threads.get(i);
            if (t.isIdle()) {
                found = true;
                break;
            }
        }
        if (!found) // 线程池中的线程都忙，没有办法了，只有创建
        { // 一个线程了，同时添加到线程池中。
            t = new IOThread2(ios);
            t.start();
            try {
                Thread.sleep(300);
            } catch (Exception e) {
            }
            threads.add(t);
        }
        t.setSocket(socket); // 将服务器端的socket对象传递给这个空闲的线程
    } // 让其开始执行协议，向客户端提供服务
}
class ServerProtocol implements IOStrategy {

    //IServerIO c = new ServerImpl(); // 此语句如果转移到service方法中会如何？
    //static List<UUID> uuids = new ArrayList<>();
    static Map<String,String> authMode = new HashMap<>(); //"non","student","manager"


    public void service(Socket socket) { // 本例子中只有一个协议对象，那么
        try { // 计算器对象也只有一个，多线程共享的。
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            System.out.println("Port:" + socket.getPort()+" "+"Client connected");
            dos.flush();
            int command;
            String uuid = "UNKNOWN UUID";
            String user = "";
            String psw = "";
            String jsonString = "";
            JSONArray array = null;
            JSONObject jsonObject;
            Map<String,Object> filters;
            Map<String,Object> columns;
            String InfoType;
            String formType;
            int linesModified = 0;
            while (true) {
                String str = "";
                try{
                    str = dis.readUTF();
                    System.out.println("Port:" + socket.getPort()+" "+str);
                    if(!str.startsWith("__command__")){
                        System.out.println("Port:" + socket.getPort()+" "+"Command head error");
                        socket.close();
                        return;
                    }
                }catch (EOFException | SocketException e){
                    Thread.sleep(2000);
                    if(socket.isClosed()) {
                        System.out.println("Port:" + socket.getPort()+" "+"Socket closed");
                        return;
                    }
                }


                ThreadTool.cmd_mutex.acquire();
                dos.flush();
                try{
                    command = dis.readInt(); // 实际上，协议命令的数值没有多大的意义
                }catch (EOFException | SocketException e){
                    ThreadTool.cmd_mutex.release();
                    throw e;
                }
                System.out.println("Port:" + socket.getPort()+" "+command);
                switch (command) {
                    case 0://命令0映射到ConnectionClosed
                        ThreadTool.cmd_mutex.release();
                        socket.close();
                        System.out.println("Port:" + socket.getPort()+" "+"Connection closed by client command");
                        return;
                    case 1: // 命令1映射到SendLogout
                        ThreadTool.cmd_mutex.release();
                        dos.flush();
                        uuid = dis.readUTF();
                        if (!authMode.containsKey(uuid)) {
                            break;
                        }
                        authMode.put(uuid,"non");
                        break;
                    case 2: // 命令2映射到SendStudentLoginAuth
                        ThreadTool.cmd_mutex.release();
                        dos.flush();
                        uuid = dis.readUTF();
                        user = dis.readUTF();
                        psw = dis.readUTF();
                        try{
                            boolean authRes = UserAuthDAO.StudentUserPswAuth(new String[]{ "sID","sName","sPhone" },user,psw);

                            //Critical area
                            if(authRes){
                                authMode.put(uuid,"student");
                            }else{
                                authMode.put(uuid,"non");
                            }
                            Thread.yield();


                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                        break;
                    case 3: // 命令3映射到SendManagerLoginAuth
                        ThreadTool.cmd_mutex.release();
                        dos.flush();
                        uuid = dis.readUTF();
                        user = dis.readUTF();
                        psw = dis.readUTF();
                        try{
                            boolean authRes = UserAuthDAO.ManagerUserPswAuth(new String[]{ "mID","mName","mPhone" },user,psw);
                                //Critical area
                                if(authRes){
                                    authMode.put(uuid,"manager");
                                }else{
                                    authMode.put(uuid,"non");
                                }
                                Thread.yield();
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                        System.out.println("Port:" + socket.getPort()+" "+"Auth state:"+authMode.get(uuid)+" from uuid:"+uuid);
                        break;
                    case 4://命令4映射到GetAuthStatus
                        ThreadTool.cmd_mutex.release();
                        dos.flush();
                        uuid = dis.readUTF();
                        String res = null;
                        res = authMode.get(uuid)==null?"null":authMode.get(uuid);
                        System.out.println("Port:" + socket.getPort()+" "+"Auth state:"+authMode.get(uuid)+" from uuid:"+uuid);
                        dos.writeUTF(res==null?"null":res);
                        dos.flush();
                        break;
                    case 5:
                        ThreadTool.cmd_mutex.release();
                        jsonString = dis.readUTF();
                        Application application = (Application)JSONObject.parse(jsonString);
                        break;
                    case 8:
                        ThreadTool.cmd_mutex.release();
                        dos.flush();
                        jsonString = dis.readUTF();
                        System.out.println(jsonString);
                        jsonObject = JSONObject.parseObject(jsonString);
                        filters = jsonObject.getInnerMap();
                        InfoType = (String) filters.get("InfoType");
                        filters.remove("InfoType");
                        array = null;
                        if(NameTransformer.StringToClass.containsKey(InfoType)){
                            array= JSONArray.parseArray(JSON.toJSONString(QueryDAO.getList(filters,NameTransformer.StringToClass.get(InfoType))));
                        }else{
                            System.out.println("Port: "+socket.getPort()+" invalid InfoType: "+InfoType);
                        }

                        dos.flush();
                        break;
                    case 9:
                        ThreadTool.cmd_mutex.release();
                        if(array==null){
                            dos.writeUTF("");
                        }else{
                            System.out.println("Port:" + socket.getPort()+" "+array.toJSONString());
                            String qres = array.toJSONString();
                            int bufferSize = 60000;
                            int i =0;
                            int sum = 0;
                            while(i < qres.length()){
                                int endIdx = java.lang.Math.min(qres.length(),i+bufferSize);
                                String jsosPart = qres.substring(i,endIdx);
                                dos.writeUTF(jsosPart);
                                sum += jsosPart.length();
                                i += bufferSize;
                            }
                            assert sum == qres.length();
                        }
                        dos.flush();
                        break;
                    case 10:
                        ThreadTool.cmd_mutex.release();
                        dos.flush();
                        jsonString = dis.readUTF();
                        jsonObject = JSONObject.parseObject(jsonString);
                        columns = jsonObject.getInnerMap();
                        InfoType = (String) columns.get("InfoType");
                        columns.remove("InfoType");
                        if(NameTransformer.StringToClass.containsKey(InfoType)){
                            linesModified = ModifyDAO.executeInsert(columns,NameTransformer.StringToClass.get(InfoType));
                        }else{
                            System.out.println("Port: "+socket.getPort()+" invalid InfoType: "+InfoType);
                        }
                        dos.writeInt(linesModified);
                        dos.flush();
                        break;
                    case 11:
                        ThreadTool.cmd_mutex.release();
                        dos.flush();
                        jsonString = dis.readUTF();
                        jsonObject = JSONObject.parseObject(jsonString);
                        columns = jsonObject.getInnerMap();
                        jsonString = dis.readUTF();
                        jsonObject = JSONObject.parseObject(jsonString);
                        filters = jsonObject.getInnerMap();
                        InfoType = (String) filters.get("InfoType");
                        filters.remove("InfoType");
                        if(NameTransformer.StringToClass.containsKey(InfoType)){
                            linesModified = ModifyDAO.executeUpdate(columns,filters,NameTransformer.StringToClass.get(InfoType));
                        }else{
                            System.out.println("Port: "+socket.getPort()+" invalid InfoType: "+InfoType);
                        }
                        System.out.println("Update "+linesModified+" line(s)");
                        dos.writeInt(linesModified);
                        dos.flush();
                        break;
                    case 12:
                        ThreadTool.cmd_mutex.release();
                        dos.flush();
                        jsonString = dis.readUTF();
                        jsonObject = JSONObject.parseObject(jsonString);
                        filters = jsonObject.getInnerMap();
                        InfoType = (String) filters.get("InfoType");
                        filters.remove("InfoType");
                        if(NameTransformer.StringToClass.containsKey(InfoType)){
                            linesModified = ModifyDAO.executeDelete(filters,NameTransformer.StringToClass.get(InfoType));
                        }else{
                            System.out.println("Port: "+socket.getPort()+" invalid InfoType: "+InfoType);
                        }

                        dos.writeInt(linesModified);
                        dos.flush();
                        break;
                    default:
                        System.out.println("Port:" + socket.getPort()+" "+"Invalid command!");
                        return;
                }
            }
        }catch(NullPointerException e)
        {
            System.out.println("Port: "+socket.getPort());
            e.printStackTrace();
        }


        catch (EOFException | SocketException e){
            System.out.println("Port:" + socket.getPort()+" "+"Connection idle");

        }
        catch (Exception e) {
            System.out.println("Port:"+socket.getPort());
            e.printStackTrace();
        }
    }
}