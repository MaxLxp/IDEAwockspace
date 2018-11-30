package clientCMS;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransport;

public class Impl implements CommonManageService.Iface{
    protected int nMsgCount = 0;
    protected TTransport socket;
    protected TProcessor processor;
    public Impl(TTransport socket){
        this.socket = socket;
    }
    @Override
    public void ServiceRequest(CommonManageReqInfo reqInfo) throws TException {
        System.out.println(reqInfo.getCallMethod());
        System.out.println(reqInfo.getCallMethodParam());
    }

    @Override
    public void ServiceReply(CommonManageReply repInfo) throws TException {
        if(repInfo!= null){
            System.out.println(repInfo.getCallRet());
            System.out.println(repInfo.getUuid());
        } else {
            System.out.println("回复空");
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
                    System.out.println("走阻塞式方法");
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
