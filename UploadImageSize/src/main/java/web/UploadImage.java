package web;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import util.FileNameUtil;
import util.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 青年晚报
 * @// TODO: 2024/1/12  
 * @version 2.0
 * 图片上传升级版
 */
@Controller
public class UploadImage {
    private String originalFilename=null;
    private String name="";
    @RequestMapping(value = "/login",produces = "text/plain;charset=GBK")
    @ResponseBody
    public void Preload(MultipartFile pimage, HttpServletRequest request) throws IOException {
        InputStream inputStream = pimage.getInputStream();
        BufferedImage read = ImageIO.read(inputStream);
        BufferedImage newImage = ImageUtils.resizeImage(read,50,50);
        originalFilename =pimage.getOriginalFilename();
        name= FileNameUtil.getUUIDFileName()+ FileNameUtil.getFileType(pimage.getOriginalFilename());
        String path=request.getServletContext().getRealPath("/image");
        File file = new File(path + File.separator + name);
        ImageIO.write(newImage,"jpg",file);
        request.getSession().setAttribute("name",name);
    }
    @RequestMapping(value = "/name",produces = "text/plain;charset=GBK")
    @ResponseBody
    public String getName(HttpServletRequest request){
       String name1 = (String) request.getSession().getAttribute("name");
       return name1;
    }
    @RequestMapping(value = "/copy",produces = "text/plain;charset=GBK")
    @ResponseBody
    public String getCopy(HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/image");
        File file = new File(path + File.separator + name);
        String localPath = "C:/Users/张驰/IdeaProjects/pricture/UploadImageSize/src/main/webapp/image";
        File localFile = new File(localPath + File.separator + name);
        FileUtils.copyFile(file, localFile);
        return "success";
    }
}
