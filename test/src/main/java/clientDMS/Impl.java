package clientDMS;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransport;

public class Impl implements CommonManageService.Iface{
    protected int nMsgCount = 0;
    protected TTransport socket;
    protected TProcessor processor;
    protected CommonManageService.Client client;
    public Impl(TTransport socket, CommonManageService.Client client){
        this.client = client;
        this.socket = socket;
    }
    @Override
    public void ServiceRequest(CommonManageReqInfo reqInfo) throws TException {
        System.out.println(reqInfo.getCallMethod());
        System.out.println(reqInfo.getCallMethodParam());
        if(reqInfo.getCallMethod().equals("GetNVRInfoFromDMS")){
            CommonManageReply repInfo = new CommonManageReply();
            repInfo.setCallRet("[{\"channelol\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\"cpuused\":0,\"devtype\":\"JF-VER008K-016A\",\"diskinfo\":[{\"disktotal\":1000,\"diskused\":400,\"id\":0},{\"disktotal\":1000,\"diskused\":400,\"id\":1},{\"disktotal\":1000,\"diskused\":400,\"id\":2}],\"meminfo\":{\"memtotal\":1000,\"memused\":1000},\"msg\":\"\",\"online\":1,\"ret\":0,\"uri\":\"\",\"vtstate\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}]\n");
            repInfo.setUuid(reqInfo.getUuid());
            reqInfo.setType(reqInfo.getType());
            client.ServiceReply(repInfo);
        }
    }

    @Override
    public void ServiceReply(CommonManageReply repInfo) throws TException {
        if(repInfo!= null){
            System.out.println(repInfo.getCallRet());
            System.out.println(repInfo.getUuid());
        }
    }

    @Override
    public CommonManageReply ServiceReqAndReply(CommonManageReqInfo reqInfo) throws TException {
        return null;
    }
    public void process(){
        processor = new CommonManageService.Processor<CommonManageService.Iface>(this);
        TBinaryProtocol protocol = new TBinaryProtocol(socket);
        while (true)
        {
            try
            {
                //TProcessor，负责调用用户定义的服务接口，从一个接口读入数据，写入一个输出接口
                while (processor.process(protocol, protocol)){
                    //阻塞式方法,不需要内容
//                    System.out.println("走阻塞式方法");
                    //关闭socket
                    //socket.close();
                }
                //connection lost, return
                return;
            }catch (TException e){
                System.out.println("连接已断开...");
                e.printStackTrace();
                return;
            }
        }
    }
}
