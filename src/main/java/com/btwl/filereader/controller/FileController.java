package com.btwl.filereader.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {

  static String split = file_split_char_With_OS_type();
  static String running_dir = System.getProperty("user.dir") + split;

  private static String file_split_char_With_OS_type() {
    String property = System.getProperty("os.name");
    String split_linux = "/";
    String split_windows = "\\";
    if (property.startsWith("Windows")) {
      return split_windows;
    } else {
      return split_linux;
    }
  }

  @RequestMapping("{file_name}")
  public void download(
      @PathVariable("file_name") String file_name, HttpServletResponse response)
      throws IOException {

    InputStream iStream = null;
    OutputStream oStream = null;
    String full_name = running_dir + file_name;

    if (!fileExist(full_name)) {
      response.setStatus(404);
      return;
    }

    try {
      iStream = new FileInputStream(full_name);
      oStream = response.getOutputStream();

//      todo : if contentType is img , it display good.
//             if pdf, if cant display.
//             mp4,mp3 not test yet. mp4 is much more big, need advance code, maybe like a slicing windows mechanism?
//      response.setContentType("application/octet-stream");
            response.setContentType("application/"+getSuffix(file_name));
//      setContentType() ->  receive a para called: MIME type( Multipurpose Internet Mail Extensions)
//      application/octet-stream  ->  goto download behaviour directly in browser.


      response.addHeader("Content-Disposition", "attachment; filename="+file_name);
      data_flow_with_buffer(iStream, oStream);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        assert iStream != null;
        iStream.close();
        assert oStream != null;
        oStream.close();
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }

  private static void data_flow_with_buffer(InputStream iStream, OutputStream oStream) throws IOException {
    int len = 0;
    byte[] bytes = new byte[1024];
    while ((len = iStream.read(bytes)) != -1) {
      oStream.write(bytes, 0, len);
      oStream.flush();
    }
  }

  private boolean fileExist(String f) throws IOException {
    File file = new File(f);
    return file.exists();
  }

  private String getSuffix(String s) {
    String[] strArray = s.split("\\.");
    int suffixIndex = strArray.length - 1;
    return strArray[suffixIndex];
  }
}



