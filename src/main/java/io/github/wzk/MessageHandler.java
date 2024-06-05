package io.github.wzk;

import io.github.wzk.core.ApplicationContext;
import io.github.wzk.entity.dto.MessageType;
import io.github.wzk.entity.dto.transData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class MessageHandler extends ChannelInboundHandlerAdapter {

    // 用set统计在线客户端
//    static Set<Channel> channelSet = new HashSet<>();
    // 用map记录ip对应的channel用于转发
//    static Map<String, Channel> channelMap = new HashMap<>();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress().toString()+" online");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof transData){

            transData data = (transData) msg;
            if(data.getMsgType() == MessageType.TYPE_AUTH.value()){          //认证消息
                log.info("认证消息：" + msg);
                ApplicationContext.add(data.getID(),ctx);
            }else if(data.getMsgType() == MessageType.TYPE_FILE.value()){    //CHAT消息
                ChannelHandlerContext c = ApplicationContext.getContext(data.getTargetID());
                if(c==null){           //接收方不在线，反馈给客户端
                    log.error("转发失败");
                }else{                 //将消转发给接收方
                    c.writeAndFlush(data);

                    String projectDir = System.getProperty("user.dir");
                    Path projectPath = Paths.get(projectDir);
                    Path parentPath = projectPath.getParent();

                    // 获取当前时间作为文件名

                    String timeStamp = String.valueOf(System.currentTimeMillis());
                    // 时间戳转年月日时分秒
                    String dateStr = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(Long.parseLong(timeStamp)));
                    // 构造文件名

                    String parentDir = parentPath.toString() + File.separator + "backup" + File.separator + timeStamp+"_"+data.getID()+"_TO_"+data.getTargetID();

                    if(data.getType().equals("jpg") || data.getType().equals("png") || data.getType().equals("jpeg"))
                    {
                        byte[] fileBytes = (byte[]) data.getData();   //   读取内容
                        File outputDirectory = new File(parentDir);   //   目标文件地址
                        if (!outputDirectory.exists()) {
                            outputDirectory.mkdirs();  // 确保目标文件夹存在
                        }
                        File outputFile = new File(outputDirectory, data.getFileName() + "." + data.getType());
                        try {
//                    nettyClient.getFileWatcherthread().wait();
                            Files.write(outputFile.toPath(), fileBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                            log.info("jpg文件成功保存到: " + outputFile.getAbsolutePath());
//                    nettyClient.getFileWatcherthread().notify();
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }

                    }
                    if(data.getType().equals("txt"))
                    {
                        byte[] fileBytes = (byte[]) data.getData();   //   读取内容
                        File outputDirectory = new File(parentDir);   //   目标文件地址
                        if (!outputDirectory.exists()) {
                            outputDirectory.mkdirs();  // 确保目标文件夹存在
                        }
                        File outputFile = new File(outputDirectory, data.getFileName() + "." + data.getType());
                        try {
//                    nettyClient.getFileWatcherthread().wait();
                            Files.write(outputFile.toPath(), fileBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                            log.info("文本文件成功保存到: " + outputFile.getAbsolutePath());
//                    nettyClient.getFileWatcherthread().notify();
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    }
                    if(data.getType().equals("xlsx"))
                    {
                        byte[] fileBytes = (byte[]) data.getData();
                        File outputDirectory = new File(parentDir);
                        if (!outputDirectory.exists()) {
                            outputDirectory.mkdirs();  // 确保目标文件夹存在
                        }
                        File outputFile = new File(outputDirectory, data.getFileName() + ".xlsx");
                        try (InputStream is = new ByteArrayInputStream(fileBytes);
                             XSSFWorkbook workbook = new XSSFWorkbook(is);
                             FileOutputStream fos = new FileOutputStream(outputFile)) {
                            workbook.write(fos);  // 写入工作簿到目标文件
                            log.info("Excel文件成功保存到: " + outputFile.getAbsolutePath());
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    }
                    log.info("成功将来自 ["+data.getID()+"] 的数据转发给 ["+data.getTargetID()+"]");
                }
            }
            // 广播数据
//            channelSet.forEach(channel -> { // 向所有已连接的客户端发送消息
//                    channel.writeAndFlush(data);
//            });

        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        channelSet.remove(ctx.channel()); // 从channelSet中移除该客户端的channel
        log.info(ctx.channel().remoteAddress().toString() + " offline");
    }

}
