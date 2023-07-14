package com.btwl.filereader;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@SpringBootTest
class FileReaderApplicationTests {

  @Resource
  private MockMvc mockMvc;

  @Test
  void contextLoads() throws Exception {
    String split=file_split_char_With_OS_type();
    String f_name = "resume.pdf";
    String dir = System.getProperty("user.dir");
    String fullName = dir + split + f_name;//only use for linux , windows use \
    File file = new File(fullName);
    String destFileName=dir+split+"res2.pdf";
    ServletOutputStream inputStream = mockMvc.perform(
        MockMvcRequestBuilders
            .get(split+"file"+split+"resume.pdf")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn().getResponse().getOutputStream();

    var outputStream = new FileOutputStream(destFileName);
    byte[] buf=new byte[1024*4];
    int readLen=0;
//    while ((readLen=inputStream.(buf))!=1){
//    }
  }

  private  String file_split_char_With_OS_type(){
    String property = System.getProperty("os.name");
    String split_linux="/";
    String split_windows="\\";
    if (property.startsWith("Windows")){
      return  split_windows;
    }
    else
      return split_linux;
    }
  }

