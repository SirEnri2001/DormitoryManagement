package src;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IServerIO {

    //Filter 查询方法: String表示字段名，args表示字段名对应的值。对每一个filter所属的类型，args有其filter所指定的类型。
    //比如查询Student：filters = {("sID","")}
    //Filter 查询方法2:若filter字段名

    void EstablishConnection();
    void EstablishConnection(NetworkUtil util);
    void SwitchToNetworkUtil(NetworkUtil util);
    int ConnectionStatus(); //0:连接成功 -1:连接失败
    void ConnectionClosed(); //命令号：0
    void SendLogout() throws IOException;
    void SendStudentLoginAuth(String user,String psw_rsa256) throws IOException; //命令号：2 return uuid of login
    void SendManagerLoginAuth(String user,String psw_rsa256) throws IOException; //命令号：3 return uuid of login
    String GetAuthStatus() throws IOException; //命令号：4
    void SendQueryRequest(String InfoType, Map<String,Object> filters); //命令号：8 filters contains InfoType
    public <T> List<T> GetQueryInfo(Class<T> type); //命令号：9
    int SendInsertRequest(String InfoType, Map<String,Object> columns); //命令号：10 column includes InfoType
    int SendUpdateRequest(String InfoType, Map<String,Object> columns,Map<String,Object> filters); //命令号：11 filters includes InfoType
    int SendDeleteRequest(String InfoType, Map<String,Object> filters);//命令号：12 filters includes InfoType
    //void PaymentRequest(PaymentInfo payment,double amount); //命令号：
    int GetErrorCode(); //错误号:  -1:验证失败 -2:数据库/服务端内部错误 -3:uuid错误 0:等待服务器 1:登录验证成功
    @Deprecated
    int sendUUID(); //命令号：

}
