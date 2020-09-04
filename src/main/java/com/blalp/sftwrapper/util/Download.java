package com.blalp.sftwrapper.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download implements Runnable {
    protected String url;
    protected File file;
    int size = -1;

    public Download(String url, String path) {
        this(url, new File(path));
    }

    public Download(String url, File file) {
        this.file = file;
        this.url = url;
        new Thread(this).start();
        try {
            URL url2 = new URI(url).toURL();
            URLConnection connection = url2.openConnection();
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.connect();
            size = connection.getContentLength();
            new Thread(new DownloadBar(size, file)).start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            ReadableByteChannel input = Channels.newChannel(new URI(url).toURL().openStream());
            outputStream.getChannel().transferFrom(input, 0, Long.MAX_VALUE);
            input.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
