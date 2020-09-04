package com.blalp.sftwrapper.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ExtractedDownload extends Download {

    private String extractedPath;

    public ExtractedDownload(final String url, final String path, final String extractedPath) {
        super(url, path);
        this.extractedPath = extractedPath;
        if(!extractedPath.endsWith(File.separator)) {
            this.extractedPath+=File.separatorChar;
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            ArchiveInputStream inputStream;
            if (Config.path.getMultiMCArchiveFormat().equals("zip")){
                inputStream = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(file)));
            } else if (Config.path.getMultiMCArchiveFormat().equals("tar.gz")) {
                inputStream = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(file))));
            } else {
                throw new UserErrorExepction("Archive type not ZIP or targz.");
            }
            ArchiveEntry archiveEntry;
            FileOutputStream outputStream;
            while ((archiveEntry = inputStream.getNextEntry()) != null) {
                if (!inputStream.canReadEntryData(archiveEntry)) {
                    throw new UserErrorExepction("Invalid archive");
                }
                if(archiveEntry.isDirectory()) {
                    new File(extractedPath+archiveEntry.getName()).mkdirs();
                } else {
                    outputStream = new FileOutputStream(new File(extractedPath+archiveEntry.getName()));
                    IOUtils.copy(inputStream, outputStream);
                    outputStream.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final UserErrorExepction e) {
            e.printStackTrace();
        }
    }
    
}
