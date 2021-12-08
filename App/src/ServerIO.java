package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class ServerIO implements IServerIO{
    UUID uuid = null;
    DataInputStream dis;
    DataOutputStream dos;
    Socket socket;
    public String ServerIP;
    public int ServerPort;

    public ServerIO(String IP_addr,int port) {
        ServerIP = IP_addr;
        ServerPort = port;

    }

    public ServerIO() {
        //ServerIP = "155.138.157.102";
        //ServerIP = "192.168.203.130";
        ServerIP = "127.0.0.1";
        ServerPort = 4321;
    }

    @Override
    public synchronized void EstablishConnection(NetworkUtil util) {
        socket = (Socket)util;
        if(socket.isConnected()){
            System.out.println("socketInit completed with util");
        }else{
            System.out.println("socketInit failed with util");
        }
        dis = util.dis;
        dos = util.dos;
        uuid = util.uuid;
        //sendUUID();
    }

    @Override
    public synchronized void SwitchToNetworkUtil(NetworkUtil util) {
        socket = (Socket) util;
        dis = util.dis;
        dos = util.dos;
        uuid = util.uuid;
    }

    @Override
    public synchronized void EstablishConnection() {
        try {
            socket = new Socket(ServerIP,ServerPort);
            if(socket.isConnected()){
                System.out.println("socketInit completed");
            }else{
                System.out.println("socketInit failed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            //sendUUID();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void ConnectionClosed() {
        try {
            System.out.println("Port: "+socket.getLocalPort());
            socket.close();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void SendLogout() throws IOException {
        if(uuid==null){
            return;
        }
        sendCommand(1);
        dos.writeUTF(uuid.toString());
        dos.flush();
    }

    private void sendCommand(int code) {
        try {
            dis.read(new byte[dis.available()]);
            dos.writeUTF("__command__");
            dos.flush();
            dos.writeInt(code);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    @Deprecated
    public int sendUUID() {
        if(uuid==null){
            uuid = UUID.randomUUID();
        }
        try {
            sendCommand(1);
            dos.writeUTF(uuid.toString());
            dos.flush();
            String response = dis.readUTF();
            if(uuid.equals(response)){
                return 1;
            }else{
                return -3;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public void SendStudentLoginAuth(String user, String psw) throws IOException {
        sendCommand(2);
        dos.writeUTF(uuid.toString());
        dos.writeUTF(user);
        dos.writeUTF(SHAUtil.SHAMD5(psw));
        dos.flush();
    }

    @Override
    public void SendManagerLoginAuth(String user, String psw_rsa256) throws IOException {
        sendCommand(3);
        dos.writeUTF(uuid.toString());
        dos.writeUTF(user);
        dos.writeUTF(psw_rsa256);
        dos.flush();
    }

    @Override
    public int ConnectionStatus() {
        if(socket == null||!socket.isConnected()){
            return -1;
        }
        else if(socket.isConnected()){
            return 0;
        }
        return -1;
    }

    @Override
    public String GetAuthStatus() throws IOException {
        String res = "";
        sendCommand(4);
        dos.writeUTF(uuid.toString());
        dos.flush();
        res = dis.readUTF();

        return res;
    }


    @Override
    public void SendQueryRequest(String InfoType, Map<String,Object> filters) {
        try {
            sendCommand(8);
            JSONObject jsonObject = new JSONObject(filters);
            jsonObject.fluentPut("InfoType",InfoType);
            System.out.println(jsonObject.toJSONString());
            dos.writeUTF(jsonObject.toJSONString());
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> List<T> GetQueryInfo(Class<T> type) {
        try {
            sendCommand(9);
            dos.flush();
            String resStr = dis.readUTF();
            JSONArray jsonArray;
            while (true){
                try{
                    jsonArray = JSONArray.parseArray(resStr);
                    break;
                }catch (JSONException e){
                    resStr += dis.readUTF();
                }
            }
            System.out.println(jsonArray.toString());
            return JSONArray.parseArray(resStr,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    @Override
    public int SendInsertRequest(String InfoType, Map<String, Object> columns) {
        try {
            sendCommand(10);
            JSONObject jsonObject = new JSONObject(columns);
            jsonObject.fluentPut("InfoType",InfoType);
            System.out.println(jsonObject.toJSONString());
            dos.writeUTF(jsonObject.toJSONString());
            dos.flush();
            return dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int SendUpdateRequest(String InfoType, Map<String, Object> columns, Map<String, Object> filters) {
        try {
            sendCommand(11);
            JSONObject jsonObject = new JSONObject(columns);
            System.out.println(jsonObject.toJSONString());
            dos.writeUTF(jsonObject.toJSONString());
            jsonObject = new JSONObject(filters);
            jsonObject.fluentPut("InfoType",InfoType);
            System.out.println(jsonObject.toJSONString());
            dos.writeUTF(jsonObject.toJSONString());
            dos.flush();
            return dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int SendDeleteRequest(String InfoType, Map<String, Object> filters) {
        try {
            sendCommand(12);
            JSONObject jsonObject = new JSONObject(filters);
            jsonObject.fluentPut("InfoType",InfoType);
            System.out.println(jsonObject.toJSONString());
            dos.writeUTF(jsonObject.toJSONString());
            dos.flush();
            return dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int GetErrorCode() {
        return 0;
    }

}
