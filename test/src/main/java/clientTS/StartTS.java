package clientTS;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import util.AES128Algrt;
import util.ServiceTools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StartTS {
    public static void main(String[] args) {
        try {
            TTransport socket = new TSocket("localhost",9012);
           // TTransport transport = new TFramedTransport(socket);
            TProtocol protocol = new TBinaryProtocol(socket);
            CommonManageService.Client client = new CommonManageService.Client(protocol);
            socket.open();
            runMethod(socket);
            // client.Login("client");

            ObjectMapper oMapper = new ObjectMapper();
            CommonManageReqInfo req = new CommonManageReqInfo();
            String uuid = ServiceTools.getUUID();
            req.setUuid(uuid);
            //获取密钥
            String key = ServiceTools.getAESKey(uuid);
            String jiami = AES128Algrt.encrypt(uuid,key);
            req.setCallMethod("loginMPBIS");
            Map<String, String> params = new HashMap<String, String>();
            params.put("type","TS");
            params.put("id","1");
            params.put("key",jiami);
            params.put("version","2.0.2018.2");
            req.setCallMethodParam(oMapper.writeValueAsString(params));
            //向服务端发送消息
            for (int i = 0; i < 1; ++i){
                System.out.println("发送请求");
                client.ServiceRequest(req);
                Thread.sleep(1000);
            }
            CommonManageReqInfo heart = new CommonManageReqInfo();
            heart.setCallMethod("HeartBeatWithMPBIS");
            while (true){
                System.out.println("心跳");
                client.ServiceRequest(heart);
                Thread.sleep(3000);
            }
//            System.in.read();
//            socket.close();
        } catch (TTransportException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void runMethod(final TTransport tSocket){
        Thread thread = new Thread(new Runnable(){
            Impl impl = new Impl(tSocket);
            public void run() {
                // TODO Auto-generated method stub
                impl.process();
            }

        });
        thread.start();
    };
}
