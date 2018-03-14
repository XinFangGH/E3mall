package cn.test;

import cn.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

public class Fun {
    @Test
    public void fun1() throws Exception {// 1、创建一个配置文件，client.conf，内容就是tracker服务器的ip及端口号。
        // 2、加载配置文件
        ClientGlobal.init("F:\\JetBrains\\IdeaDevelop\\e3mall\\e3-parent\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        // 3、创建一个TrackerClient对象。
        TrackerClient trackerClient = new TrackerClient();
        // 4、使用TrackerClient对象获得TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 5、创建一个StorageClient对象，需要两个构造参数，一个TrackerServer，StorageServer可以是null
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 6、使用StorageClient对象上传文件，服务端返回一个路径及文件名。
        //参数1：本地文件的路径及文件名
        //参数2：文件的扩展名，不带“.”
        //参数3：元数据。可以为null的
        String[] strings = storageClient.upload_file("F:\\课堂资料\\宜立方商城资料\\01.参考资料\\广告图片\\1946ceef1ea90c932e1f7c8bb631a3fa.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void fun2() throws Exception {
        FastDFSClient fast = new FastDFSClient("F:\\JetBrains\\IdeaDevelop\\e3mall\\e3-parent\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        String file = fast.uploadFile("F:\\课堂资料\\宜立方商城资料\\01.参考资料\\广告图片\\1946ceef1ea90c932e1f7c8bb631a3fa.jpg", "jpg");
        System.out.println(file);

    }
}
