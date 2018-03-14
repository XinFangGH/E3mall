package cn.e3mall.controller;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PictureController {

    @Value("${image.server.url}")
    private String imageServerURL;

    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody
    public String uploadPicture(MultipartFile uploadFile) {
        try {
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf("." )+ 1);
            FastDFSClient fast = new FastDFSClient("classpath:conf/client.conf");
            String url = fast.uploadFile(uploadFile.getBytes(), extName);
            url = imageServerURL + url;
            Map result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);
            String json = JsonUtils.objectToJson(result);

            return json;
        } catch (Exception e) {
            Map result = new HashMap();
            result.put("error", 1);
            result.put("message", "文件上传失败");
            String json = JsonUtils.objectToJson(result);

            return json;
        }
    }

}
